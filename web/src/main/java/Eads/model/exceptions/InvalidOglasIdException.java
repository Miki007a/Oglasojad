package Eads.model.exceptions;

public class InvalidOglasIdException extends RuntimeException {
    public InvalidOglasIdException(Long id) {
        super(String.format("Нема оглас со Id = %d најдено.", id));
    }
}
