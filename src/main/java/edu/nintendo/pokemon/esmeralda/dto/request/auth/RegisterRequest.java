package main.java.edu.nintendo.pokemon.esmeralda.dto.request.auth;

public class RegisterRequest {
    private String nickname;
    private String email;
    private String passwrd;
    private String initialPokemon;

    public RegisterRequest(String nickname, String email, String passwrd, String initialPokemon) {
        this.nickname = nickname;
        this.email = email;
        this.passwrd = passwrd;
        this.initialPokemon = initialPokemon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswrd() {
        return passwrd;
    }

    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }

    public String getInitialPokemon() {
        return initialPokemon;
    }

    public void setInitialPokemon(String initialPokemon) {
        this.initialPokemon = initialPokemon;
    }
}