package main.java.edu.nintendo.pokemon.esmeralda.dto.request.auth;

public class RegisterRequest {
    private String nickname;
    private String email;
    private String passwrd;
    private int pokemons;
    private int pokedex;

    public RegisterRequest(String nickname, String email, String passwrd) {
        this.nickname = nickname;
        this.email = email;
        this.passwrd = passwrd;
        this.pokemons = 1;
        this.pokedex = 1;
    }

    // Getters y Setters
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswrd() { return passwrd; }
    public void setPasswrd(String passwrd) { this.passwrd = passwrd; }

    public int getPokemons() { return pokemons; }
    public void setPokemons(int pokemons) { this.pokemons = pokemons; }

    public int getPokedex() { return pokedex; }
    public void setPokedex(int pokedex) { this.pokedex = pokedex; }
}