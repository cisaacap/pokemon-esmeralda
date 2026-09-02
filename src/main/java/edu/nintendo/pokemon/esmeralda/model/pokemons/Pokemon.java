package main.java.edu.nintendo.pokemon.esmeralda.model.pokemons;

public class Pokemon {

    private int idPokemon;
    private String nombrePokemon;
    private String primaryType;
    private String secondType;
    private double baseDamage;

    public Pokemon(int idPokemon, String nombrePokemon, String primaryType, String secondType, double baseDamage) {
        this.idPokemon = idPokemon;
        this.nombrePokemon = nombrePokemon;
        this.primaryType = primaryType;
        this.secondType = secondType;
        this.baseDamage = baseDamage;
    }

    public int getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(int idPokemon) {
        this.idPokemon = idPokemon;
    }

    public String getNombrePokemon() {
        return nombrePokemon;
    }

    public void setNombrePokemon(String nombrePokemon) {
        this.nombrePokemon = nombrePokemon;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }

    public String getSecondType() {
        return secondType;
    }

    public void setSecondType(String secondType) {
        this.secondType = secondType;
    }

    public double getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(double baseDamage) {
        this.baseDamage = baseDamage;
    }
}
