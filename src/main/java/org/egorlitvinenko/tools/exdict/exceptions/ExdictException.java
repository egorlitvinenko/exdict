package org.egorlitvinenko.tools.exdict.exceptions;

import org.egorlitvinenko.tools.exdict.ExdictContext;

/**
 * @author Egor Litvinenko
 *
 */
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

}
