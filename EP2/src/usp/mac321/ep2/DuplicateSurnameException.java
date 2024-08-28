package usp.mac321.ep2;

public class DuplicateSurnameException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public DuplicateSurnameException(String surname) {
        super("Two users with the same surname: " + surname);
    }
}