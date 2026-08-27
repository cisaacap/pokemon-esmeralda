package main.java.edu.nintendo.pokemon.esmeralda.dto.response.auth;

public class RegisterResponse {
    private boolean success;
    private String message;
    private String nickname;

    public RegisterResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public RegisterResponse(boolean success, String message, String nickname) {
        this.success = success;
        this.message = message;
        this.nickname = nickname;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}