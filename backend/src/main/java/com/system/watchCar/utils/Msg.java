package com.system.watchCar.utils;

import com.sun.istack.NotNull;

public class Msg {

    private final static String TAG = "**********   WatchCar   **********\n";
    private final static String LINE = "\n**********************************";

    public static void System(@NotNull String msg, @NotNull Class clazz) {
        System.out.println(TAG.concat(msg).concat(" - ").concat(clazz.getSimpleName()).concat(LINE));
    }

    public static void Erro(@NotNull String msg, @NotNull Class clazz) {
        System.err.println(TAG.concat(msg).concat(" - ").concat(clazz.getSimpleName()).concat(LINE));
    }

    public static void Erro(@NotNull Exception exception) {
        System.err.println(TAG.concat(exception.getMessage()).concat(LINE));
    }
}
