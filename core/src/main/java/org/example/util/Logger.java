package org.example.util;

public class Logger {
    // 打印日志字体颜色ANSI转译码
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static String fg = ANSI_RESET;
    public static String PREFIX = "X";

    public static void trace(String msg) {
        log("!T".concat(PREFIX).concat(" ").concat(msg));
    }

    private static void log(String msg) {
        System.out.println(fg.concat(msg).concat(ANSI_RESET));
    }

    public static void clazz(String msg) {
        log("!C".concat(PREFIX).concat(" ").concat(msg));
    }

    public static void debug(String tpl) {
        log("!D " + tpl);
    }
}
