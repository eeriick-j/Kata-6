package model;

import java.util.Map;

public record HistogramPOJO<T>(String title, String x, String y, String legend, Map<T, Integer> data) {}
