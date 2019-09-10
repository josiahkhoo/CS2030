import java.util.Scanner;

class Main {
    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Point[] points = new Point[n];
        //n is the size of the array
        for (int i = 0; i < n; i++) {
            points[i] = new Point(scanner.nextDouble(), scanner.nextDouble());
        }
        int maximumCoverage = discCoverage(points);
        System.out.println("Maximum Disc Coverage: " + String.format("%d", maximumCoverage));
            
    }

    public static Circle createCircle(Point pointA, Point pointB, double radius) {
        if (pointA.getX() == pointB.getX() && pointA.getY() == pointB.getY()) {
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

    public static int discCoverage(Point[] points) {
        int maximumCoverage = 0;
        for (int i = 0; i < points.length-1; i++) {
            Point pointA = points[i];
            for (int j = i+1; j < points.length; j++) {
                Point pointB = points[j];
                Circle circle = createCircle(pointA, pointB, 1);        
                if (circle == null) {
                    continue;
                } else {
                    int count = 2;
                    for (Point point : points) {
                        if (point == pointA || point == pointB) {
                            continue;
                        } else if (point.distanceTo(circle.getCentre()) <= 1) {
                            count += 1;
                        } else {
                            continue;
                        }
                    }
                    if (count > maximumCoverage) {
                         maximumCoverage = count;
                    }
                }
            }   
        }
        return maximumCoverage;
    }
            
} 

        
