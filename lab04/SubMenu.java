import java.util.ArrayList;
import java.util.List;

public class SubMenu {

    private String type;
    private List<? extends Food> foods;

    public SubMenu(String type) {
        this.type = type;
        this.foods = new ArrayList<>();
    }

    public List<? extends Food> getFoods() {
        return this.foods;
    }

    public SubMenu add(Food food) {
        foods.add(food);
        return this;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
        

        

