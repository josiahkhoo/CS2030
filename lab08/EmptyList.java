package cs2030.mystream;

import java.util.Optional;

public class EmptyList<T> extends InfiniteListImpl<T> {

    public EmptyList() {
        super(() -> Optional.empty(),
                () -> new EmptyList<T>());
    }

}
