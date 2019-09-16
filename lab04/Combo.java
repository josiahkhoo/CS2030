import java.util.ArrayList;
import java.util.List;

public class Combo extends Food {

    private Order order;

    public Combo(int id, String name, Order order) {
        super(id, "Combo", name, order.getTotal());
        this.order = order;
    }

    @Override
    public String toString() {
        String string = new String();
        string += super.toString();
        Iterable<Food> foods = this.order.getFoods();
        for (Food food : foods) {
            string += "\n   ";
            string += food;
        }
        return string;
    }
            
}
