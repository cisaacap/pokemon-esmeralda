package main.java.edu.nintendo.pokemon.esmeralda.controller.auth;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.edu.nintendo.pokemon.esmeralda.dto.request.auth.RegisterRequest;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.auth.RegisterResponse;
import main.java.edu.nintendo.pokemon.esmeralda.service.auth.UserService;
import main.java.edu.nintendo.pokemon.esmeralda.util.scenemanager.SceneManager;

public class RegisterController implements Initializable {

    private UserService userService;
    private SceneManager stage;
    
    @FXML private Label lblErrorMessage;
    @FXML private TextField txtTrainerName;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private ComboBox<String> cmbInitialPokemon;
    @FXML private Button btnRegister;

    public RegisterController(UserService userService, SceneManager stage) {
        this.userService = userService;
        this.stage = stage;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbInitialPokemon.setItems(FXCollections.observableArrayList(
            "Bulbasaur", 
            "Charmander", 
            "Squirtle"
        ));
        
        btnRegister.setOnAction(event -> handleRegisterUser());
    }   
    
    @FXML
    public RegisterResponse handleRegisterUser() {
        clearErrors();

        String nickname = txtTrainerName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String selectedPokemon = cmbInitialPokemon.getValue();

        if (isTextEmpty(nickname) || isTextEmpty(email) || isTextEmpty(password) || isTextEmpty(confirmPassword)) {
            String msg = "Todos los campos obligatorios deben estar llenos.";
            showError(msg, txtTrainerName, txtEmail, txtPassword, txtConfirmPassword);
            return new RegisterResponse(false, msg);
        }

        if (selectedPokemon == null || selectedPokemon.isEmpty()) {
            String msg = "Debes elegir un Pokémon inicial.";
            showError(msg, cmbInitialPokemon);
            return new RegisterResponse(false, msg);
        }

        if (!password.equals(confirmPassword)) {
            String msg = "Las contraseñas no coinciden.";
            showError(msg, txtPassword, txtConfirmPassword);
            return new RegisterResponse(false, msg);
        }

        RegisterRequest request = new RegisterRequest(nickname, email, password, selectedPokemon);

        try {
            RegisterResponse response = userService.register(request);
            
            if (response.isSuccess()) {
                stage.showLoginView();
            } else {
                String errorMsg = response.getMessage().toLowerCase();
                if (errorMsg.contains("correo") || errorMsg.contains("email")) {
                    showError(response.getMessage(), txtEmail);
                } else if (errorMsg.contains("contraseña")) {
                    showError(response.getMessage(), txtPassword, txtConfirmPassword);
                } else if (errorMsg.contains("nombre") || errorMsg.contains("nickname")) {
                    showError(response.getMessage(), txtTrainerName);
                } else if (errorMsg.contains("pokémon") || errorMsg.contains("pokemon")) {
                    showError(response.getMessage(), cmbInitialPokemon);
                } else {
                    showError(response.getMessage(), txtTrainerName, txtEmail, txtPassword, txtConfirmPassword, cmbInitialPokemon);
                }
            }
            
            return response;
        } catch (Exception e) {
            String errorMsg = "Error en el servidor: " + e.getMessage();
            showError(errorMsg, txtTrainerName, txtEmail, txtPassword, txtConfirmPassword, cmbInitialPokemon);
            return new RegisterResponse(false, errorMsg);
        }
    }
    
    @FXML
    public void handleLoginView() throws IOException {
        stage.showLoginView();
    }

    private boolean isTextEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    private void showError(String message, Control... fields) {
        if (lblErrorMessage != null) {
            lblErrorMessage.setText(message);
            lblErrorMessage.setVisible(true);
            lblErrorMessage.setManaged(true);
        }

        for (Control field : fields) {
            if (field != null && !field.getStyleClass().contains("error-field")) {
                field.getStyleClass().add("error-field");
            }
        }
    }

    private void clearErrors() {
        if (lblErrorMessage != null) {
            lblErrorMessage.setText("");
            lblErrorMessage.setVisible(false);
            lblErrorMessage.setManaged(false);
        }

        txtTrainerName.getStyleClass().remove("error-field");
        txtEmail.getStyleClass().remove("error-field");
        txtPassword.getStyleClass().remove("error-field");
        txtConfirmPassword.getStyleClass().remove("error-field");
        cmbInitialPokemon.getStyleClass().remove("error-field");
    }
}