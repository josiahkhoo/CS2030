import java.util.Scanner;

class Main {
    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in); 
        Point pointA = new Point(scanner.nextDouble(), scanner.nextDouble());
        Point pointB = new Point(scanner.nextDouble(), scanner.nextDouble());
        double radius = scanner.nextDouble();
        
        Circle circle = createCircle(pointA, pointB, radius);
        if (circle != null) {
            System.out.println("Created: " + circle);
        } else {
            System.out.println("No valid circle can be created");
        }
    }

    public static Circle createCircle(Point pointA, Point pointB, double radius) {
        if (pointA.getX() == pointB.getX() && pointA.getX() == pointB.getY()) {
            return null;
        }
        Point midpoint = pointA.midPoint(pointB);
        double distanceToMidpoint = midpoint.distanceTo(pointA);
        if (distanceToMidpoint > radius) {
            return null;
        }
        double midpointToNewCentre = Math.sqrt(Math.pow(radius,2) - Math.pow(distanceToMidpoint,2));
        double thetaAtoB = pointA.angleTo(pointB);
        double thetaMidpointToCentre = Math.PI / 2 + thetaAtoB;
        Point newCentre = midpoint.moveTo(thetaMidpointToCentre, midpointToNewCentre);  
        return Circle.getCircle(newCentre, radius);
    }
} 

        
