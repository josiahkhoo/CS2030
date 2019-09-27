/**
 * This class is thrown when an animal
 * cannot eat a food.
 */
class CannotEatException extends Exception {
   
    private final String message;
    private final String type;
    
    /**
     * Constructor for the CannotEatException.
     */
    public CannotEatException(String message, String type) {
        this.message = message;
        this.type = type;
    }

    /**
     * This method returns the message of the exception.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * This method returns the type of exception.
     */
    public String getType() {
        return this.type;
    }

}
