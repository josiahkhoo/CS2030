import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

class ChildTrace<T> extends Trace<T> {
    
    protected ChildTrace(T object, List<T> history) {
        super(object, history);
    }
    
    @SafeVarargs
    public static <T> ChildTrace<T> of(T... objects) {
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
        return new ChildTrace<T>(objectInitial, history);
    }
    
    @Override
    public ChildTrace<T> back(int n) {
        int historyBackSize = this.history().size() - n;
        if (historyBackSize < 1) {
            historyBackSize = 1;
        }
        T objectBack = this.history().get(historyBackSize - 1);
        List<T> historyBack = new ArrayList<>(); 
        for (int i = 0; i < historyBackSize; i++) {
            historyBack.add(this.history().get(i));
        }
        return new ChildTrace<T>(objectBack, historyBack);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <R> ChildTrace<R> map(Function<? super T, ? extends R> function) {
        R objectNew = function.apply(this.get());
        List<R> historyNew = new ArrayList<>();
        for (T object : this.history()) {
            historyNew.add((R) object);
        }
        historyNew.add(objectNew);
        return new ChildTrace<R>(objectNew, historyNew);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof ChildTrace) {
            ChildTrace<?> trace = (ChildTrace) obj;
            return ((this.get().equals(trace.get())) && 
                    (this.history().equals(trace.history())));
        } else {
            return false;
        }
    }


}

