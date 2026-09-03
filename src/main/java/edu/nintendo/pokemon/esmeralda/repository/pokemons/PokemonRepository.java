package main.java.edu.nintendo.pokemon.esmeralda.repository.pokemons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.java.edu.nintendo.pokemon.esmeralda.config.ConnectionDB;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.pokemon.PokedexItemResponse;

public class PokemonRepository {

    private Connection conn = ConnectionDB.getConnection();

    public List<PokedexItemResponse> getPokedexEntriesForUser(String nickname) {
        List<PokedexItemResponse> list = new ArrayList<>();
        String sql = "SELECT p.id_pokemon, p.nombre_pokemon, p.primary_type, p.second_type, p.base_damage, " +
                     "CASE WHEN up.nickname IS NOT NULL THEN 1 ELSE 0 END AS unlocked " +
                     "FROM Pokemon p " +
                     "LEFT JOIN Usuario_Pokemon up ON p.id_pokemon = up.id_pokemon AND up.nickname = ? " +
                     "WHERE p.id_pokemon BETWEEN 1 AND 51 " +
                     "GROUP BY p.id_pokemon, p.nombre_pokemon, p.primary_type, p.second_type, p.base_damage " +
                     "ORDER BY p.id_pokemon ASC";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, nickname);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    list.add(new PokedexItemResponse(
                        rs.getInt("id_pokemon"),
                        rs.getString("nombre_pokemon"),
                        rs.getString("primary_type"),
                        rs.getString("second_type"),
                        rs.getDouble("base_damage"),
                        rs.getInt("unlocked") == 1
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar la Pokédex: " + e.getMessage());
        }
        return list;
    }
}