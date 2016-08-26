package org.egorlitvinenko.tools.exdict.exceptions;

import org.egorlitvinenko.tools.exdict.ExceptionInfo;
import org.egorlitvinenko.tools.exdict.ExdictContext;

/**
 * @author Egor Litvinenko
 *
 */
// https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
public class ExdictException extends Exception implements IExdictException {

    static final long serialVersionUID = -6187936659998236257L;

    private final ExdictContext exdictContext;

    /**
     * @see Exception
     */
    public ExdictException(String message) {
	super(message);
	exdictContext = ExdictContext.of(this).with(message);
    }

    /**
     * @see Exception
     */
    public ExdictException(String group, String message) {
	super(ExdictContext.getGroupInfoHelper().getFullMessage(group, message));
	exdictContext = ExdictContext.of(this).with(group, message);
    }

    /**
     * @see Exception
     */
    public ExdictException(String namespace, String group, String message) {
	super(ExdictContext.namespace(namespace).getGroupInfoHelper().getFullMessage(group, message));
	exdictContext = ExdictContext.of(this).with(namespace, group, message);
    }

    /**
     * @see Exception
     */
    public ExdictException(String message, Throwable cause) {
	super(message, cause);
	exdictContext = ExdictContext.of(this).with(message);
    }

    /**
     * @see Exception
     */
    public ExdictException(String group, String message, Throwable cause) {
	super(ExdictContext.getGroupInfoHelper().getFullMessage(group, message), cause);
	exdictContext = ExdictContext.of(this).with(group, message);
    }

    /**
     * @see Exception
     */
    public ExdictException(String namespace, String group, String message, Throwable cause) {
	super(ExdictContext.namespace(namespace).getGroupInfoHelper().getFullMessage(group, message), cause);
	exdictContext = ExdictContext.of(this).with(namespace, group, message);
    }

    /**
     * @see Exception
     */
    protected ExdictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
	exdictContext = ExdictContext.of(this).with(message);
    }

    /**
     * @see Exception
     */
    protected ExdictException(String group, String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(ExdictContext.getGroupInfoHelper().getFullMessage(group, message), cause, enableSuppression,
		writableStackTrace);
	exdictContext = ExdictContext.of(this).with(group, message);
    }

    /**
     * @see Exception
     */
    protected ExdictException(String namespace, String group, String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(ExdictContext.namespace(namespace).getGroupInfoHelper().getFullMessage(group, message), cause,
		enableSuppression, writableStackTrace);
	exdictContext = ExdictContext.of(this).with(namespace, group, message);
    }

    @Override
    public Integer getCode() {
	return exdictContext.getCode();
    }

    @Override
    public String getHelpMessage() {
	return exdictContext.getHelpMessage();
    }

    @Override
    public void setHelpMessage(String helpMessage) {
	exdictContext.setHelpMessage(helpMessage);
    }

    @Override
    public String getDeveloperMessage() {
	return exdictContext.getDeveloperMessage();
    }

    @Override
    public void setDeveloperMessage(String developerMessage) {
	exdictContext.setDeveloperMessage(developerMessage);
    }

    @Override
    public String getGroup() {
	return exdictContext.getGroup();
    }

    @Override
    public ExceptionInfo getExceptionInfo() {
	return exdictContext.findExceptionInfoByMessage(getMessage());
    }

}
