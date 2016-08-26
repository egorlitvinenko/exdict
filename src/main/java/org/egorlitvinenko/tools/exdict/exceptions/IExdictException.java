package org.egorlitvinenko.tools.exdict.exceptions;

import org.egorlitvinenko.tools.exdict.ExceptionInfo;

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
