package main.java.edu.nintendo.pokemon.esmeralda.repository.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import main.java.edu.nintendo.pokemon.esmeralda.config.ConnectionDB;
import main.java.edu.nintendo.pokemon.esmeralda.dto.request.auth.LoginRequest;
import main.java.edu.nintendo.pokemon.esmeralda.dto.request.auth.RegisterRequest;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.auth.LoginResponse;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.auth.RegisterResponse;

public class UserRepository {

    private Connection conn = ConnectionDB.getConnection();

    public UserRepository() {
    }

    public LoginResponse findUserByNickName(LoginRequest loginRequest) throws Exception {
        String sql = "SELECT nickname, email, passwrd, pokemons, pokedex FROM Usuarios WHERE email = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, loginRequest.getEmail());
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return new LoginResponse(rs.getString("nickname"), rs.getString("email"), rs.getString("passwrd"), rs.getInt("pokemons"), rs.getInt("pokedex"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en: " + e.getMessage());
        }
        return null;
    }

    public RegisterResponse saveNewUser(RegisterRequest registerRequest) throws Exception {
        String sql = "insert into Usuarios(nickname, email, passwrd) values(?, ?, ?)";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, registerRequest.getNickname());
            pstm.setString(2, registerRequest.getEmail());
            pstm.setString(3, registerRequest.getPasswrd());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                // Retornamos una respuesta exitosa
                return new RegisterResponse(true, "¡Entrenador registrado exitosamente!", registerRequest.getNickname());
            } else {
                return new RegisterResponse(false, "No se pudo registrar al usuario en la base de datos.");
            }

        } catch (SQLException e) {
            System.out.println("Error en: " + e.getMessage());
            return new RegisterResponse(false, "Error de SQL: " + e.getMessage());
        }
    }
}
