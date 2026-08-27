package main.java.edu.nintendo.pokemon.esmeralda.controller.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.auth.LoginResponse;
import main.java.edu.nintendo.pokemon.esmeralda.service.auth.UserService;
import main.java.edu.nintendo.pokemon.esmeralda.util.scenemanager.SceneManager;

public class DashboardController {

    @FXML
    private Label lblNickname;

    @FXML
    private HBox hboxCurrentPokemons;

    @FXML
    private AnchorPane panePokedex;

    private LoginResponse userSession;
    private UserService userService;
    private SceneManager stage;
    
    public DashboardController(UserService userService, SceneManager stage) {
        this.userService = userService;
        this.stage = stage;
    }
    
    
    public void initData(LoginResponse loginResponse) {
        this.userSession = loginResponse;
        
        if (this.userSession != null) {
            // 1. Mostrar el Nickname real en la cabecera
            if (userSession.getNickname() != null) {
                lblNickname.setText(userSession.getNickname());
            }

            // 2. Cargar los datos dinámicos basados en la sesión
            renderizarEquipoPokemon();
            renderizarProgresoPokedex();
        }
    }

    /**
     * Renderiza la sección de Pokémones Actuales usando el número del DTO.
     */
    private void renderizarEquipoPokemon() {
        // Limpiamos los elementos anteriores (como el texto de carga)
        hboxCurrentPokemons.getChildren().clear();

        int cantidadPokemons = userSession.getPokemons();

        if (cantidadPokemons == 0) {
            Label emptyLabel = new Label("No tienes Pokémones en tu equipo actual.");
            emptyLabel.getStyleClass().add("placeholder-label");
            hboxCurrentPokemons.getChildren().add(emptyLabel);
        } else {
            // Aquí puedes simular o crear dinámicamente tarjetas según la cantidad
            Label infoLabel = new Label("Pokémones en equipo: " + cantidadPokemons);
            infoLabel.getStyleClass().add("placeholder-label");
            hboxCurrentPokemons.getChildren().add(infoLabel);
            
            // TODO: Si en el futuro tienes una lista de objetos Pokémon, 
            // puedes iterar sobre ellos y agregar componentes gráficos (AnchorPane/VBox) por cada uno.
        }
    }

    /**
     * Renderiza el registro de la Pokédex usando el progreso del DTO.
     */
    private void renderizarProgresoPokedex() {
        // Limpiamos el AnchorPane
        panePokedex.getChildren().clear();

        int pokedexRegistrados = userSession.getPokedex();

        // Creamos una etiqueta informativa con el progreso real de la BD
        Label pokedexLabel = new Label("Pokémon registrados en la Pokédex: " + pokedexRegistrados + " / 386");
        pokedexLabel.getStyleClass().add("placeholder-label");

        // Anclamos la etiqueta dentro del AnchorPane para que respete los márgenes
        AnchorPane.setTopAnchor(pokedexLabel, 14.0);
        AnchorPane.setLeftAnchor(pokedexLabel, 14.0);
        AnchorPane.setRightAnchor(pokedexLabel, 14.0);
        AnchorPane.setBottomAnchor(pokedexLabel, 14.0);

        panePokedex.getChildren().add(pokedexLabel);
    }
}