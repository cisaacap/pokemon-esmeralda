package main.java.edu.nintendo.pokemon.esmeralda.util.scenemanager;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.edu.nintendo.pokemon.esmeralda.controller.auth.LoginController;
import main.java.edu.nintendo.pokemon.esmeralda.controller.auth.RegisterController;
import main.java.edu.nintendo.pokemon.esmeralda.controller.dashboard.DashboardController;
import main.java.edu.nintendo.pokemon.esmeralda.controller.dashboard.PokedexController;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.auth.LoginResponse;
import main.java.edu.nintendo.pokemon.esmeralda.repository.auth.UserRepository;
import main.java.edu.nintendo.pokemon.esmeralda.repository.pokemons.PokemonRepository;
import main.java.edu.nintendo.pokemon.esmeralda.service.auth.UserService;

public class SceneManager {

    private Stage primaryStage;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showLoginView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/auth/login-view.fxml"));
        loader.setControllerFactory(clazz -> {
            if (clazz == LoginController.class) {
                UserRepository userRepo = new UserRepository();
                UserService userService = new UserService(userRepo);
                return new LoginController(userService, this);
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al instanciar el controlador: " + clazz.getName(), e);
            }
        });
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pokemon Esmeralda - Iniciar Sesión");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void showRegisterView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/auth/register-view.fxml"));
        loader.setControllerFactory(clazz -> {
            if (clazz == RegisterController.class) {
                UserRepository userRepo = new UserRepository();
                UserService userService = new UserService(userRepo);
                return new RegisterController(userService, this);
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al instanciar el controlador: " + clazz.getName(), e);
            }
        });
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pokemon Esmeralda - Registrate");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void showDashboardView(LoginResponse loginResponse) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/dashboard/dashboard-view.fxml"));
        loader.setControllerFactory(clazz -> {
            if (clazz == DashboardController.class) {
                UserRepository userRepo = new UserRepository();
                UserService userService = new UserService(userRepo);
                return new DashboardController(userService, this);
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al instanciar el controlador: " + clazz.getName(), e);
            }
        });

        Parent root = loader.load();
        DashboardController controller = loader.getController();

        if (loginResponse != null) {
            controller.initData(loginResponse);
        }

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pokemon Esmeralda - Dashboard");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void showPokedexView(LoginResponse loginResponse) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/dashboard/pokedex-view.fxml"));
        loader.setControllerFactory(clazz -> {
            if (clazz == PokedexController.class) {
                UserRepository userRepo = new UserRepository();
                UserService userService = new UserService(userRepo);
                PokemonRepository pokemonRepo = new PokemonRepository();
                return new PokedexController(userService, pokemonRepo, this);
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al instanciar el controlador: " + clazz.getName(), e);
            }
        });

        Parent root = loader.load();
        PokedexController controller = loader.getController();

        if (loginResponse != null) {
            controller.initData(loginResponse);
        }

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pokemon Esmeralda - Pokédex");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}