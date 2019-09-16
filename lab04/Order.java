import java.util.List;
import java.util.ArrayList;

public class Order {
    private Menu menu;
    private List<Food> foods;
    private static int total;

    public Order(Menu menu) {
        this.menu = menu;
        this.foods = new ArrayList<>();
        this.total = 0;
    }

    public Order add(int[] intArray) {
        Iterable<SubMenu> subMenus = this.menu.getBigMenu();
        for (int n : intArray) {
            for (SubMenu subMenu : subMenus) {
                Iterable<Food> foods = subMenu.getFoods();
                for (Food food : foods) {
                    if (food.getId() == n) {
                        this.foods.add(food);
                        this.total += food.getPrice();
                    }
                }
            }
        }
        return this;
    }

    public int getTotal() {
        return this.total;
    }

    public List<Food> getFoods() {
        return this.foods;
    }

    @Override
    public String toString() {
        String string = new String();
        string += "\n";
        Iterable<Food> foods = this.foods;
        for (Food food : foods) {
            string += food;
            string += "\n";
        }
        string += String.format("Total: %d", total);;
        return string;
    }
}
