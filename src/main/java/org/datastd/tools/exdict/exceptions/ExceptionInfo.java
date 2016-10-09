package org.datastd.tools.exdict.exceptions;

/**
 * @author Egor Litvinenko
 */
public class ExceptionInfo {

    private Integer code;
    private String message = null;
    private String developerMessage = null;
    private String helpMessage = null;

    public ExceptionInfo(final Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getHelpMessage() {
        return helpMessage;
    }

    public void setHelpMessage(String helpMessage) {
        this.helpMessage = helpMessage;
    }

}
