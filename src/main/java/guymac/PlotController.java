package guymac;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tooltip;

public class PlotController
{

    @FXML private ObservableList <XYChart.Data <Double, Double>> xydata;
    private XPath xpath = XPathFactory.newInstance().newXPath();
    
    void parseRow(Element tr) throws NumberFormatException, XPathExpressionException
    {
        var state = tr.getElementsByTagName("th").item(0).getTextContent();
        
        var x = Double.parseDouble(xpath.evaluate("td[1]", tr).replaceAll("%", ""));
        var y = Double.parseDouble(xpath.evaluate("td[2]", tr).replaceAll("%", ""));
        xydata.add(new XYChart.Data <Double, Double>(x, y, state));
    }

    @FXML void initialize()
    {

        URL file = null;
        
        try
        {
            file = getClass().getResource("/vaxdata20210717.xhtml");
            if (file == null) throw new IOException("File not found");
        }
        catch (IOException ex)
        {
            new Alert(AlertType.ERROR, ex.getMessage()).showAndWait();
            Platform.exit();
            return;
        }
             
        try
        {
            NodeList nodes = (NodeList)xpath.evaluate("//tbody/tr", new InputSource(file.openStream()), XPathConstants.NODESET);
            for (int i = 0 ; i < nodes.getLength() ; i++)
            {
                parseRow((Element)nodes.item(i));
            }
        } 
        catch (Exception ex)
        {
            new Alert(AlertType.ERROR, ex.getMessage()).showAndWait();
            Platform.exit();
            return;
        }

        for (var item : xydata)
        {
            var color = item.getYValue().doubleValue() >= 50 ? "blue" : "red";
            item.getNode().setStyle(String.format("-fx-background-color: %s", color));
            Tooltip.install(item.getNode(), new Tooltip(item.getExtraValue().toString()));
        }
    }
}
