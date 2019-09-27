/**
 * This class inherits from Food and is used to create a Chocolate object.
 */
public class Chocolate extends Food {

    private final String colour;

    /**
     * Constructor method to create a Chocolate object.
     */
    public Chocolate(String brand, String colour) {
        super(brand);
        this.colour = colour;
    }

    /**
     * Method that return the name of this Chocolate.
     */
    public String toString() {
        return String.format("%s %s %s", super.toString(), this.colour, 
                "chocolate");
    }

}
