package org.datastd.tools.exdict.context.impl;

import org.datastd.tools.exdict.context.*;
import org.datastd.tools.exdict.exceptions.ExdictException;
import org.datastd.tools.exdict.exceptions.ExdictRuntimeException;
import org.datastd.tools.exdict.exceptions.GroupInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Egor Litvinenko
 */
public class Namespace implements INamespace {

    private final String name;
    private ResolverInitProvider resolverInitProvider;
    private ErrorsStoreProvider errorsStoreProvider;
    private ExdictCodeGenerator codeGenerator;
    private IGroupInfoHelper groupInfoHelper;

    public Namespace(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
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
    public List<Integer> getInitialCodes() {
        return groupInfoHelper.getGroupInfos().values().stream().map(GroupInfo::getInitialCode).collect(Collectors.toList());
    }

    @Override
    public ExdictException getException(String message) {
        return getException(getGroupInfoHelper().getDefaultGroup(), message);
    }

    @Override
    public ExdictException getException(String group, String message) {
        return new ExdictException(getName(), getGroupInfoHelper().getDefaultGroup(), message);
    }

    @Override
    public ExdictException getException(String message, Throwable e) {
        return getException(getGroupInfoHelper().getDefaultGroup(), message, e);
    }

    @Override
    public ExdictException getException(String group, String message, Throwable e) {
        return new ExdictException(getName(), group, message, e);
    }

    @Override
    public ExdictRuntimeException getRuntimeException(String message) {
        return getRuntimeException(getGroupInfoHelper().getDefaultGroup(), message);
    }

    @Override
    public ExdictRuntimeException getRuntimeException(String message, Throwable e) {
        return getRuntimeException(getGroupInfoHelper().getDefaultGroup(), message, e);
    }

    @Override
    public ExdictRuntimeException getRuntimeException(String group, String message) {
        return new ExdictRuntimeException(getName(), group, message);
    }

    @Override
    public ExdictRuntimeException getRuntimeException(String group, String message, Throwable e) {
        return new ExdictRuntimeException(getName(), group, message, e);
    }

}
