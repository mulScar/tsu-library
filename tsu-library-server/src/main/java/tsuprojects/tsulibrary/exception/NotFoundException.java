package tsuprojects.tsulibrary.exception;


public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6235724169969400422L;

    public NotFoundException(String message) {
        super(message);
    }
}
