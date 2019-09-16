public class Food {
    private final int id;
    private final String type;
    private final String name;
    private final int price;

    public Food(int id, String type, String name, int price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public int getPrice() {
        return this.price;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        String string = new String();
        string = String.format("#%d %s: %s (%d)", id, type, name, price);
        return string;
    }

}
