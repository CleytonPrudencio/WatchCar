package com.system.watchCar.dto.response;

public record Response<T>(boolean success, T content) {
    public static <T> Response<T> success(T content) {
        return new Response<>(true, content);
    }
    public static <T> Response<T> error(T content) {
        return new Response<>(false, content);
    }
}
