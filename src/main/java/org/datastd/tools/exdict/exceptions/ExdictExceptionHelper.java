package org.datastd.tools.exdict.exceptions;

import org.datastd.tools.exdict.context.ExdictContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

public class ExdictExceptionHelper {

    private static final Logger logger = LoggerFactory.getLogger(ExdictExceptionHelper.class);

    public static ExdictException newException(String namespace, String group, String message) {
        return new ExdictException(namespace, group, message);
    }

    public static ExdictException newException(String namespace, String group, String message, Throwable ee) {
        return new ExdictException(namespace, group, message, ee);
    }

    public static ExdictException newException(String message, Throwable ee) {
        return new ExdictException(ExdictContext.defaultNamespace().getName(),
                ExdictContext.defaultNamespace().getGroupInfoHelper().getDefaultGroup(), message, ee);
    }

    public static <T extends Exception> T insertInStack(final String namespace, final String group,
                                                              final String message, final Class<T> exceptionClass) {
        try {
            return internalInsertInStack(namespace, group, message, exceptionClass);
        } catch (Exception e) {
            logger.error("can not wrap " + exceptionClass.getName(), e);
        }
        throw new RuntimeException("Use method only for exception with (String, Throwable) constructors.");
    }

    public static <T extends Exception> T tryInsertInStack(final String namespace, final String group,
                                                                 final String message, final Class<T> exceptionClass) {
        try {
            return internalInsertInStack(namespace, group, message, exceptionClass);
        } catch (Exception e) {
            logger.warn("can not wrap " + exceptionClass.getName(), e);
        }
        try {
            return exceptionClass.getConstructor(String.class).newInstance(message);
        } catch (Exception e) {
            logger.warn("can not create with constructor with message " + exceptionClass.getName(), e);
        }
        throw new RuntimeException("Use method only for exception with (String) or (String, Throwable) constructors.");
    }

    private static <T extends Exception> T internalInsertInStack(final String namespace, final String group,
                                              final String message, final Class<T> exceptionClass) throws Exception, RuntimeException {
        Constructor<T> constructor = null;
        constructor = exceptionClass.getConstructor(String.class, Throwable.class);
        final ExdictException ee = newException(namespace, group, message);
        return constructor.newInstance(message, ee);
    }

}
