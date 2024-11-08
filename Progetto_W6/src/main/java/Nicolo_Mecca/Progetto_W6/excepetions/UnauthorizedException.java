package Nicolo_Mecca.Progetto_W6.excepetions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
