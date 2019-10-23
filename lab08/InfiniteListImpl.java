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

    private final Supplier<Optional<T>> head;
    private final Supplier<InfiniteListImpl<T>> tail;

    protected InfiniteListImpl(Supplier<Optional<T>> head, Supplier<InfiniteListImpl<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    public static <T> InfiniteListImpl<T> generate(Supplier<? extends T> s) {
        System.out.println("generate");
        return new InfiniteListImpl<T>(() -> Optional.of(s.get()), 
                () -> InfiniteListImpl.generate(s));
    }

    public static <T> InfiniteListImpl<T> iterate(T seed, Function<? super T, ? extends T> next) {
        System.out.println("iterate");
        return new InfiniteListImpl<T>(() -> Optional.of(seed), 
                () -> InfiniteListImpl.iterate(next.apply(seed), next));
    }

    public <R> InfiniteListImpl<R> map(Function<? super T, ? extends R> mapper) {
        System.out.println("map");
        return new InfiniteListImpl<R>(() -> head.get().map(mapper),
                () -> {
                    if (tail.get() instanceof EmptyList) {
                        return new EmptyList<>();
                    } else {
                        return tail.get().map(mapper);
                    }
        });
    }

    public InfiniteListImpl<T> filter(Predicate<? super T> predicate) {
        System.out.println("filter");
        return new InfiniteListImpl<T>(() -> head.get().filter(predicate),
                () -> {
                    if (tail.get() instanceof EmptyList) {
                        return new EmptyList<>();
                    } else {
                        return tail.get().filter(predicate);
                    }
        });
    }

    public void forEach(Consumer<? super T> action) {
        System.out.println("forEach");
        Optional<T> currHead = head.get();
        InfiniteListImpl<T> currTail = tail.get();
        while (true) {
            if (currHead.isPresent()) {
                T curr = currHead.get();
                action.accept(curr);
            }
            if (currTail.isEmptyList()) {
                break;
            }
            currHead = currTail.head.get();
            currTail = currTail.tail.get();
        }
        return;
    }

    public Object[] toArray() {
        List<T> list = new ArrayList<>();
        this.forEach(x -> list.add(x));
        return list.toArray();
    }

    public InfiniteListImpl<T> limit(long n) {
        System.out.println("limit");
        Optional<T> currHead = head.get();
        InfiniteListImpl<T> currTail = tail.get();
        if (currTail.isEmptyList()) {
            if (currHead.isEmpty()) {
                return new EmptyList<T>();
            }
            Optional<T> newHead = currHead;
            return new InfiniteListImpl<T>(() -> newHead,
                    () -> new EmptyList<T>());
        }
        if (n > 1 && currHead.isPresent()) {
            Optional<T> newHead = currHead;
            InfiniteListImpl<T> newTail = currTail;
            System.out.println(newHead.get());
            return new InfiniteListImpl<T>(() -> newHead,
                    () -> newTail.limit(n-1));
        } else if (currHead.isEmpty()) {
            Optional<T> newHead = currHead;
            InfiniteListImpl<T> newTail = currTail;
            return newTail.limit(n);
        } else if (n == 1) {
            Optional<T> newHead = currHead;
            return new InfiniteListImpl<T>(() -> newHead,
                    () -> new EmptyList<T>());
        } else {
            return new EmptyList<T>();
        }
    }

    public long count() {
        long count = 0;
        Optional<T> currHead = head.get();
        InfiniteListImpl<T> currTail = tail.get();
        while (true) {
            if (currHead.isPresent()) {
                count += 1;
            }
            if (currTail.isEmptyList()) {
                break;
            }
            currHead = currTail.head.get();
            currTail = currTail.tail.get();
        }
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
        System.out.println("takeWhile");
        Optional<T> currHead = head.get();
        InfiniteListImpl<T> currTail = tail.get();
        if (currTail.isEmptyList()) {
            if (currHead.isEmpty()) {
                return new EmptyList<T>();
            }
            Optional<T> newHead = currHead;
            return new InfiniteListImpl<T>(() -> newHead,
                    () -> new EmptyList<T>());
        }
        if (currHead.isPresent()) {
            if (predicate.test(currHead.get())) {
                Optional<T> newHead = currHead;
                InfiniteListImpl<T> newTail = currTail;
                return new InfiniteListImpl<T>(() -> newHead,
                        () -> newTail.takeWhile(predicate));
            }
            else {
                return new EmptyList<T>();
            }
        } else {
            Optional<T> newHead = currHead;
            InfiniteListImpl<T> newTail = currTail;
            return new InfiniteListImpl<T>(() -> newHead,
                    () -> newTail.takeWhile(predicate));
        }
    }

//        if (n > 1 && currHead.isPresent()) {
//            Optional<T> newHead = currHead;
//            InfiniteListImpl<T> newTail = currTail;
//            return new InfiniteListImpl<T>(() -> newHead,
//                    () -> newTail.limit(n-1));
//        } else if (currHead.isEmpty()) {
//            Optional<T> newHead = currHead;
//            InfiniteListImpl<T> newTail = currTail;
//            return new InfiniteListImpl<T>(() -> newHead,
//                    () -> newTail.limit(n));
//        } else if (n == 1) {
//            Optional<T> newHead = currHead;
//            return new InfiniteListImpl<T>(() -> newHead,
//                    () -> new EmptyList<T>());
//        } else {
//            return new EmptyList<T>();
//        }
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
