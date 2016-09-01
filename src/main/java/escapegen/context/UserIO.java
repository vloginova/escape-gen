package escapegen.context;

/**
 * Created by vita on 9/1/16.
 */
public interface UserIO<T> {
    void write(T data);
    T read();
}
