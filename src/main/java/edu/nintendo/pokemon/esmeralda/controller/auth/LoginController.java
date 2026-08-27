package main.java.edu.nintendo.pokemon.esmeralda.controller.auth;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.edu.nintendo.pokemon.esmeralda.dto.request.auth.LoginRequest;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.auth.LoginResponse;
import main.java.edu.nintendo.pokemon.esmeralda.service.auth.UserService;
import main.java.edu.nintendo.pokemon.esmeralda.util.scenemanager.SceneManager;

public class LoginController implements Initializable {

    private final UserService userService;
    private final SceneManager stage;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblErrorMessage; // Enlace al Label de error del FXML

    public LoginController(UserService userService, SceneManager stage) {
        this.userService = userService;
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Limpiar el mensaje de error al iniciar la vista
        hideError();
    }

    @FXML
    public void handleLogin() throws IOException {
        try {
            hideError();

            String email = txtEmail.getText();
            String password = txtPassword.getText();

            LoginRequest request = new LoginRequest(email, password, null);
            LoginResponse response = userService.login(request);

            if (response != null) {
                try {
                    LoginResponse userSession = userService.login(request);

                    stage.showDashboardView(userSession);

                } catch (Exception e) {
                    System.out.println("Error al iniciar sesión: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    public void handleRegisterView() throws IOException {
        stage.showRegisterView();
    }

    private void showError(String message) {
        lblErrorMessage.setText(message);
        lblErrorMessage.setVisible(true);
        lblErrorMessage.setManaged(true);
    }

    private void hideError() {
        lblErrorMessage.setText("");
        lblErrorMessage.setVisible(false);
        lblErrorMessage.setManaged(false);
    }
}
