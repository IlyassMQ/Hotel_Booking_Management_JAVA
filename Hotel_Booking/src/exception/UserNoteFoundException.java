package exception;

public class UserNoteFoundException extends RuntimeException {
    public UserNoteFoundException(String message) {
        super(message);
    }
    public UserNoteFoundException(){
        super("User Note founde");
    }
}
