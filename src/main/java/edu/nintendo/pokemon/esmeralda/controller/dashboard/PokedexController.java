package main.java.edu.nintendo.pokemon.esmeralda.controller.dashboard;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.auth.LoginResponse;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.pokemon.PokedexItemResponse;
import main.java.edu.nintendo.pokemon.esmeralda.repository.pokemons.PokemonRepository;
import main.java.edu.nintendo.pokemon.esmeralda.service.auth.UserService;
import main.java.edu.nintendo.pokemon.esmeralda.util.scenemanager.SceneManager;

public class PokedexController {

    @FXML
    private FlowPane flowPokedex;

    private final PokemonRepository pokemonRepository;
    private final UserService userService;
    private final SceneManager stage;
    private LoginResponse userSession;

    public PokedexController(UserService userService, PokemonRepository pokemonRepository, SceneManager stage) {
        this.userService = userService;
        this.pokemonRepository = pokemonRepository;
        this.stage = stage;
    }

    public void initData(LoginResponse loginResponse) {
        this.userSession = loginResponse;
        if (this.userSession != null && flowPokedex != null) {
            flowPokedex.getChildren().clear();
            List<PokedexItemResponse> entries = pokemonRepository.getPokedexEntriesForUser(this.userSession.getNickname());

            for (PokedexItemResponse pkmn : entries) {
                VBox card = crearTarjetaPokedex(pkmn);
                flowPokedex.getChildren().add(card);
            }
        }
    }

    private VBox crearTarjetaPokedex(PokedexItemResponse pkmn) {
        VBox card = new VBox(5.0);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("pokedex-card");
        if (!pkmn.isUnlocked()) {
            card.getStyleClass().add("locked-card");
        }

        // Encabezado ID (#001, #002...)
        Label idLabel = new Label(String.format("#%03d", pkmn.getIdPokemon()));
        idLabel.getStyleClass().add("pokedex-card-id");

        // Contenedor de la imagen recortada
        double baseSize = 70.0;
        StackPane imgContainer = new StackPane();
        imgContainer.setPrefSize(baseSize, baseSize);
        imgContainer.setMinSize(baseSize, baseSize);
        imgContainer.setMaxSize(baseSize, baseSize);

        Rectangle clip = new Rectangle(baseSize, baseSize);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        imgContainer.setClip(clip);

        ImageView imgView = new ImageView();
        imgView.setFitHeight(baseSize * 1.75);
        imgView.setFitWidth(baseSize * 1.75);
        imgView.setPreserveRatio(true);

        if (pkmn.isUnlocked()) {
            String imageFileName = String.format("P%03d.png", pkmn.getIdPokemon());
            String resourcePath = "/main/resources/assets/pokemons/primera-gen/" + imageFileName;
            try {
                InputStream is = getClass().getResourceAsStream(resourcePath);
                if (is != null) {
                    imgView.setImage(new Image(is));
                }
            } catch (Exception e) {
                System.err.println("Error al cargar imagen: " + e.getMessage());
            }
        } else {
            InputStream is = getClass().getResourceAsStream("/main/resources/assets/pokemons/not_found.png");
            if (is != null) {
                imgView.setImage(new Image(is));
            } else {
                Label notFoundImgLabel = new Label("Not found");
                notFoundImgLabel.getStyleClass().add("not-found-text");
                imgContainer.getChildren().add(notFoundImgLabel);
            }
        }

        if (imgView.getImage() != null) {
            imgContainer.getChildren().add(imgView);
        }

        // Datos: Nombre, Daño Base y Tipos
        Label nameLabel = new Label(pkmn.isUnlocked() ? pkmn.getNombrePokemon() : "???");
        nameLabel.getStyleClass().add("pokedex-card-name");

        Label damageLabel = new Label("Atk: " + (pkmn.isUnlocked() ? String.format("%.0f", pkmn.getBaseDamage()) : "???"));
        damageLabel.getStyleClass().add("pokedex-card-damage");

        HBox typesContainer = new HBox(4.0);
        typesContainer.setAlignment(Pos.CENTER);

        if (pkmn.isUnlocked()) {
            typesContainer.getChildren().add(crearBadgeTipo(pkmn.getPrimaryType()));
            if (pkmn.getSecondType() != null && !pkmn.getSecondType().equalsIgnoreCase("null") && !pkmn.getSecondType().trim().isEmpty()) {
                typesContainer.getChildren().add(crearBadgeTipo(pkmn.getSecondType()));
            }
        } else {
            Label lockedType = new Label("???");
            lockedType.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-padding: 2px 6px; -fx-background-radius: 8px; -fx-font-size: 9px;");
            typesContainer.getChildren().add(lockedType);
        }

        card.getChildren().addAll(idLabel, imgContainer, nameLabel, damageLabel, typesContainer);
        return card;
    }

    private Label crearBadgeTipo(String tipo) {
        Label badge = new Label(tipo.toUpperCase());
        String colorHex = obtenerColorPorTipo(tipo);
        badge.setStyle(String.format(
                "-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 9px; -fx-padding: 2px 6px; -fx-background-radius: 8px;",
                colorHex
        ));
        return badge;
    }

    private String obtenerColorPorTipo(String tipo) {
        if (tipo == null) return "#777777";
        switch (tipo.toLowerCase().trim()) {
            case "fuego": case "fire": return "#E62829";
            case "agua": case "water": return "#2980EF";
            case "planta": case "grass": return "#3FA129";
            case "eléctrico": case "electrico": case "electric": return "#FAC000";
            case "hielo": case "ice": return "#3DCEF3";
            case "lucha": case "fighting": return "#FF8000";
            case "veneno": case "poison": return "#9141CB";
            case "tierra": case "ground": return "#915121";
            case "volador": case "flying": return "#81B9EF";
            case "psíquico": case "psiquico": case "psychic": return "#EF4179";
            case "bicho": case "bug": return "#91A119";
            case "roca": case "rock": return "#AFA981";
            case "fantasma": case "ghost": return "#704170";
            case "dragón": case "dragon": return "#5060E1";
            case "siniestro": case "dark": return "#50413F";
            case "acero": case "steel": return "#60A1B8";
            case "hada": case "fairy": return "#EF70EF";
            default: return "#9FA19F";
        }   
    }
}