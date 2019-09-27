/**
 * This class inherits form food and is used to create a Tuna object.
 */
public class Tuna extends Food {

    /**
     * Constructor method to create a Tuna object.
     */
    public Tuna(String brand) {
        super(brand);
    }

    /**
     * Method that returns the name of this Tuna.
     */
    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), "tuna");
    }

}
