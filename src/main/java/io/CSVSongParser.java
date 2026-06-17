package io;

import model.Song;

public class CSVSongParser  {
    public static Song parse(String line) {
        String[] fields = line.split(",");
        return new Song(fields[0], fields[1], toInt(fields[4]), fields[17]);
    }

    private static int toInt(String field) {
        try{
            return Integer.parseInt(field);
        }
        catch(NumberFormatException e) {
            return 0;
        }
    }
}
