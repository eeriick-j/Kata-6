package io;

import model.Song;

import java.util.stream.Stream;

public interface Store {
    Stream<Song> songs();
}
