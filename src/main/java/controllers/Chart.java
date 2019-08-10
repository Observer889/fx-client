package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class Chart implements Initializable {
    @FXML  private BarChart<String, Integer> taskCharts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Январь");

        series1.getData().add(new XYChart.Data("Завершенные", 23));
        series1.getData().add(new XYChart.Data("Незавершенные", 12));

        taskCharts.getData().add(series1);

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Февраль");

        series2.getData().add(new XYChart.Data("Завершенные", 10));
        series2.getData().add(new XYChart.Data("Незавершенные", 35));

        taskCharts.getData().add(series2);

    }
}
