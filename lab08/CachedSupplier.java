import java.util.function.Supplier;
import java.util.Optional;

public class CachedSupplier<T> {

    Supplier<T> supplier;
    Optional<T> cachedValue;

    public T get() {
        if (cachedValue.isEmpty()) {

        }
        return cachedValue.get();
    }
}            
