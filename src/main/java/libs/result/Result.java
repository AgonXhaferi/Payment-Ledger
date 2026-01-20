package libs.result;

import lombok.Getter;

public class Result<T, E> {

    private final T value;
    @Getter
    private final E error;
    @Getter
    private final boolean isSuccess;

    private Result(T value, E error, boolean isSuccess) {
        this.value = value;
        this.error = error;
        this.isSuccess = isSuccess;
    }

    public static <T, E> Result<T, E> ok(T value) {
        return new Result<>(value, null, true);
    }

    public static <T, E> Result<T, E> err(E error) {
        return new Result<>(null, error, false);
    }

    public boolean isFailure() {
        return !isSuccess;
    }

    public T unwrap() {
        if (!isSuccess) throw new IllegalStateException("Called unwrap on an Error Result");
        return value;
    }
}