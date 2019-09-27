import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

/**
 * This class will store the animals and foods.
 */
class Farm {
    
    private List<Animal> animalList;
    private List<Food> foodList;
    
    /**
     * Constructor to create a Farm object.
     */
    public Farm() {
        this.animalList = new ArrayList<>();
        this.foodList = new ArrayList<>();
    }

    /**
     * This method executes instructions inside the farm.
     */
    public void runInstruction(String instruction) 
            throws IllegalInstructionException {
        String[] parsedInstruction = instruction.split(" ");
        String powerword = parsedInstruction[0];
        switch (powerword) {
            //creation of new animals takes in 5 args
            case "new":
                if (!(parsedInstruction[1].equals("cat")) && 
                        !(parsedInstruction[1].equals("dog"))) {
                    throw new IllegalInstructionException(
                            String.format("%s is not a valid species", 
                                parsedInstruction[1]));
                } else if (parsedInstruction.length != 5) {
                    throw new IllegalInstructionException("Too few arguments");
                } else if (parsedInstruction[1].equals("cat")) {
                    Animal animal = new Cat(parsedInstruction[2], 
                            Integer.parseInt(parsedInstruction[3]),
                            parsedInstruction[4]);
                    System.out.println(String.format("%s was created", animal));
                    animalList.add(animal);
                } else if (parsedInstruction[1].equals("dog")) {
                    Animal animal = new Dog(parsedInstruction[2],
                            Integer.parseInt(parsedInstruction[3]),
                            parsedInstruction[4]);
                    System.out.println(String.format("%s was created", animal));
                    animalList.add(animal);
                } else {
                    throw new IllegalInstructionException("Invalid arguments");
                }
                break;
            //creation of food items
            case "add":
                if ((parsedInstruction.length <= 2) && 
                        (parsedInstruction.length >= 5)) {
                    throw new IllegalInstructionException("Invalid arguments");
                        }
                String foodType = parsedInstruction[1];
                Food food = null;
                if (foodType.equals("chocolate")) {
                    if (parsedInstruction.length != 4) {
                        throw new IllegalInstructionException(
                                "Too few arguments");
                    } else {
                        food = new Chocolate(parsedInstruction[2], 
                                parsedInstruction[3]);
                        System.out.println(String.format("%s was added", food));
                        foodList.add(food);
                        return;
                    }
                } else if (foodType.equals("cheese")) {
                    if (parsedInstruction.length != 3) {
                        throw new IllegalInstructionException(
                                "Too few arguments");
                    } else {
                        food = new Cheese(parsedInstruction[2]); 
                        System.out.println(String.format("%s was added", food));
                        foodList.add(food);
                        return;
                    }
                } else if (foodType.equals("tuna")) {
                    if (parsedInstruction.length != 3) {
                        throw new IllegalInstructionException(
                                "Too few arguments");
                    } else {
                        food = new Tuna(parsedInstruction[2]); 
                        System.out.println(String.format("%s was added", food));
                        foodList.add(food);
                        return;
                    }
                } else {
                    throw new IllegalInstructionException(String.format(
                                "%s is not a valid food type", foodType));
                }
            case "eat":
                makeEat();
                break;
            case "greet":
                makeGreet();
                break;
            default:
                throw new IllegalInstructionException(String.format("%s is not a valid instruction", instruction));
        }
        }

    /**
     * Make all animals eat.
     */
    private void makeEat() {
        Collections.sort(animalList, new Comparator<Animal>() {
            public int compare(Animal a1, Animal a2) {
                return a1.toString().compareToIgnoreCase(a2.toString());
            }
        });
        for (Animal animal : animalList) {
            List<Food> remainingFood = new ArrayList<>();
            for (Food food : foodList) {
                try {
                    animal.eat(food);
                } catch (CannotEatException e) {
                    remainingFood.add(food);
                }
            }
            foodList = remainingFood;
            if (foodList.isEmpty()) {
                break;
            }
        }
    }

    /**
     * Make all animals greet.
     */
    private void makeGreet() {
        Collections.sort(animalList, new Comparator<Animal>() {
            public int compare(Animal a1, Animal a2) {
                return a1.toString().compareToIgnoreCase(a2.toString());
            }
        });

        for (Animal animal : animalList) {
            animal.greet();
        }
    }

    /**
     * Returns the string output of the farm.
     */
    @Override
    public String toString() {
        Collections.sort(animalList, new Comparator<Animal>() {
            public int compare(Animal a1, Animal a2) {
                return a1.toString().compareToIgnoreCase(a2.toString());
            }
        }); 

        String output = new String();
        output += "Animals:";
        for (Animal animal : animalList) {
             output += "\n";
             output += animal.toString();
        }
        output += "\n\nFood:";
        for (Food food : foodList) {
            output += "\n";
            output += food.toString();
        }
        return output;
    }
}
               


