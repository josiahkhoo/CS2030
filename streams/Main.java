import java.util.stream.IntStream;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.function.Function;

class Main {

    public static boolean isPerfect(int n) {
        int sumDivisors = IntStream.range(1, n).filter(x -> n % x == 0).sum();
        return (n == sumDivisors);
    }
    
    public static boolean isSquare(int n) {
        int validSquare = IntStream.range(1, n+1).filter(x -> x * x == n).sum();
        return (validSquare != 0);
    }
    
    public static long countRepeats(int[] array) {
        int n = array.length;
        //checks !base case
        long repeats = IntStream
        .range(1, n-1)
        .filter(x -> (array[x] != array[x-1]) && (array[x] == array[x+1]))
        .count();
        //checks base case
        if (array[0] == array[1]) {
            repeats += 1;
        }
        return repeats;
   }

   public static OptionalDouble variance(int[] array) {
       int arrLength = array.length;
       if (arrLength <= 1) {
           return OptionalDouble.empty();
       }
       double mean = IntStream.of(array).average().getAsDouble();
       Function<Integer, Double> f = x -> ((x - mean) * (x - mean));
       return OptionalDouble.of(IntStream.of(array).mapToDouble(x -> f.apply(x))
               .sum() / (arrLength - 1));
   }

   public static boolean isPrime(int n) {
       if (IntStream.range(2, n).filter(x -> n % x == 0).count() == 0) {
           return true;
       } else {
           return false;
       }
   }
    
   public static IntStream primeFactors(int n) {
       return IntStream.range(2, n+1).filter(x -> n % x == 0).filter(x -> isPrime(x));
   }
}
