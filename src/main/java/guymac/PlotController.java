package guymac;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

public class PlotController
{

    @FXML private ObservableList <XYChart.Data <Double, Double>> xydata;

    @FXML void initialize()
    {
        for (var item : xydata)
        {
            var color = item.getYValue().doubleValue() >= 50 ? "blue" : "red";
            item.getNode().setStyle(String.format("-fx-background-color: %s", color));
            Tooltip.install(item.getNode(), new Tooltip(item.getExtraValue().toString()));
        }
    }
}
