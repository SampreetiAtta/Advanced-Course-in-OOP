import java.util.ArrayList;
import java.util.List;

public class Zipper<T> {
    private final List<T> items;

    public Zipper(List<T> items) {
        this.items = items;
    }

    // Generic traverse method using the new Handler<T, X> interface
    public <X> List<X> traverse(Handler<T, X> handler) {
        List<X> result = new ArrayList<>();
        for (T item : items) {
            result.add(handler.handle(item));
        }
        return result;
    }
}

