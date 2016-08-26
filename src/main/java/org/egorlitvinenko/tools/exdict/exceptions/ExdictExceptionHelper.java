package org.egorlitvinenko.tools.exdict.exceptions;

import java.lang.reflect.Constructor;

import org.egorlitvinenko.tools.exdict.ExdictContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExdictExceptionHelper {

    private static final Logger logger = LoggerFactory.getLogger(ExdictExceptionHelper.class);

    public static final ExdictException newException(String namespace, String group, String message) {
	return new ExdictException(namespace, group, message);
    }

    public static final ExdictException newException(String namespace, String group, String message, Throwable ee) {
	return new ExdictException(namespace, group, message, ee);
    }

    public static final ExdictException newException(String message, Throwable ee) {
	return new ExdictException(ExdictContext.defaultNamespace().getName(),
		ExdictContext.defaultNamespace().getGroupInfoHelper().getDefaultGroup(), message, ee);
    }

    public static final <T extends Exception> T insertInStack(final String namespace, final String group,
	    final String message, final Class<T> exceptionClass) {
	Constructor<T> constructor = null;
	try {
	    constructor = exceptionClass.getConstructor(String.class, Throwable.class);
	    final ExdictException ee = newException(namespace, group, message);
	    return constructor.newInstance(message, ee);
	} catch (Exception e) {
	    logger.warn("can not wrap " + exceptionClass.getName(), e);
	}
	throw new RuntimeException("Use method only for exception with (String, Throwable) constructors.");
    }

    public static final <T extends Exception> T tryInsertInStack(final String namespace, final String group,
	    final String message, final Class<T> exceptionClass) {
	Constructor<T> constructor = null;
	try {
	    constructor = exceptionClass.getConstructor(String.class, Throwable.class);
	    final ExdictException ee = newException(namespace, group, message);
	    return constructor.newInstance(message, ee);
	} catch (Exception e) {
	    logger.warn("can not wrap " + exceptionClass.getName(), e);
	}
	try {
	    constructor = exceptionClass.getConstructor(String.class);
	    return constructor.newInstance(message);
	} catch (Exception e) {
	    logger.warn("can not create with constructor with message " + exceptionClass.getName(), e);
	}
	throw new RuntimeException("Use method only for exception with (String) or (String, Throwable) constructors.");
    }
}
