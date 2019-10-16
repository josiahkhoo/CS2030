import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

class Trace<T> {

    private List<T> history;
    private T object;

    protected Trace(T object, List<T> history) {
        this.history = history;
        this.object = object;
    }

    @SafeVarargs
    public static <T> Trace<T> of(T... objects) {
        T objectInitial = objects[0];
        List<T> history = new ArrayList<>();
        int arglength = objects.length;
        if (arglength > 1) {
            T[] objectsHistory = Arrays.copyOfRange(objects, 1, arglength);
            for (T object : objectsHistory) {
                history.add(object);
            }
        }
        history.add(objectInitial);
        return new Trace<T>(objectInitial, history);
    }

    public T get() {
        return object;
    }

    public List<T> history() {
        return history;
    }

    public Trace<T> back(int n) {
        int historyBackSize = history.size() - n;
        if (historyBackSize < 1) {
            historyBackSize = 1;
        }
        T objectBack = history.get(historyBackSize - 1);
        List<T> historyBack = new ArrayList<>(); 
        for (int i = 0; i < historyBackSize; i++) {
            historyBack.add(history.get(i));
        }
        return new Trace<T>(objectBack, historyBack);
    }

    @SuppressWarnings("unchecked")
    public <R> Trace<R> map(Function<? super T, ? extends R> function) {
        R objectNew = function.apply(this.get());
        List<R> historyNew = new ArrayList<>();
        for (T object : history) {
            historyNew.add((R) object);
        }
        historyNew.add(objectNew);
        return new Trace<R>(objectNew, historyNew);
    }

    @SuppressWarnings("unchecked")
    public Trace<T> flatMap(Function<? super T, ? extends Trace<? extends T>> function) {
        Trace<? extends T> newTrace = function.apply(this.get());
        //removes last object
        this.history.remove(this.get());
        //appends the new items into the history
        this.history.addAll(newTrace.history());
        return new Trace<T>(newTrace.get(), this.history());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Trace) {
            Trace<?> trace = (Trace) obj;
            return ((this.get().equals(trace.get())) && 
                    (this.history().equals(trace.history())));
        } else {
            return false;
        }
    }


}
