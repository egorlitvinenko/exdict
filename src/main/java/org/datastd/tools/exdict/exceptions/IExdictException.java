package org.datastd.tools.exdict.exceptions;

public interface IExdictException {

    Integer getCode();

    String getGroup();

    String getMessage();

    String getHelpMessage();

    void setHelpMessage(String helpMessage);

    String getDeveloperMessage();

    void setDeveloperMessage(String developerMessage);

    ExceptionInfo getExceptionInfo();

}
