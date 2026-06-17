package io;

import model.Song;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Stream;

public class DatabaseStore implements Store {
    private final Connection connection;

    public DatabaseStore(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Stream<Song> songs() {
        try {
            return songsIn(connection.createStatement().executeQuery("SELECT * FROM songs"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Song> songsIn(ResultSet resultSet) {
        return Stream.generate(() -> songIn(resultSet)).takeWhile(Objects::nonNull);
    }

    private Song songIn(ResultSet resultSet) {
        try {
            return resultSet.next() ? readSongFrom(resultSet) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Song readSongFrom(ResultSet resultSet) throws SQLException {
        return new Song(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getInt(3),
                resultSet.getString(4)
        );
    }
}
