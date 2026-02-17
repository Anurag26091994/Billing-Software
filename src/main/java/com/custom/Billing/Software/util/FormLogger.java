package com.custom.Billing.Software.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormLogger {
    private static String getCallerDetails() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 3) {
            StackTraceElement caller = stackTrace[3];
            return String.format("[%s:%s:%d]", caller.getClassName(), caller.getMethodName(), caller.getLineNumber());
        }
        return "[Unknown Source]";
    }

    private static Logger getLoggerForCaller() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 3) {
            return LoggerFactory.getLogger(stackTrace[3].getClassName());
        }
        return LoggerFactory.getLogger(FormLogger.class);
    }

    private static String formatMessage(String message, Object... details) {
        if (details == null || details.length == 0) return message;
        StringBuilder sb = new StringBuilder(message);
        sb.append(" ");
        for (Object detail : details) {
            sb.append(detail).append(": ");
        }
        return sb.toString();
    }

    // ------------------ INFO ------------------

    public static void info(String message) {
        getLoggerForCaller().info(getCallerDetails() + " - " + message);
    }

    public static void info(String message, Object... variables) {
        getLoggerForCaller().info(getCallerDetails() + " - " + formatMessage(message, variables));
    }

    public static void info(String message, Throwable throwable) {
        getLoggerForCaller().info(getCallerDetails() + " - " + message, throwable);
    }

    public static void info(String message, Throwable throwable, Object... variables) {
        getLoggerForCaller().info(getCallerDetails() + " - " + formatMessage(message, variables), throwable);
    }

    // ------------------ DEBUG ------------------

    public static void debug(String message) {
        getLoggerForCaller().debug(getCallerDetails() + " - " + message);
    }

    public static void debug(String message, Object... variables) {
        getLoggerForCaller().debug(getCallerDetails() + " - " + formatMessage(message, variables));
    }

    public static void debug(String message, Throwable throwable) {
        getLoggerForCaller().debug(getCallerDetails() + " - " + message, throwable);
    }

    public static void debug(String message, Throwable throwable, Object... variables) {
        getLoggerForCaller().debug(getCallerDetails() + " - " + formatMessage(message, variables), throwable);
    }

    // ------------------ WARN ------------------

    public static void warn(String message) {
        getLoggerForCaller().warn(getCallerDetails() + " - " + message);
    }

    public static void warn(String message, Object... variables) {
        getLoggerForCaller().warn(getCallerDetails() + " - " + formatMessage(message, variables));
    }

    public static void warn(String message, Throwable throwable) {
        getLoggerForCaller().warn(getCallerDetails() + " - " + message, throwable);
    }

    public static void warn(String message, Throwable throwable, Object... variables) {
        getLoggerForCaller().warn(getCallerDetails() + " - " + formatMessage(message, variables), throwable);
    }

    // ------------------ ERROR ------------------

    public static void error(String message) {
        getLoggerForCaller().error(getCallerDetails() + " - " + message);
    }

    public static void error(String message, Object... variables) {
        getLoggerForCaller().error(getCallerDetails() + " - " + formatMessage(message, variables));
    }

    public static void error(String message, Throwable throwable) {
        getLoggerForCaller().error(getCallerDetails() + " - " + message, throwable);
    }

    public static void error(String message, Throwable throwable, Object... variables) {
        getLoggerForCaller().error(getCallerDetails() + " - " + formatMessage(message, variables), throwable);
    }

    // ------------------ TRACE ------------------

    public static void trace(String message) {
        getLoggerForCaller().trace(getCallerDetails() + " - " + message);
    }

    public static void trace(String message, Object... variables) {
        getLoggerForCaller().trace(getCallerDetails() + " - " + formatMessage(message, variables));
    }

    public static void trace(String message, Throwable throwable) {
        getLoggerForCaller().trace(getCallerDetails() + " - " + message, throwable);
    }

    public static void trace(String message, Throwable throwable, Object... variables) {
        getLoggerForCaller().trace(getCallerDetails() + " - " + formatMessage(message, variables), throwable);
    }

    // ------------------ GENERIC LOG ------------------

    public static void log(String message, Object... details) {
        info(message, details);
    }
}
