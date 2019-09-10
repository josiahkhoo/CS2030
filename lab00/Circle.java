public class Circle {
    private double radius;
    private Point centre;

    private Circle(Point centre, double radius) {
        this.radius = radius;
        this.centre = centre;
    }

    public static Circle getCircle(Point centre, double radius) {
        if (radius <= 0) {
            return null;
        } else {
            return new Circle(centre, radius);
        }
    }

    @Override
    public String toString() {
        return "circle of radius " + radius + " centered at " + centre;
    }
}



    
