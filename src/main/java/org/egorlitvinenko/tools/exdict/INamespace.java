package org.egorlitvinenko.tools.exdict;

import java.util.Set;

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

}
