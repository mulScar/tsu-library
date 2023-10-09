package tsuprojects.tsulibrary.exception;


public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = -4222552071079688649L;

    public BadRequestException(String message) {
        super(message);
    }
}
