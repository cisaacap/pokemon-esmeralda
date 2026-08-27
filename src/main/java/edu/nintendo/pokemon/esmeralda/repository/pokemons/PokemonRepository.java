package main.java.edu.nintendo.pokemon.esmeralda.repository.pokemons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.edu.nintendo.pokemon.esmeralda.config.ConnectionDB;
import main.java.nintendo.pokemon.esmeralda.dto.request.pokemon.PokemonRequest;
import main.java.nintendo.pokemon.esmeralda.dto.response.pokemon.PokemonResponse;

public class PokemonRepository {

    private Connection conn = ConnectionDB.getConnection();

    public PokemonRepository() {
    }

    public PokemonResponse savePokemonUser(PokemonRequest pokemonRequest) throws Exception {
        String sql = "INSERT INTO Usuario_Pokemon (nickname, id_pokemon, mote, health) VALUES (?, ?, ?, ?)";
        
        // Indicamos que queremos recuperar las claves generadas (id_pokemon_usuario)
        try (PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, pokemonRequest.getNickname());
            pstm.setInt(2, pokemonRequest.getIdPokemon());
            pstm.setString(3, pokemonRequest.getMote());
            pstm.setDouble(4, pokemonRequest.getHealth());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idPokemonUsuario = generatedKeys.getInt(1);
                        return new PokemonResponse(
                            idPokemonUsuario,
                            pokemonRequest.getNickname(),
                            pokemonRequest.getIdPokemon(),
                            pokemonRequest.getMote(),
                            pokemonRequest.getHealth()
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en: " + e.getMessage());
        }
        return null;
    }
    
    public List<PokemonResponse> findPokemonsByNickname(String nickname) throws Exception {
        List<PokemonResponse> pokemons = new ArrayList<>();
        String sql = "SELECT id_pokemon_usuario, nickname, id_pokemon, mote, health FROM Usuario_Pokemon WHERE nickname = ?";
        
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, nickname);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    PokemonResponse pokemon = new PokemonResponse(
                        rs.getInt("id_pokemon_usuario"),
                        rs.getString("nickname"),
                        rs.getInt("id_pokemon"),
                        rs.getString("mote"),
                        rs.getDouble("health")
                    );
                    pokemons.add(pokemon);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en: " + e.getMessage());
        }
        return pokemons;
    }
}