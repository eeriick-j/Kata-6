package io;

import model.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Stream;

public class DatabaseRecorder implements Recorder {
    private final Connection connection;

    public DatabaseRecorder(Connection connection) {
        this.connection = connection;
        initDB(connection);
    }

    private void initDB(Connection connection) {
        try {
            connection.createStatement()
                    .execute("CREATE TABLE IF NOT EXISTS songs (artist TEXT, title TEXT, year INTEGER, genre TEXT)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Stream<Song> songs) {
        try {
            connection.setAutoCommit(false);
            try(PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO songs (artist, title, year, genre) VALUES (?, ?, ?, ?)")){
                songs.forEach(song -> this.insert(statement, song));
                statement.executeBatch();
                connection.commit();
            }
        } catch (SQLException e) {
            try{connection.rollback();} catch (SQLException _){} ;
        } finally {
            try{connection.setAutoCommit(true);} catch (SQLException _) {}
        }
    }

    private void insert(PreparedStatement statement, Song song) {
        try {
            statement.setString(1, song.artist());
            statement.setString(2, song.title());
            statement.setInt(3, song.year());
            statement.setString(4, song.genre());
            statement.addBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
