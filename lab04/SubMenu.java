import java.util.ArrayList;
import java.util.List;

public class SubMenu {

    private String type;
    private List<Food> foods;

    public SubMenu(String type) {
        this.type = type;
        this.foods = new ArrayList<>();
    }

    public String getType() {
        return this.type;
    }

    public List<Food> getFoods() {
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
        

        

