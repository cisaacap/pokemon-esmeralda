package main.java.nintendo.pokemon.esmeralda.dto.request.pokemon;

public class PokemonRequest {

    private String nickname;
    private int idPokemon;
    private String mote;
    private double health;

    public PokemonRequest(String nickname, int idPokemon, String mote, double health) {
        this.nickname = nickname;
        this.idPokemon = idPokemon;
        this.mote = mote;
        this.health = health;
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