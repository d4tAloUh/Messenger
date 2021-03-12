package messenger.backend.utils;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Response<T> {

    private final String message;
    private final T data;

    public static <T> Response<T> error(String errorMessage) {
        return new Response<>(errorMessage, null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>("Success", data);
    }

    public static <T> Response<T> of(String message, T data) {
        return new Response<>(message, data);
    }

}
