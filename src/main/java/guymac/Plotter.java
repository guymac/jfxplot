package guymac;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Plotter extends Application
{
    
    // Must override this method for class that extends Application
    @Override
    public void start(Stage stage) 
    {
        Pane root;
        try
        {
            root = FXMLLoader.load(getClass().getResource("/ui.fxml"));          
        } 
        catch (IOException ex)
        {
            ex.printStackTrace();
            Platform.exit();
            return;
        }

        // Container associated with root node
        Scene scene = new Scene(root);

        // Set the scene for the stage
        stage.setScene(scene);

        // Set stage title
        stage.setTitle("Plot Points");

        // Show the window
        stage.show();
    }

}
