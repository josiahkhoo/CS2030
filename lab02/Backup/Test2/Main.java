import java.util.Scanner;

class Main {
    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in); 
        int numberCruises = scanner.nextInt();
        Cruise[] cruises = new Cruise[numberCruises];
        for (int i = 0; i < numberCruises; i++) {
            String identifier = scanner.next();
            int arrivalTime = scanner.nextInt();
            if (identifier.charAt(0) == 'B') {
                int numLoadersRequired = scanner.nextInt();
                int serviceTimeRequired = scanner.nextInt();
                cruises[i] = new BigCruise(identifier, arrivalTime,
                        numLoadersRequired, serviceTimeRequired);
            } else {
                cruises[i] = new Cruise(identifier, arrivalTime);
            }
        }
        int totalLoadersUnoptimised = getTotalLoaders(cruises);
        Loader[] loaders = new Loader[totalLoadersUnoptimised];
        for (int i = 0; i < totalLoadersUnoptimised; i++) {
            loaders[i] = new Loader(i + 1);
        }
        loadCruises(loaders, cruises);
    }

    public static int getTotalLoaders(Cruise[] cruises) {
        int total = 0;
        for (Cruise cruise : cruises) {
            total += cruise.getNumLoadersRequired();
        }
        return total;
    }

    public static void loadCruises(Loader[] loaders, Cruise[] cruises) {
        for (Cruise cruise : cruises) {
            int numLoadersRequired = cruise.getNumLoadersRequired();
            int i = 0;
            while (numLoadersRequired > 0) {
                Loader updatedLoader = loaders[i].serve(cruise);
                if (updatedLoader == null) {
                    i++;
                } else {
                    loaders[i] = updatedLoader;
                    numLoadersRequired--;
                    System.out.println(loaders[i]);
                }
            }
        }
    }
}
    

        
