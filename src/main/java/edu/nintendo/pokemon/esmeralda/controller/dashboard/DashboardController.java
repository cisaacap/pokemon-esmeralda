package main.java.edu.nintendo.pokemon.esmeralda.controller.dashboard;

import javafx.scene.shape.Rectangle;
import java.io.InputStream;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.auth.LoginResponse;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.pokemon.UserPokemonResponse;
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
            if (userSession.getNickname() != null) {
                lblNickname.setText(userSession.getNickname());
            }

            renderizarEquipoPokemon();
            renderizarProgresoPokedex();
        }
    }

    private void renderizarEquipoPokemon() {
        hboxCurrentPokemons.getChildren().clear();

        List<UserPokemonResponse> team = userService.getUserPokemons(userSession.getNickname());

        if (team == null || team.isEmpty()) {
            Label emptyLabel = new Label("No tienes Pokémones en tu equipo actual.");
            emptyLabel.getStyleClass().add("placeholder-label");
            hboxCurrentPokemons.getChildren().add(emptyLabel);
            return;
        }

        for (UserPokemonResponse pkmn : team) {
            VBox card = crearTarjetaPokemon(pkmn);
            hboxCurrentPokemons.getChildren().add(card);
        }
    }

    private VBox crearTarjetaPokemon(UserPokemonResponse pkmn) {
        VBox card = new VBox(6.0);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("pokemon-card");

        // --- CONFIGURACIÓN DE TAMAÑO Y CLIP ---
        double baseSize = 75.0; // Tamaño fijo del marco
        double scaleFactor = 1.75;

        StackPane imgContainer = new StackPane();
        imgContainer.setPrefSize(baseSize, baseSize);
        imgContainer.setMaxSize(baseSize, baseSize);
        imgContainer.setMinSize(baseSize, baseSize); // 1. Imagen del Pokémon
        String imageFileName = String.format("P%03d.png", pkmn.getIdPokemon());
        String resourcePath = "/main/resources/assets/pokemons/primera-gen/" + imageFileName;

        Rectangle clip = new Rectangle(baseSize, baseSize);
        clip.setArcWidth(12);  // Bordes redondeados (opcional)
        clip.setArcHeight(12);
        imgContainer.setClip(clip);

        ImageView imgView = new ImageView();
        imgView.setFitHeight(baseSize * scaleFactor);
        imgView.setFitWidth(baseSize * scaleFactor);
        imgView.setPreserveRatio(true);

        try {
            InputStream is = getClass().getResourceAsStream(resourcePath);
            if (is != null) {
                imgView.setImage(new Image(is));
            }
        } catch (Exception e) {
            System.err.println("Error al cargar imagen: " + e.getMessage());
        }

        // Agregar la imagen al contenedor recortado
        imgContainer.getChildren().add(imgView);

        // 2. Mote o Nombre
        String displayName = (pkmn.getMote() != null && !pkmn.getMote().trim().isEmpty())
                ? pkmn.getMote()
                : pkmn.getNombrePokemon();

        Label nameLabel = new Label(displayName);
        nameLabel.getStyleClass().add("pokemon-card-title");

        // 3. Tipos con Padding y Colores (Primario y Secundario)
        HBox typesContainer = new HBox(4.0);
        typesContainer.setAlignment(Pos.CENTER);

        // Tipo 1
        if (pkmn.getPrimaryType() != null && !pkmn.getPrimaryType().isEmpty()) {
            Label type1 = crearBadgeTipo(pkmn.getPrimaryType());
            typesContainer.getChildren().add(type1);
        }

        // Tipo 2 (Sólo se agrega si existe y no es "null")
        String secondType = pkmn.getSecondType();
        if (secondType != null
                && !secondType.trim().isEmpty()
                && !secondType.equalsIgnoreCase("null")) {
            Label type2 = crearBadgeTipo(secondType);
            typesContainer.getChildren().add(type2);
        }

        card.getChildren().addAll(imgView, nameLabel, typesContainer);
        return card;
    }

    /**
     * Crea un Label estilizado tipo píldora con padding y color representativo
     * según el elemento.
     */
    private Label crearBadgeTipo(String tipo) {
        Label badge = new Label(tipo.toUpperCase());
        String colorHex = obtenerColorPorTipo(tipo);

        // Estilos CSS Inline para el padding, bordes redondeados y fondo de color
        badge.setStyle(String.format(
                "-fx-background-color: %s; "
                + "-fx-text-fill: #ffffff; "
                + "-fx-font-weight: bold; "
                + "-fx-font-size: 10px; "
                + "-fx-padding: 3px 8px 3px 8px; "
                + "-fx-background-radius: 10px;",
                colorHex
        ));

        return badge;
    }

    /**
     * Devuelve el código Hex del color oficial según el tipo elemental.
     */
    private String obtenerColorPorTipo(String tipo) {
        if (tipo == null) {
            return "#777777";
        }

        switch (tipo.toLowerCase().trim()) {
            case "fuego":
            case "fire":
                return "#E62829";
            case "agua":
            case "water":
                return "#2980EF";
            case "planta":
            case "grass":
                return "#3FA129";
            case "eléctrico":
            case "electrico":
            case "electric":
                return "#FAC000";
            case "hielo":
            case "ice":
                return "#3DCEF3";
            case "lucha":
            case "fighting":
                return "#FF8000";
            case "veneno":
            case "poison":
                return "#9141CB";
            case "tierra":
            case "ground":
                return "#915121";
            case "volador":
            case "flying":
                return "#81B9EF";
            case "psíquico":
            case "psiquico":
            case "psychic":
                return "#EF4179";
            case "bicho":
            case "bug":
                return "#91A119";
            case "roca":
            case "rock":
                return "#AFA981";
            case "fantasma":
            case "ghost":
                return "#704170";
            case "dragón":
            case "dragon":
                return "#5060E1";
            case "siniestro":
            case "dark":
                return "#50413F";
            case "acero":
            case "steel":
                return "#60A1B8";
            case "hada":
            case "fairy":
                return "#EF70EF";
            case "normal":
            default:
                return "#9FA19F";
        }
    }

    private void renderizarProgresoPokedex() {
        panePokedex.getChildren().clear();

        int pokedexRegistrados = userSession.getPokedex();
        Label pokedexLabel = new Label("Pokémon registrados en la Pokédex: " + pokedexRegistrados + " / 386");
        pokedexLabel.getStyleClass().add("placeholder-label");

        AnchorPane.setTopAnchor(pokedexLabel, 14.0);
        AnchorPane.setLeftAnchor(pokedexLabel, 14.0);
        AnchorPane.setRightAnchor(pokedexLabel, 14.0);
        AnchorPane.setBottomAnchor(pokedexLabel, 14.0);

        panePokedex.getChildren().add(pokedexLabel);
    }
}
