import java.util.List;
import java.util.ArrayList;

class Trace<T> {

    private List<T> history;
    private T object;

    private Trace(T object, List<T> history) {
        this.history = history;
        this.object = object;
    }

    public static <T> Trace of(T... objects) {
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

    public Trace back(int n) {
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

    public Trace map(Function function) {
        T objectNew = function(this.get());
        List<T> historyNew = new ArrayList<>(this.history());
        historyNew.add(objectNew);
        return new Trace<T>(objectNew, historyNew);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Trace) {
            Trace trace = (Trace) obj;
            return ((this.get().equals(trace.get())) && 
                    (this.history().equals(trace.history())));
        } else {
            return false;
        }
    }


}
