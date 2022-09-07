package guymac;

import java.io.IOException;
import java.net.URL;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
