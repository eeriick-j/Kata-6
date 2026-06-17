package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import viewmodel.Histogram;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private MainFrame(){
        this.setTitle("Songs by year");
        this.setSize(850, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static MainFrame create(){
        return new MainFrame();
    }

    public <T> MainFrame display(Histogram<T> histogram){
        this.getContentPane().add(chartPanelWith(histogram));
        return this;
    }

    private <T> ChartPanel chartPanelWith(Histogram<T> histogram) {
        return new ChartPanel(chartOf(histogram));
    }

    private <T> JFreeChart chartOf(Histogram<T> histogram) {
        JFreeChart chart = ChartFactory.createBarChart(
                histogram.title(),
                histogram.x(),
                histogram.y(),
                datasetOf(histogram)
        );
        chart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_90);
        return chart;
    }

    private <T> CategoryDataset datasetOf(Histogram<T> histogram) {
        DefaultCategoryDataset  dataset = new DefaultCategoryDataset();
        histogram.forEach(bin -> dataset.addValue(
                histogram.count(bin),
                histogram.legend(),
                bin.toString()
        ));
        return dataset;
    }
}
