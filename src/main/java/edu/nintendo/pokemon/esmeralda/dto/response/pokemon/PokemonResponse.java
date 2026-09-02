package main.java.nintendo.pokemon.esmeralda.dto.response.pokemon;

public class PokemonResponse {

    private int idPokemonUsuario;
    private String nickname;
    private int idPokemon;
    private String mote;
    private double health;

    public PokemonResponse(int idPokemonUsuario, String nickname, int idPokemon, String mote, double health) {
        this.idPokemonUsuario = idPokemonUsuario;
        this.nickname = nickname;
        this.idPokemon = idPokemon;
        this.mote = mote;
        this.health = health;
    }

    public int getIdPokemonUsuario() {
        return idPokemonUsuario;
    }

    public void setIdPokemonUsuario(int idPokemonUsuario) {
        this.idPokemonUsuario = idPokemonUsuario;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(int idPokemon) {
        this.idPokemon = idPokemon;
    }

    public String getMote() {
        return mote;
    }

    public void setMote(String mote) {
        this.mote = mote;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}