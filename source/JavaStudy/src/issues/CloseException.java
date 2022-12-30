package issues;
public class CloseException extends Exception {
    private static final long serialVersionUID = 1L;

    CloseException(String msg) {
        super(msg);
    }
}
