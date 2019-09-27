/**
 * This class is used to create a Food object that will be inherited from.
 */
public class Food {

    private final String brand;

    /**
     * Constructor method to create a Food object.
     */
    public Food(String brand) {
        this.brand = brand;
    }

    /**
     * Method that returns the brand of this Food object.
     */
    public String getBrand() {
        return this.brand;
    }

    /**
     * Method that returns the brand name of this Food object.
     */
    @Override
    public String toString() {
        return this.brand;
    }

}
