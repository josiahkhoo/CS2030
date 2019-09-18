import java.util.ArrayList;
import java.util.List;

public class Menu {
    protected List<SubMenu> bigMenu;
    private static int count = 0;

    public Menu() {
        this.bigMenu = new ArrayList<>();
    }

    public Combo add(String type, String name, List<Integer> intList) {
        Order order = new Order(this);
        int[] intArray = new int[intList.size()];
        int track = 0;
        for (int n : intList) {
            intArray[track++] = n;
        }
        order.add(intArray);
        Combo combo = new Combo(count++, name, order);
        for (SubMenu availType : bigMenu) {
            if (availType.toString() == combo.getType()) {
                availType.add(combo);
                System.out.println(combo);
                return combo;
            }
        }
        SubMenu subMenu = new SubMenu(type);
        subMenu.add(combo);
        bigMenu.add(subMenu);
        return combo;
    }

    public Food add(String type, String name, int price) {
        Food food = new Food(count++, type, name, price);
        for (SubMenu availType : bigMenu) {
            if (availType.getType().equals(food.getType())) {
                availType.add(food);
                return food;
            }
        }
        SubMenu subMenu = new SubMenu(type);
        subMenu.add(food);
        bigMenu.add(subMenu);
        return food;
    }

    public Menu print() {
        Iterable<SubMenu> subMenus = bigMenu;
        for (SubMenu subMenu : subMenus) {
            if (subMenu.getType().equals("Combo")) {
                continue;
            }
            Iterable<Food> foods = subMenu.getFoods();
            for (Food food : foods) {
                System.out.println(food);
            }
        }
        for (SubMenu subMenu : subMenus) {
            if (subMenu.getType().equals("Combo")) {
                Iterable<Food> foods = subMenu.getFoods();
                for (Food food : foods) {
                    System.out.println(food);
                }
            }
        }
        return this;
    }

    public List<SubMenu> getBigMenu() {
        return this.bigMenu;
    }

}
        
    
