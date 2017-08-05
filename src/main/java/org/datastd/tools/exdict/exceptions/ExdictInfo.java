package org.datastd.tools.exdict.exceptions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Egor Litvinenko on 04.02.17.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExdictInfo {

    /**
     * The key of given exception. Default will be equal to result of {@link Throwable#getMessage()} method.
     * */
    String value() default "";

    /**
     * If <code>true</code>, then the {@link Class#getName()} for key will be used.
     * */
    boolean useClassName() default false;

    /**
     * Namespace of {@link Throwable}
     * */
    String namespace() default "";

    /**
     * Group of {@link Throwable}
     * */
    String group() default "";

    /**
     * Code of {@link Throwable}
     * */
    int code() default -1;

    /**
     * Help message of {@link Throwable}
     * */
    String helpMessage() default "";

    /**
     * Description of {@link Throwable}
     * */
    String description() default "";

}
