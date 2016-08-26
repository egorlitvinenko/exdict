package org.egorlitvinenko.tools.exdict;

import java.util.Set;

import org.egorlitvinenko.tools.exdict.exceptions.ExdictException;
import org.egorlitvinenko.tools.exdict.exceptions.ExdictRuntimeException;

/**
 * @author Egor Litvinenko
 *
 */
public interface INamespace {

    String getName();

    void setName(String name);

    public ResolverInitProvider getResolverInitProvider();

    public void setResolverInitProvider(ResolverInitProvider resolverInitProvider);

    public ErrorsStoreProvider getErrorsStoreProvider();

    public void setErrorsStoreProvider(ErrorsStoreProvider errorsStoreProvider);

    public ExdictCodeGenerator getCodeGenerator();

    public void setCodeGenerator(ExdictCodeGenerator codeGenerator);

    public IGroupInfoHelper getGroupInfoHelper();

    public void setGroupInfoHelper(IGroupInfoHelper groupInfoHelper);

    public void addGroup(String group);

    public Set<String> getGroups();

    public ExdictException getException(String message);

    public ExdictException getException(String message, Throwable e);

    public ExdictRuntimeException getRuntimeException(String message);

    public ExdictRuntimeException getRuntimeException(String message, Throwable e);
}
