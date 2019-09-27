/**
 * This class inherits from food and is used to create a Cheese object.
 */
public class Cheese extends Food {

    /**
     * Constructor method to create a Cheese object.
     */
    public Cheese(String brand) {
        super(brand);
    }

    /**
     * Method that returns the name of this Cheese.
     */
    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), "cheese");
    }

}
