package main.java.edu.nintendo.pokemon.esmeralda.service.auth;

import java.util.List;
import main.java.edu.nintendo.pokemon.esmeralda.dto.request.auth.LoginRequest;
import main.java.edu.nintendo.pokemon.esmeralda.dto.request.auth.RegisterRequest;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.auth.LoginResponse;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.auth.RegisterResponse;
import main.java.edu.nintendo.pokemon.esmeralda.dto.response.pokemon.UserPokemonResponse;
import main.java.edu.nintendo.pokemon.esmeralda.repository.auth.UserRepository;

public class UserService {

    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        if (loginRequest == null) {
            throw new RuntimeException("Credenciales vacías");
        } else if (loginRequest.getEmail() == null || loginRequest.getPasswrd() == null) {
            throw new RuntimeException("Parámetros vacíos");
        }
        LoginResponse response = userRepo.findUserByNickName(loginRequest);
        if (response == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        String psswrd = response.getPasswrd();
        if (psswrd == null) {
            throw new RuntimeException("Contraseña Incorrecta");
        } else {
            return new LoginResponse(response.getNickname(), response.getEmail(),
                    response.getPasswrd(), response.getPokemons(), response.getPokedex());
        }
    }

    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
        if (registerRequest == null) {
            return new RegisterResponse(false, "La petición no puede ser nula.");
        }

        // Validar campos vacíos o nulos
        if (registerRequest.getNickname() == null || registerRequest.getNickname().trim().isEmpty()
                || registerRequest.getEmail() == null || registerRequest.getEmail().trim().isEmpty()
                || registerRequest.getPasswrd() == null || registerRequest.getPasswrd().trim().isEmpty()) {
            return new RegisterResponse(false, "Todos los campos son obligatorios.");
        }

        // Validar que no contengan espacios
        if (registerRequest.getNickname().contains(" ")) {
            return new RegisterResponse(false, "El nickname no puede contener espacios.");
        }
        if (registerRequest.getEmail().contains(" ")) {
            return new RegisterResponse(false, "El correo electrónico no puede contener espacios.");
        }

        // Validar formato básico de correo
        if (!registerRequest.getEmail().contains("@") || !registerRequest.getEmail().contains(".")) {
            return new RegisterResponse(false, "El formato del correo electrónico no es válido.");
        }

        // Validar longitud de la contraseña
        if (registerRequest.getPasswrd().length() < 8) {
            return new RegisterResponse(false, "La contraseña debe tener al menos 8 caracteres.");
        }
        return userRepo.saveNewUser(registerRequest);
    }

    public List<UserPokemonResponse> getUserPokemons(String nickname) {
        return userRepo.findUserPokemonsByNickname(nickname);
    }
}
