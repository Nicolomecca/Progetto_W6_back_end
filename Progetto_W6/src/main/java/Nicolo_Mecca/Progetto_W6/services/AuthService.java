package Nicolo_Mecca.Progetto_W6.services;

import Nicolo_Mecca.Progetto_W6.entities.User;
import Nicolo_Mecca.Progetto_W6.excepetions.UnauthorizedException;
import Nicolo_Mecca.Progetto_W6.payloads.UserLoginDTO;
import Nicolo_Mecca.Progetto_W6.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JWT jwt;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUser(UserLoginDTO body) {
        User found = userService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwt.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali non valide");
        }
    }

}
