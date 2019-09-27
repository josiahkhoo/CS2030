/**
 * This class inherits from Animal class and is used to create a Cat object.
 */
public class Cat extends Animal {

    private final String colour;
    private boolean lazy;
    
    /**
     * Constructor method to create a Cat object.
     */
    public Cat(String name, int appetite, String colour) {
        super(name, appetite, "meow");
        this.colour = colour;
        this.lazy = false;
    }

    /**
     * Method that makes this Cat greet.
     */
    @Override
    public void greet() {
        String output = new String();
        if (lazy) {
            this.lazy = false;
            output += String.format("%s is lazy", this.toString());
        } else {
            this.lazy = true;
            output += String.format("%s says %s and gets lazy",
                    this.toString(), this.makeNoise());
        }
        System.out.println(output);
    }

    /**
     * Method that overrides the name of this animal.
     */
    @Override
    public String getName() {
        return String.format("%s(%s)", super.getName(), this.colour);
    }

    /**
     * Method that overrides the toString() method.
     */
    @Override
    public String toString() {
        return this.getName();
    }

    /**
     * Method that makes this cat eat a Food class object.
     */
    @Override
    public void eat(Food food) throws CannotEatException {
        if (food instanceof Cheese) {
            String message = String.format("%s cannot eat %s", this.getName(),
                    food);
            throw new CannotEatException(message, "cannot eat");
        } else if (this.appetiteLeft() == 0) {
            String message = String.format("%s cannot eat %s as it is full",
                    this.getName(), food);
            throw new CannotEatException(message, "full");
        } else {
            String message = String.format("%s eats %s", this.getName(),
                    food);
            this.appetite--;
            System.out.println(message);
        }
    }

}
