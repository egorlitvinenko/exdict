package org.datastd.tools.exdict.context;

import org.datastd.tools.exdict.exceptions.ExdictException;
import org.datastd.tools.exdict.exceptions.ExdictRuntimeException;

import java.util.List;

/**
 * @author Egor Litvinenko
 */
public interface Namespace {

    String getName();

    InitializationProvider getInitializationProvider();

    void setInitializationProvider(InitializationProvider initializationProvider);

    ErrorsStoreProvider getErrorsStoreProvider();

    void setErrorsStoreProvider(ErrorsStoreProvider errorsStoreProvider);

    ExdictCodeGenerator getCodeGenerator();

    void setCodeGenerator(ExdictCodeGenerator codeGenerator);

    GroupInfoHelper getGroupInfoHelper();

    void setGroupInfoHelper(GroupInfoHelper groupInfoHelper);

    List<Integer> getInitialCodes();

    ExdictException getException(String message);

    ExdictException getException(String message, Throwable e);

    ExdictException getException(String group, String message);

    ExdictException getException(String group, String message, Throwable e);

    ExdictRuntimeException getRuntimeException(String message);

    ExdictRuntimeException getRuntimeException(String message, Throwable e);

    ExdictRuntimeException getRuntimeException(String group, String message);

    ExdictRuntimeException getRuntimeException(String group, String message, Throwable e);

}
