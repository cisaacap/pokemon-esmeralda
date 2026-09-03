package main.java.edu.nintendo.pokemon.esmeralda.dto.response.pokemon;

public class UserPokemonResponse {
    private int idPokemon;
    private String nombrePokemon;
    private String mote;
    private String primaryType;
    private String secondType;
    private double health;

    public UserPokemonResponse(int idPokemon, String nombrePokemon, String mote, String primaryType, String secondType, double health) {
        this.idPokemon = idPokemon;
        this.nombrePokemon = nombrePokemon;
        this.mote = mote;
        this.primaryType = primaryType;
        this.secondType = secondType;
        this.health = health;
    }

    public int getIdPokemon() { return idPokemon; }
    public String getNombrePokemon() { return nombrePokemon; }
    public String getMote() { return mote; }
    public String getPrimaryType() { return primaryType; }
    public String getSecondType() { return secondType; }
    public double getHealth() { return health; }
}