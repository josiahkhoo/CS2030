import java.util.Scanner;

class Main {
    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in); 
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
        scanner.close();
    }
}
