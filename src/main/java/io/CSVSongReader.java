package io;

import model.Song;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Function;
import java.util.stream.Stream;

public class CSVSongReader implements Store, Closeable {
    private final BufferedReader reader;
    private final Function<String, Song> deserialize;
    private static final String SOURCE_URL =
            "https://huggingface.co/datasets/osanseviero/top-hits-spotify/resolve/main/songs_normalize.csv";

    public CSVSongReader(Function<String, Song> deserialize) throws IOException {
        this.reader = createReader(createConnection());
        this.reader.readLine();
        this.deserialize = deserialize;
    }

    private URLConnection createConnection() throws IOException {
        return new URL(SOURCE_URL).openConnection();
    }

    private BufferedReader createReader(URLConnection connection) throws IOException {
        return new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }

    @Override
    public Stream<Song> songs() {
        return reader.lines().skip(1).map(deserialize);
    }

    private Song readSong() {
        try{
            String line = reader.readLine();
            return (line != null) ? deserialize.apply(line) : null;
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
