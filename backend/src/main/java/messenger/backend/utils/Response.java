package messenger.backend.utils;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Response<T> {

    private final String error;
    private final T data;

    public static <T> Response<T> error(String errorMessage) {
        return new Response<>(errorMessage, null);
    }

    public static <T> Response<T> data(T data) {
        return new Response<>(null, data);
    }

}
