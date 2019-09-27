/**
 * This class inherits from Animal class and is used to create a Dog object.
 */
public class Dog extends Animal {
    
    private int greetCount = 1;

    /**
     * Constructor method to create a Dog object.
     */
    public Dog(String name, int appetite, String sound) {
        super(name, appetite, sound);
    }

    /**
     * Method that makes the Dog greet.
     */
    @Override
    public void greet() {
        String output = String.format("%s says ", this.getName());
        for (int i = 0; i < greetCount; i++) {
            output += this.makeNoise();
        }
        greetCount++;
        System.out.println(output);
    }

    /**
     * Method that makes this dog eat a Food class object.
     */
    public void eat(Food food) throws CannotEatException {
        if (food instanceof Chocolate) {
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
