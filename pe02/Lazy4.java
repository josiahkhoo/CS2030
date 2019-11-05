import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.function.Predicate;

class Lazy<T> {

    private Supplier<T> supplier;
    private boolean computed;

    private Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
        this.computed = false;
    }

    private Lazy(T v) {
        this.supplier = (() -> v);
        this.computed = true;
    }

    static <T> Lazy<T> of(T v) {
        return new Lazy<T>(v);
    }

    static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<T>(supplier);
    }

    public void computed() {
        this.computed = true;
    }

    public T get() {
        T result = supplier.get();
        if (!(computed)) {
            this.supplier = (() -> result);
            this.computed();
            return result;
        } else {
            return result;
        }
    }

    public <R> Lazy<R> map(Function<T,R> function) {
        Supplier<R> mapSupplier = (() -> function.apply(this.get()));
        return new Lazy<R>(mapSupplier);
    }

    public <R> Lazy<R> flatMap(Function<T, Lazy<R>> function) {
        Supplier<R> flatMapSupplier = (() -> function.apply(this.get()).get());
        return new Lazy<R>(flatMapSupplier);
    }

    public <U,R> Lazy<R> combine(Lazy<U> otherLazy, BiFunction<T,U,R> function) {
        Supplier<R> combineSupplier= (() -> function.apply(this.get(), otherLazy.get()));
        return new Lazy<R>(combineSupplier);
    }

    public Lazy<Boolean> test(Predicate<T> predicate) {
        Supplier<Boolean> predicateSupplier = (() -> predicate.test(this.get()));
        return new Lazy<Boolean>(predicateSupplier);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (Lazy.class.isInstance(object)) {
            Lazy<?> otherLazy = (Lazy<?>) object;
            return (this.get() == otherLazy.get());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (computed) {
            return this.supplier.get().toString();
        } else {
            return "?";
        }
    }

}
