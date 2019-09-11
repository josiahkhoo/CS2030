import Java.util.Scanner;

public class Main {
    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in);
        int[] faceArray = new int[54];

        populateFaceArray(faceArray, scanner);
        int[][][] intArray = makeIntArray(faceArray);
        Rubik rubik = new Rubik(intArray);

        Rubik rubik = manipulateRubik(rubik, scanner);
        System.out.println(rubik);
        
    }

    public static void  populateFaceArray(int[] faceArray, Scanner scanner) {
        for (i = 0; i < 54; i++) {
            faceArray[0] = scanner.nextInt();
        }
    }

    public static int[][][] makeIntArray(int[] array) {
        int[][][] grid =  new int[6][3][3];
        int d = 0;
        for (int k = 0; k < 6; k++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    grid[k][i][j] = faceArray[d++];
                }
            }
        }
        return grid;
    }

    public static Rubik manipulateRubik(Rubik rubik, Scanner scanner) {
        while (scanner.hasNext() == true) {
            String instruction = scanner.next();
            switch (instruction) {
                case 'F':
                    break;
                case 'R':
                    break;
                case 'U':
                    break;
                case 'L':
                    break;
                case 'B':
                    break;
                case 'D':
                    break;
                case "F\'":
                    break;
                case "R\'":
                    break;
                case "U\'":
                    break;
                case "L\'":
                    break;
                case "B\'":
                    break;
                case "D\'":
                    break;
                case "F2":
                    break;
                case "R2":
                    break;
                case "U2":
                    break;
                case "L2":
                    break;
                case "B2":
                    break;
                case "D2":
                    break;
                default:
                    break;
            }
        }
        return rubik;
    }
}
        
            
                    
                    
