import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class LazyList<T extends Comparable<T>> {

    List<Lazy<T>> list;

    private LazyList(List<Lazy<T>> list) {
        this.list = list;
    }

    static <T extends Comparable<T>> LazyList<T> generate(int n, T seed, UnaryOperator<T> f) {
        return new LazyList<T>(
                Stream.iterate(Lazy.of(seed), x -> x.map(f))
                .limit(n)
                .collect(Collectors.toList())
                );
    }

    public T get(int i) {
        return this.list.get(i).get();
    }

    public int indexOf(T v) {
        return this.list.indexOf(Lazy.of(v));
    }
}
