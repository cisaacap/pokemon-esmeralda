package main.java.edu.nintendo.pokemon.esmeralda.dto.request.auth;

public class LoginRequest {

    private String nickname;
    private String email;
    private String passwrd;

    public LoginRequest(String email, String passwrd, String nickname) {
        this.email = email;
        this.passwrd = passwrd;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
