package messenger.backend.utils;


import lombok.Value;

@Value(staticConstructor = "of")
public class Response<T> {

    String error;
    T data;

}
