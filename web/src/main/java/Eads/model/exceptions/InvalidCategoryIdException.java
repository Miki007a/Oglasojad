package Eads.model.exceptions;

public class InvalidCategoryIdException extends RuntimeException {
    public InvalidCategoryIdException(Long id) {
        super(String.format("No category with Id = %d found.", id));
    }
}
