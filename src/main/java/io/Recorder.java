package io;

import model.Song;

import java.util.stream.Stream;

public interface Recorder {
    void insert(Stream<Song> songs);
}
