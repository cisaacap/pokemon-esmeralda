package main.java.edu.nintendo.pokemon.esmeralda.repository.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.java.edu.nintendo.pokemon.esmeralda.config.ConnectionDB;
import main.java.edu.nintendo.pokemon.esmeralda.dto.request.auth.LoginRequest;
import main.java.edu.nintendo.pokemon.esmeralda.dto.request.auth.RegisterRequest;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.auth.LoginResponse;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.auth.RegisterResponse;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.pokemon.UserPokemonResponse;

public class UserRepository {

    private Connection conn = ConnectionDB.getConnection();

    public UserRepository() {
    }

    public List<UserPokemonResponse> findUserPokemonsByNickname(String nickname) {
        List<UserPokemonResponse> pokemons = new ArrayList<>();
        String sql = "SELECT p.id_pokemon, p.nombre_pokemon, up.mote, p.primary_type, p.second_type, up.health "
                + "FROM Usuario_Pokemon up "
                + "INNER JOIN Pokemon p ON up.id_pokemon = p.id_pokemon "
                + "WHERE up.nickname = ?";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, nickname);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    pokemons.add(new UserPokemonResponse(
                            rs.getInt("id_pokemon"),
                            rs.getString("nombre_pokemon"),
                            rs.getString("mote"),
                            rs.getString("primary_type"),
                            rs.getString("second_type"),
                            rs.getDouble("health")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo equipo Pokémon: " + e.getMessage());
        }
        return pokemons;
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
        String insertUserSql = "INSERT INTO Usuarios(nickname, email, passwrd) VALUES (?, ?, ?)";
        String insertPokemonUserSql = "INSERT INTO Usuario_Pokemon(nickname, id_pokemon, mote) VALUES (?, ?, ?)";

        int pokemonId = getPokemonIdByName(registerRequest.getInitialPokemon());
        if (pokemonId == -1) {
            return new RegisterResponse(false, "El Pokémon inicial seleccionado no es válido.");
        }

        try {
            // Desactivamos autoCommit para manejar la transacción manualmente
            conn.setAutoCommit(false);

            // 1. Registrar el usuario en la tabla Usuarios
            try (PreparedStatement pstmUser = conn.prepareStatement(insertUserSql)) {
                pstmUser.setString(1, registerRequest.getNickname());
                pstmUser.setString(2, registerRequest.getEmail());
                pstmUser.setString(3, registerRequest.getPasswrd());
                pstmUser.executeUpdate();
            }

            // 2. Asignar el Pokémon inicial en Usuario_Pokemon
            try (PreparedStatement pstmPokemon = conn.prepareStatement(insertPokemonUserSql)) {
                pstmPokemon.setString(1, registerRequest.getNickname());
                pstmPokemon.setInt(2, pokemonId);
                pstmPokemon.setString(3, registerRequest.getInitialPokemon()); // Mote por defecto = nombre
                pstmPokemon.executeUpdate();
            }

            // Confirmar transacción
            conn.commit();
            return new RegisterResponse(true, "¡Entrenador registrado exitosamente con su Pokémon inicial!", registerRequest.getNickname());

        } catch (SQLException e) {
            // En caso de fallar, revertimos los cambios realizados
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Error en rollback: " + rollbackEx.getMessage());
            }
            System.out.println("Error en saveNewUser: " + e.getMessage());
            return new RegisterResponse(false, "Error de SQL: " + e.getMessage());
        } finally {
            // Restauramos el comportamiento por defecto de la conexión
            conn.setAutoCommit(true);
        }
    }

    private int getPokemonIdByName(String name) {
        if (name == null) {
            return -1;
        }
        switch (name.trim().toLowerCase()) {
            case "bulbasaur":
                return 1;
            case "charmander":
                return 4;
            case "squirtle":
                return 7;
            default:
                return -1;
        }
    }
}
