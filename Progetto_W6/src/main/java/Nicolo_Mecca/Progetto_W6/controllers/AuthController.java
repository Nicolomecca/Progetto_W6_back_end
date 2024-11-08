package Nicolo_Mecca.Progetto_W6.controllers;


import Nicolo_Mecca.Progetto_W6.entities.User;
import Nicolo_Mecca.Progetto_W6.entities.UserRole;
import Nicolo_Mecca.Progetto_W6.excepetions.BadRequestException;
import Nicolo_Mecca.Progetto_W6.payloads.UserDTO;
import Nicolo_Mecca.Progetto_W6.payloads.UserLoginDTO;
import Nicolo_Mecca.Progetto_W6.payloads.UserLoginResponseDTO;
import Nicolo_Mecca.Progetto_W6.services.AuthService;
import Nicolo_Mecca.Progetto_W6.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
        return new UserLoginResponseDTO(authService.authenticateUser(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody @Validated UserDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Errori nel payload: " + message);
        }

        // Imposto di default il ruolo NORMAL_USER
        body = new UserDTO(body.email(), body.password(), UserRole.NORMAL_USER);
        return userService.save(body);
    }

    @PostMapping("/register/organizer")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerOrganizer(@RequestBody @Validated UserDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Errori nel payload: " + message);
        }

        // Imposto il ruolo EVENT_ORGANIZER
        body = new UserDTO(body.email(), body.password(), UserRole.EVENT_ORGANIZER);
        return userService.save(body);
    }
}