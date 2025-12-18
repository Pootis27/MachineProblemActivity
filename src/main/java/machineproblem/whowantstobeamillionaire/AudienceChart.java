package machineproblem.whowantstobeamillionaire;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class AudienceChart {
    private static final Stage stage = new Stage(); // class-level stage
    public static void showGraph(int[] values) {
        stage.setTitle("Answer Frequencies");

        // Axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Options");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Frequency");

        // Bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Audience's Answer");

        // Data
        String[] options = {"A", "B", "C", "D"};
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        barChart.setLegendVisible(false);


        for (int i = 0; i < 4; i++) {
            series.getData().add(new XYChart.Data<>(options[i], values[i]));
        }

        barChart.getData().add(series);

        // Show scene
        Scene scene = new Scene(barChart, 500, 400);
        stage.setScene(scene);
        stage.show();

        // astetiks
        Platform.runLater(() -> {
            for (int i = 0; i < 4; i++) {
                XYChart.Data<String, Number> data = series.getData().get(i);
                String color;
                switch (i) {
                    case 0 -> color = "#00008B";
                    case 1 -> color = "#8A2BE2";
                    case 2 -> color = "#FF8C00";
                    case 3 -> color = "#228B22";
                    default -> color = "#000000";
                }
                data.getNode().setStyle("-fx-bar-fill: " + color + ";");
            }
        });         // thanks chatgpt for the making of this graph and aesthetics
    }

    public static void closeGraph() {
        stage.close();
    }
}
