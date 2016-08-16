package org.egorlitvinenko.tools.exdict;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Egor Litvinenko
 *
 */
public class Namespace implements INamespace {

    private String name = "DEFAULT";

    private ResolverInitProvider resolverInitProvider;
    private ErrorsStoreProvider errorsStoreProvider;
    private ExdictCodeGenerator codeGenerator;
    private IGroupInfoHelper groupInfoHelper;

    private Set<String> groups;

    public Namespace() {
	groups = new ConcurrentSkipListSet<>();
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public void setName(String name) {
	this.name = name;
    }

    @Override
    public ResolverInitProvider getResolverInitProvider() {
	return resolverInitProvider;
    }

    @Override
    public void setResolverInitProvider(ResolverInitProvider resolverInitProvider) {
	this.resolverInitProvider = resolverInitProvider;
    }

    @Override
    public ErrorsStoreProvider getErrorsStoreProvider() {
	return errorsStoreProvider;
    }

    @Override
    public void setErrorsStoreProvider(ErrorsStoreProvider errorsStoreProvider) {
	this.errorsStoreProvider = errorsStoreProvider;
    }

    @Override
    public ExdictCodeGenerator getCodeGenerator() {
	return codeGenerator;
    }

    @Override
    public void setCodeGenerator(ExdictCodeGenerator codeGenerator) {
	this.codeGenerator = codeGenerator;
    }

    @Override
    public IGroupInfoHelper getGroupInfoHelper() {
	return groupInfoHelper;
    }

    @Override
    public void setGroupInfoHelper(IGroupInfoHelper groupInfoHelper) {
	this.groupInfoHelper = groupInfoHelper;
    }

    @Override
    public void addGroup(String group) {
	groups.add(group);
    }

    @Override
    public Set<String> getGroups() {
	return groups;
    }

}
