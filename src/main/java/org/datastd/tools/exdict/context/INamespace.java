package org.datastd.tools.exdict.context;

import org.datastd.tools.exdict.exceptions.ExdictException;
import org.datastd.tools.exdict.exceptions.ExdictRuntimeException;

import java.util.List;
import java.util.Set;

/**
 * @author Egor Litvinenko
 */
public interface INamespace {

    String getName();

    ResolverInitProvider getResolverInitProvider();

    void setResolverInitProvider(ResolverInitProvider resolverInitProvider);

    ErrorsStoreProvider getErrorsStoreProvider();

    void setErrorsStoreProvider(ErrorsStoreProvider errorsStoreProvider);

    ExdictCodeGenerator getCodeGenerator();

    void setCodeGenerator(ExdictCodeGenerator codeGenerator);

    IGroupInfoHelper getGroupInfoHelper();

    void setGroupInfoHelper(IGroupInfoHelper groupInfoHelper);

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
