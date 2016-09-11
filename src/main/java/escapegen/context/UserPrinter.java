package escapegen.context;

/**
 * Created by vita on 9/1/16.
 */
public interface UserPrinter<T> {
    void print(T data);
    void println(T data);
}
