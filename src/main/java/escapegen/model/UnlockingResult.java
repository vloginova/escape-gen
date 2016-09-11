package escapegen.model;

import lombok.Getter;

import java.util.function.Function;

/**
 * Created by vita on 9/11/16.
 */
public class UnlockingResult {
    @Getter
    private final Function<String, UnlockingResult> callback;
    private final State state;

    public enum State { LOCKED, INTERMEDIATE, UNLOCKED }
    public static final UnlockingResult SUCCESS = new UnlockingResult(State.UNLOCKED);

    public static final UnlockingResult FAIL = new UnlockingResult(State.LOCKED);

    private UnlockingResult(State state) {
        this.state = state;
        callback = null;
    }

    public UnlockingResult(Function<String, UnlockingResult> callback) {
        state = State.INTERMEDIATE;
        this.callback = callback;
    }

    public boolean isUnlocked() {
        return state == State.UNLOCKED;
    }

    public boolean isIntermediate() {
        return state == State.INTERMEDIATE;
    }
}
