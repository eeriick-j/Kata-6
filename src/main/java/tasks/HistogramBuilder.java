package tasks;

import model.Song;
import viewmodel.Histogram;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class HistogramBuilder {
    private final Stream<Song> songs;
    private final HashMap<String, String> labels;

    public static HistogramBuilder with(Stream<Song> songs){
        return new HistogramBuilder(songs);
    }

    private HistogramBuilder(Stream<Song> songs) {
        this.songs = songs;
        this.labels = new HashMap<>();
    }

    public <T> Histogram<T> build(Function<Song, T> classifier){
        Histogram<T> histogram = new Histogram<>(labels);
        songs.map(classifier).forEach(histogram::put);
        return histogram;
    }

    public HistogramBuilder title(String label){
        this.labels.put("title", label);
        return this;
    }

    public HistogramBuilder x(String label){
        this.labels.put("x", label);
        return this;
    }

    public HistogramBuilder y(String label){
        this.labels.put("y", label);
        return this;
    }

    public HistogramBuilder legend(String label){
        this.labels.put("legend", label);
        return this;
    }
}
