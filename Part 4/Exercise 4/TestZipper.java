import java.util.Arrays;
import java.util.List;

// This handler prints each item and returns Void
class PrintHandler<T> implements Handler<T, Void> {
    @Override
    public Void handle(T item) {
        System.out.println(item);
        return null; // Since the return type is Void, we return null
    }
}

public class TestZipper {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 20, 30, 40, 50);
        Zipper<Integer> zipper = new Zipper<>(numbers);

        // Traverse using PrintHandler
        zipper.traverse(new PrintHandler<>());
    }
}

