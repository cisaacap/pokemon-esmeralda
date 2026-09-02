package main.java.edu.nintendo.pokemon.esmeralda.controller.auth;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    
    // Inyección de elementos del FXML
    @FXML private Label lblErrorMessage; // Nuevo Label para los mensajes de error
    @FXML private TextField txtTrainerName;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private Button btnRegister;

    public RegisterController(UserService userService, SceneManager stage) {
        this.userService = userService;
        this.stage = stage;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRegister.setOnAction(event -> handleRegisterUser());
    }  
    
    @FXML
    public RegisterResponse handleRegisterUser() {
        // Limpiar errores visuales previos antes de evaluar de nuevo
        clearErrors();

        String nickname = txtTrainerName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        // Validaciones básicas de interfaz (campos vacíos)
        if (isTextEmpty(nickname) || isTextEmpty(email) || isTextEmpty(password) || isTextEmpty(confirmPassword)) {
            String msg = "Todos los campos obligatorios deben estar llenos.";
            showError(msg, txtTrainerName, txtEmail, txtPassword, txtConfirmPassword);
            return new RegisterResponse(false, msg);
        }

        // Validación de contraseñas coincidentes
        if (!password.equals(confirmPassword)) {
            String msg = "Las contraseñas no coinciden.";
            showError(msg, txtPassword, txtConfirmPassword);
            return new RegisterResponse(false, msg);
        }

        // Creación del DTO Request
        RegisterRequest request = new RegisterRequest(nickname, email, password);

        try {
            // Llamada al servicio
            RegisterResponse response = userService.register(request);
            
            if (response.isSuccess()) {
                stage.showLoginView();
            } else {
                // Si el servicio falla, iluminamos los campos dependiendo del mensaje de error
                String errorMsg = response.getMessage().toLowerCase();
                if (errorMsg.contains("correo") || errorMsg.contains("email")) {
                    showError(response.getMessage(), txtEmail);
                } else if (errorMsg.contains("contraseña")) {
                    showError(response.getMessage(), txtPassword, txtConfirmPassword);
                } else if (errorMsg.contains("nombre") || errorMsg.contains("nickname")) {
                    showError(response.getMessage(), txtTrainerName);
                } else {
                    showError(response.getMessage(), txtTrainerName, txtEmail, txtPassword, txtConfirmPassword);
                }
            }
            
            return response;
        } catch (Exception e) {
            String errorMsg = "Error en el servidor: " + e.getMessage();
            showError(errorMsg, txtTrainerName, txtEmail, txtPassword, txtConfirmPassword);
            return new RegisterResponse(false, errorMsg);
        }
    }
    
    @FXML
    public void handleLoginView() throws IOException {
        stage.showLoginView();
    }

    // --- Métodos Auxiliares para UI de Errores ---

    private boolean isTextEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    private void showError(String message, Control... fields) {
        // Mostrar mensaje integrado en el Label
        if (lblErrorMessage != null) {
            lblErrorMessage.setText(message);
            lblErrorMessage.setVisible(true);
            lblErrorMessage.setManaged(true);
        }

        // Aplicar clase CSS de error a los campos afectados
        for (Control field : fields) {
            if (field != null && !field.getStyleClass().contains("error-field")) {
                field.getStyleClass().add("error-field");
            }
        }
    }

    private void clearErrors() {
        // Ocultar mensaje de error
        if (lblErrorMessage != null) {
            lblErrorMessage.setText("");
            lblErrorMessage.setVisible(false);
            lblErrorMessage.setManaged(false);
        }

        // Remover la clase CSS de error de todos los inputs
        txtTrainerName.getStyleClass().remove("error-field");
        txtEmail.getStyleClass().remove("error-field");
        txtPassword.getStyleClass().remove("error-field");
        txtConfirmPassword.getStyleClass().remove("error-field");
    }
}