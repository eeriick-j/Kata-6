package io;

import model.Song;

public interface SongParser {
    Song parse(String line);
}
