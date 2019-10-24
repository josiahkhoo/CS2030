package cs2030.mystream;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.Consumer;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class InfiniteListImpl<T> implements InfiniteList<T> {

    private final CachedSupplier<Optional<T>> head;
    private final Supplier<InfiniteListImpl<T>> tail;

    protected InfiniteListImpl(Supplier<Optional<T>> head, Supplier<InfiniteListImpl<T>> tail) {
        this.head = new CachedSupplier<Optional<T>>(head);
        this.tail = tail;
    }

    protected InfiniteListImpl(CachedSupplier<Optional<T>> head, Supplier<InfiniteListImpl<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    public static <T> InfiniteListImpl<T> generate(Supplier<? extends T> s) {
        return new InfiniteListImpl<T>(
                () -> Optional.of(s.get()), 
                () -> InfiniteListImpl.generate(s));
    }

    public static <T> InfiniteListImpl<T> iterate(T seed, Function<? super T, ? extends T> next) {
        return new InfiniteListImpl<T>(
                () -> Optional.of(seed), 
                () -> InfiniteListImpl.iterate(next.apply(seed), next));
    }

    public <R> InfiniteListImpl<R> map(Function<? super T, ? extends R> mapper) {
        if (this.isEmptyList()) {
            return new EmptyList<R>();
        }
        return new InfiniteListImpl<R>(
                () -> head.get().map(mapper),
                () -> tail.get().map(mapper));
    }

    public InfiniteListImpl<T> filter(Predicate<? super T> predicate) {
        if (this.isEmptyList()) {
            return new EmptyList<T>();
        }
        return new InfiniteListImpl<T>(
                () -> head.get().filter(predicate),
                () -> tail.get().filter(predicate));
    }

    public void forEach(Consumer<? super T> action) {
        if (this.isEmptyList()) {
            return;
        }
        Optional<T> currHead = head.get();
        if (currHead.isPresent()) {
            T curr = currHead.get();
            action.accept(curr);
        }
        InfiniteListImpl<T> currTail = tail.get();
        currTail.forEach(action);
        return;
    }

    public Object[] toArray() {
        List<T> list = new ArrayList<>();
        this.forEach(x -> list.add(x));
        return list.toArray();
    }

    public InfiniteListImpl<T> limit(long n) {
        if (this.isEmptyList() || (n <= 0)) {
            return new EmptyList<T>();
        }
        else  {
            return new InfiniteListImpl<T>(
                    head,
                    () -> { 
                        if (head.get().isPresent()) {
                            if (n == 1) {
                                return new EmptyList<T>();
                            }
                            return tail.get().limit(n - 1);
                        } else {
                            return tail.get().limit(n);
                        }});
        }
    }

    public long count() {
        long count = 0;
        Optional<T> currHead = head.get();
        if (currHead.isPresent()) {
            count += 1;
        }
        InfiniteListImpl<T> currTail = tail.get();
        if (currTail.isEmptyList()) {
            return count;
        }
        count += currTail.count();
        return count;
    }

    public Optional<T> reduce (BinaryOperator<T> accumulator) {
        Optional<T> object = Optional.empty();
        Optional<T> currHead = head.get();
        InfiniteListImpl<T> currTail = tail.get();
        boolean isFirst = true;
        while (true) {
            if (currHead.isPresent() && isFirst) {
                object = Optional.of(currHead.get());
                isFirst = false;
            } else if (currHead.isPresent()) {
                object = Optional.of(accumulator.apply(object.get(), currHead.get()));
            }
            if (currTail.isEmptyList()) {
                break;
            }
            currHead = currTail.head.get();
            currTail = currTail.tail.get();
        }
        return object;
    }

    public <U> U reduce (U identity, BiFunction<U, ? super T, U> accumulator) {
        U object = identity;
        Optional<T> currHead = head.get();
        InfiniteListImpl<T> currTail = tail.get();
        while (true) {
            if (currHead.isPresent()) {
                object = accumulator.apply(object, currHead.get());
            }
            if (currTail.isEmptyList()) {
                break;
            }
            currHead = currTail.head.get();
            currTail = currTail.tail.get();
        }
        return object;
    }

    public InfiniteListImpl<T> takeWhile(Predicate<? super T> predicate) {
        if (this.isEmptyList()) {
            return new EmptyList<T>();
        }
        CachedSupplier<Boolean> predicateTest = new CachedSupplier<>(() -> predicate.test(head.get().get()));
        return new InfiniteListImpl<T>(
                () -> {
                    if (head.get().isPresent()) {
                        if (predicateTest.get()) {
                            return head.get();
                        }
                    }
                    return Optional.empty();
                    },
                () -> {
                    if (head.get().isPresent()) {
                        if (predicateTest.get()) {
                            return tail.get().takeWhile(predicate);
                        } else {
                            return new EmptyList<T>();
                        }
                    } else {
                        return tail.get().takeWhile(predicate);
                    }
                });
    }

    public InfiniteListImpl<T> get() {
        if (this.head.get().isEmpty()) {
            return this.tail.get();
        }
        System.out.println(this.head.get().get());
        return this.tail.get();
    }

    public boolean isEmptyList() {
        if (this instanceof EmptyList) {
            return true;
        } else {
            return false;
        }
    }

}
