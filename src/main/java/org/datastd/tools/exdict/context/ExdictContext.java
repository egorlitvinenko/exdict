package org.datastd.tools.exdict.context;

import org.datastd.tools.exdict.context.impl.*;
import org.datastd.tools.exdict.exceptions.ExceptionInfo;
import org.datastd.tools.exdict.exceptions.ExdictException;
import org.datastd.tools.exdict.exceptions.ExdictExceptionHelper;
import org.datastd.tools.exdict.exceptions.IExdictException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Egor Litvinenko
 */
public class ExdictContext implements IExdictException {

    public static String DEFAULT_NAMESPACE = "DEFAULT_NAMESPACE";

    private static Map<String, Namespace> namespaces = new ConcurrentHashMap<>();

    public static InitializationProvider getResolverInitProvider() {
        return namespaces.get(DEFAULT_NAMESPACE).getInitializationProvider();
    }

    public static void setResolverInitProvider(InitializationProvider initializationProvider) {
        namespaces.get(DEFAULT_NAMESPACE).setInitializationProvider(initializationProvider);
    }

    public static ErrorsStoreProvider getErrorsStoreProvider() {
        return namespaces.get(DEFAULT_NAMESPACE).getErrorsStoreProvider();
    }

    public static void setErrorsStoreProvider(ErrorsStoreProvider errorsStoreProvider) {
        namespaces.get(DEFAULT_NAMESPACE).setErrorsStoreProvider(errorsStoreProvider);
    }

    public static ExdictCodeGenerator getCodeGenerator() {
        return namespaces.get(DEFAULT_NAMESPACE).getCodeGenerator();
    }

    public static void setCodeGenerator(ExdictCodeGenerator codeGenerator) {
        namespaces.get(DEFAULT_NAMESPACE).setCodeGenerator(codeGenerator);
    }

    public static GroupInfoHelper getGroupInfoHelper() {
        return namespaces.get(DEFAULT_NAMESPACE).getGroupInfoHelper();
    }

    public static void setGroupInfoHelper(GroupInfoHelper groupInfoHelper) {
        namespaces.get(DEFAULT_NAMESPACE).setGroupInfoHelper(groupInfoHelper);
    }

    public static String getDefaultNamespace() {
        return DEFAULT_NAMESPACE;
    }

    public static void setDefaultNamespace(String defaultNamespace) {
        DEFAULT_NAMESPACE = defaultNamespace;
    }

    public static void flushAll() throws Exception {
        final List<Exception> errors = new ArrayList<>();
        namespaces.values().stream().map(Namespace::getErrorsStoreProvider).forEach(provider -> {
            try {
                provider.flushAll();
            } catch (Exception e) {
                errors.add(e);
            }
        });
        if (!errors.isEmpty()) {
            throw errors.get(0);
        }
    }

    public static Namespace namespace(String name) {
        return namespaces.get(name);
    }

    public static Namespace defaultNamespace() {
        return namespaces.get(DEFAULT_NAMESPACE);
    }

    public static Namespace createNamespace(String name) {
        final Namespace namespace = new org.datastd.tools.exdict.context.impl.Namespace(name);
        namespaces.put(name, namespace);
        return namespace;
    }

    public static void initWithDefaultProviders() {
        final Namespace namespace = createNamespace(DEFAULT_NAMESPACE);
        initWithDefaultProviders(namespace);
    }

    public static void initWithDefaultProviders(Namespace namespace) {
        namespace.setGroupInfoHelper(new DefaultGroupInfoHelper());
        namespace.setCodeGenerator(new DefaultCodeGenerator(namespace));
        namespace.setErrorsStoreProvider(new DefaultErrorsStoreProvider(namespace));
        namespace.setInitializationProvider(new DefaultInitializationProvider());
    }

    public static synchronized Integer guessInitialCode() {
        return namespaces.values().stream().map(Namespace::getInitialCodes).flatMap(List::stream).reduce(Integer::max).orElse(0) + 10000;
    }

    // OBJECT CLASS

    private final IExdictException exception;
    private Namespace objectNamespace;
    private String group;

    private ExdictContext(IExdictException exception) {
        this.exception = exception;
    }

    public static ExdictContext wrap(Exception exception) {
        final ExdictException ee = ExdictExceptionHelper.newException(exception.getMessage(), exception);
        return of(ee);
    }

    public static ExdictContext of(IExdictException exception) {
        return new ExdictContext(exception);
    }

    public ExdictContext with(String message) {
        this.group = getGroupInfoHelper().getDefaultGroup();
        return with(group, message);
    }

    public ExdictContext with(String group, String message) {
        return with(DEFAULT_NAMESPACE, group, message);
    }

    public ExdictContext with(final String namespaceName, final String group, final String message) {
        this.objectNamespace = ExdictContext.namespace(namespaceName);
        this.group = group;
        ExceptionInfo info = findExceptionInfoByMessage(
                this.objectNamespace.getGroupInfoHelper().getFullMessage(group, message));
        if (null == info) {
            info = new ExceptionInfo(this.objectNamespace.getCodeGenerator().nextForGroup(group));
            info.setMessage(message);
            this.objectNamespace.getGroupInfoHelper().setGroupName(info, group);
            this.objectNamespace.getErrorsStoreProvider().add(info);
        }
        return this;
    }

    public ExceptionInfo findExceptionInfoByMessage(final String message) {
        ExceptionInfo info = objectNamespace.getInitializationProvider().getExceptionInfosByMessage().get(message);
        if (null == info) {
            info = objectNamespace.getErrorsStoreProvider().getByMessage(message);
        }
        return info;
    }

    @Override
    public Integer getCode() {
        return Optional.of(findExceptionInfoByMessage(getMessage())).map(ExceptionInfo::getCode).orElse(null);
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public String getMessage() {
        return exception.getMessage();
    }

    @Override
    public String getHelpMessage() {
        return Optional.of(findExceptionInfoByMessage(getMessage())).map(ExceptionInfo::getHelpMessage).orElse(null);
    }

    @Override
    public void setHelpMessage(String helpMessage) {
        Optional.of(findExceptionInfoByMessage(getMessage())).map(info -> {
            info.setHelpMessage(helpMessage);
            return void.class;
        });
    }

    @Override
    public String getDeveloperMessage() {
        return Optional.of(findExceptionInfoByMessage(getMessage())).map(ExceptionInfo::getDeveloperMessage)
                .orElse(null);
    }

    @Override
    public void setDeveloperMessage(String developerMessage) {
        Optional.of(findExceptionInfoByMessage(getMessage())).map(info -> {
            info.setDeveloperMessage(developerMessage);
            return void.class;
        });
    }

    @Override
    public ExceptionInfo getExceptionInfo() {
        return this.findExceptionInfoByMessage(getMessage());
    }

}
