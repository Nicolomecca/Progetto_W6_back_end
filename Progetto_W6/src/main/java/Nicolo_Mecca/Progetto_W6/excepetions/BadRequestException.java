package Nicolo_Mecca.Progetto_W6.excepetions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String msg) {
        super(msg);
    }
}
