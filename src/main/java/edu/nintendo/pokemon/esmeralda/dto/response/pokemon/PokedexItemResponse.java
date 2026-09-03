package main.java.edu.nintendo.pokemon.esmeralda.dto.response.pokemon;

public class PokedexItemResponse {
    private int idPokemon;
    private String nombrePokemon;
    private String primaryType;
    private String secondType;
    private double baseDamage;
    private boolean unlocked;

    public PokedexItemResponse(int idPokemon, String nombrePokemon, String primaryType, String secondType, double baseDamage, boolean unlocked) {
        this.idPokemon = idPokemon;
        this.nombrePokemon = nombrePokemon;
        this.primaryType = primaryType;
        this.secondType = secondType;
        this.baseDamage = baseDamage;
        this.unlocked = unlocked;
    }

    public int getIdPokemon() { return idPokemon; }
    public String getNombrePokemon() { return nombrePokemon; }
    public String getPrimaryType() { return primaryType; }
    public String getSecondType() { return secondType; }
    public double getBaseDamage() { return baseDamage; }
    public boolean isUnlocked() { return unlocked; }
}