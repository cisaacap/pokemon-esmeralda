package main.java.edu.nintendo.pokemon.esmeralda;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import main.java.edu.nintendo.pokemon.esmeralda.util.scenemanager.SceneManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            SceneManager sceneManager = new SceneManager(primaryStage);
            
            sceneManager.showLoginView();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
