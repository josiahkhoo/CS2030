import java.util.Scanner;

public class Main {
    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in);
        int[] faceArray = new int[54];

        populateFaceArray(faceArray, scanner);
        int[][][] intArray = makeIntArray(faceArray);
        Rubik rubik = new Rubik(intArray);

        Rubik resultRubik = manipulateRubik(rubik, scanner);
        System.out.println(resultRubik);
    }

    public static void populateFaceArray(int[] faceArray, Scanner scanner) {
        for (int i = 0; i < 54; i++) {
            faceArray[i] = scanner.nextInt();
        }
    }

    public static int[][][] makeIntArray(int[] array) {
        int[][][] grid =  new int[6][3][3];
        int d = 0;
        for (int k = 0; k < 6; k++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    grid[k][i][j] = array[d++];
                }
            }
        }
        return grid;
    }

    public static Rubik manipulateRubik(Rubik rubik, Scanner scanner) {
        while (scanner.hasNext() == true) {
            String instruction = scanner.next();
            switch (instruction) {
                case "F":
                    rubik = new Rubik(rubik).right();
                    break;
                case "R":
                    rubik = new RubikRight(rubik).right();
                    break;
                case "U":
                    rubik = new RubikUp(rubik).right();
                    break;
                case "L":
                    rubik = new RubikLeft(rubik).right();
                    break;
                case "B":
                    rubik = new RubikBack(rubik).right();
                    break;
                case "D":
                    rubik = new RubikDown(rubik).right();
                    break;
                case "F\'":
                    rubik = new Rubik(rubik).left();
                    break;
                case "R\'":
                    rubik = new RubikRight(rubik).left();
                    break;
                case "U\'":
                    rubik = new RubikUp(rubik).left();
                    break;
                case "L\'":
                    rubik = new RubikLeft(rubik).left();
                    break;
                case "B\'":
                    rubik = new RubikBack(rubik).left();
                    break;
                case "D\'":
                    rubik = new RubikDown(rubik).left();
                    break;
                case "F2":
                    rubik = new Rubik(rubik).half();
                    break;
                case "R2":
                    rubik = new RubikRight(rubik).half();
                    break;
                case "U2":
                    rubik = new RubikUp(rubik).half();
                    break;
                case "L2":
                    rubik = new RubikLeft(rubik).half();
                    break;
                case "B2":
                    rubik = new RubikBack(rubik).half();
                    break;
                case "D2":
                    rubik = new RubikDown(rubik).half();
                    break;
                default:
                    break;
            }
        }
        return rubik;
    }
}
        
            
                    
                    
