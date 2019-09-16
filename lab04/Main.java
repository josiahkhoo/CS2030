import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();

        while(sc.next().equals("add")) {
            String type = sc.next();
            String name = sc.next();
            if (type.equals("Combo")) {
                List<Integer> intList = new ArrayList<>();
                while (sc.hasNextInt()) {
                    int n = sc.nextInt();
                    intList.add(n);
                }
                menu.add(type, name, intList);
            } else {
                int price = sc.nextInt();
                menu.add(type, name, price);
            }
        }
        menu.print();
        
        List<Integer> intList = new ArrayList<>();

        while (sc.hasNext()) {
            int n = sc.nextInt();
            intList.add(n);
        }

        int[] intArray = getIntArray(intList);
        Order order = new Order(menu);
        order.add(intArray);
        System.out.println("\n--- Order ---");
        System.out.println(order);

        }

    public static int[] getIntArray(List<Integer> intList) {
        int[] intArray = new int[intList.size()];
        int track = 0;
        for (int n : intList) {
            intArray[track++] = n;
        }
        return intArray;
        }

}

