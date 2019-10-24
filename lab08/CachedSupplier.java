package cs2030.mystream;

import java.util.function.Supplier;
import java.util.Optional;

public class CachedSupplier<T> {

    Supplier<T> supplier;
    Optional<T> cachedValue;

    public CachedSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
        this.cachedValue = Optional.empty();
    } 

    public T get() {
        //System.out.println("get");
        if (cachedValue.isEmpty()) {
            cachedValue = Optional.of(supplier.get());
        } 
        return cachedValue.get();
    }
}            
