package app;

import io.CSVSongParser;
import io.CSVSongReader;
import io.DatabaseRecorder;
import io.DatabaseStore;
import io.javalin.Javalin;
import io.javalin.http.Context;
import model.Song;
import org.jetbrains.annotations.NotNull;
import tasks.HistogramAdapter;
import tasks.HistogramBuilder;
import view.MainFrame;
import viewmodel.Histogram;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;

public class WebService {
    private static final File database = new File("songs.db");

    public static void main(String[] args) throws SQLException {
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + database.getAbsolutePath())){
            insertIfRequired(connection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Javalin app =  Javalin.create();
        app.get("/histogram/{field}", WebService::histogram);
        app.start(9000);
    }

    private static void histogram(@NotNull Context context) {
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + database.getAbsolutePath())){
            Stream<Song> songs = new DatabaseStore(connection).songs();
            String field = context.pathParam("field");

            switch (field) {

                case "year" -> context.json(HistogramAdapter.toPOJO(
                        HistogramBuilder.with(songs)
                                .title("Songs by year")
                                .x("Year")
                                .y("Count")
                                .legend("Songs")
                                .build(Song::year)
                        ));
                case "artist" -> context.json(HistogramAdapter.toPOJO(
                        HistogramBuilder.with(songs)
                                .title("Songs by artist")
                                .x("Artist")
                                .y("Count")
                                .legend("Songs")
                                .build(Song::artist)
                    ));
                case "genre" -> context.json(HistogramAdapter.toPOJO(
                        HistogramBuilder.with(songs)
                                .title("Songs by genre")
                                .x("Genre")
                                .y("Count")
                                .legend("Songs")
                                .build(Song::genre)
                    ));
                default -> context.status(400).result("Unknown field");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void insertIfRequired(Connection connection) throws IOException {
        DatabaseRecorder recorder=  new DatabaseRecorder(connection);
        DatabaseStore store = new DatabaseStore(connection);
        if(store.songs().findAny().isPresent()) return;
        recorder.insert(new CSVSongReader(CSVSongParser::parse).songs());
    }


    private static Histogram<Object> histogramOf(Stream<Song> songs) {
        return HistogramBuilder
                .with(songs.filter(song -> song.year() > 2000 && song.year() < 2027))
                .title("Songs by year")
                .x("Year")
                .y("Count")
                .legend("Songs")
                .build(Song::year);
    }

    private static Stream<Song> songs(Connection connection) throws IOException {
        return new DatabaseStore(connection).songs();
    }
}
