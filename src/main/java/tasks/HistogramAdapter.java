package tasks;

import model.HistogramPOJO;
import viewmodel.Histogram;

import java.util.HashMap;
import java.util.Map;

public class HistogramAdapter {
    public static <T>HistogramPOJO<T> toPOJO(Histogram<T> histogram){
        Map<T, Integer> data = new HashMap<>();
        histogram.forEach(bin -> data.put(bin, histogram.count(bin)));

        return new HistogramPOJO<>(
                histogram.title(),
                histogram.x(),
                histogram.y(),
                histogram.legend(),
                data
        );
    }
}
