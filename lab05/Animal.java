/**
 * This class is used to create an Animal object that will be inherited from.
 */
public class Animal { 

    private final String name;
    protected int appetite;
    private final String sound;
    
    /**
     * Constructor method to create an Animal object.
     */
    public Animal(String name, int appetite, String sound) {
        this.name = name;
        this.appetite = appetite;
        this.sound = sound;
    }

    /**
     * Method that returns the name of this animal.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Method that returns the appetite left of this animal.
     */
    public int appetiteLeft() {
        return this.appetite;
    }

    /**
     * Method that returns the sound of this animal.
     */
    public String makeNoise() {
        return this.sound;
    }

    /**
     * Method that returns the greet of this animal.
     */
    public void greet() {
        String output = String.format("%s says %s", this.getName(), this.makeNoise());
        System.out.println(output);
    }

    /**
     * Method that makes the animal eat a specified food.
     */
    public void eat(Food food) throws CannotEatException {
    }

    /**
     * Method that override toString() to return the name of this animal.
     */
    @Override
    public String toString() {
        return this.getName();
    }

}

   



