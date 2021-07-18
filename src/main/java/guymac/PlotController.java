package guymac;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PlotController
{
    //@FXML ScatterChart <Double, Double> xychart;
    //@FXML XYChart.Series<Double, Double> xyseries;
    @FXML ObservableList <XYChart.Data <Double, Double>> xydata;
    Pattern dataline = Pattern.compile("(?<State>\\D+)\\s+(?<VaxxPct>\\S+?)%\\s+(?<BluePct>\\S+?)%\\s*");
    
    void parseLine(String line)
    {
        var matcher = dataline.matcher(line);
        
        if (!matcher.matches()) 
        {
            System.err.format("Invalid line '%s'%n", line);
            return;
        }
        
        var x = Double.parseDouble(matcher.group("VaxxPct"));
        var y = Double.parseDouble(matcher.group("BluePct"));
        var state = matcher.group("State");
        xydata.add(new XYChart.Data <Double, Double>(x, y, state));
    }

    @FXML void initialize()
    {

        URL file = null;
        
        try
        {
            file = getClass().getResource("/vaxdata20210717.txt");
            if (file == null) throw new IOException("File not found");
        }
        catch (IOException ex)
        {
            new Alert(AlertType.ERROR, ex.getMessage()).showAndWait();
            Platform.exit();
            return;
        }
        
        try (var lines = Files.lines(Path.of(file.toURI())))
        {
            lines.filter(line -> !line.isBlank() && !line.matches("\\s*#.*")).forEach(this::parseLine);
        } 
        catch (IOException | URISyntaxException ex)
        {
            new Alert(AlertType.ERROR, ex.getMessage()).showAndWait();
            Platform.exit();
            return;
        }

        for (var item : xydata)
        {
            var color = item.getYValue().doubleValue() >= 50 ? "blue" : "red";
            item.getNode().setStyle(String.format("-fx-background-color: %s", color));
        }
    }
}
