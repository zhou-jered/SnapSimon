package me.local.utils;

public class ThreadLog {
    public static void log(String pattern, Object... objs) {
        pattern = pattern.replace("{}", "%s");
        System.out.println(String.format("[%s] " + pattern, Thread.currentThread().getName(), objs));
    }
}
