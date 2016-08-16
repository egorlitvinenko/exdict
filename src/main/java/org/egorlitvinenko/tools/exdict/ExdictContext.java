package org.egorlitvinenko.tools.exdict;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.egorlitvinenko.tools.exdict.exceptions.IExdictException;

/**
 * @author Egor Litvinenko
 *
 */
public class ExdictContext implements IExdictException {

    public static final String DEFAULT_NAMESPACE = "DEFAULT_NAMESPACE";

    private static Map<String, INamespace> namespaces = new ConcurrentHashMap<>();

    public static ResolverInitProvider getResolverInitProvider() {
	return namespaces.get(DEFAULT_NAMESPACE).getResolverInitProvider();
    }

    public static void setResolverInitProvider(ResolverInitProvider resolverInitProvider) {
	namespaces.get(DEFAULT_NAMESPACE).setResolverInitProvider(resolverInitProvider);
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

    public static IGroupInfoHelper getGroupInfoHelper() {
	return namespaces.get(DEFAULT_NAMESPACE).getGroupInfoHelper();
    }

    public static void setGroupInfoHelper(IGroupInfoHelper groupInfoHelper) {
	namespaces.get(DEFAULT_NAMESPACE).setGroupInfoHelper(groupInfoHelper);
    }

    public static void flushAll() throws Exception {
	final List<Exception> errors = new ArrayList<>();
	namespaces.values().forEach(namespace -> {
	    try {
		namespace.getErrorsStoreProvider().flushAll();
	    } catch (Exception e) {
		errors.add(e);
	    }
	});
	if (!errors.isEmpty()) {
	    throw errors.get(0);
	}
    }

    public static INamespace namespace(String name) {
	return namespaces.get(name);
    }

    public static INamespace defaultNamespace() {
	return namespaces.get(DEFAULT_NAMESPACE);
    }

    public static INamespace createNamespace(String name) {
	final INamespace namespace = new Namespace();
	namespace.setName(name);
	namespaces.put(name, namespace);
	return namespace;
    }

    public static void initWithDefaultProviders() {

	final INamespace namespace = createNamespace(DEFAULT_NAMESPACE);
	initWithDefaultProviders(namespace);

    }

    public static void initWithDefaultProviders(INamespace namespace) {

	namespace.setGroupInfoHelper(new DefaultGroupInfoHelper());
	namespace.setCodeGenerator(new DefaultCodeGenerator(namespace));
	namespace.setErrorsStoreProvider(new DefaultErrorsStoreProvider(namespace));
	namespace.setResolverInitProvider(new DefaultResolverInitProvider());

    }

    // OBJECT CLASS

    private final IExdictException exception;
    private INamespace objectNamespace;
    private String group;

    private ExdictContext(IExdictException exception) {
	this.exception = exception;
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
	ExceptionInfo info = objectNamespace.getResolverInitProvider().getExceptionInfosByMessage().get(message);
	if (null == info) {
	    info = objectNamespace.getErrorsStoreProvider().getByMessage(message);
	}
	return info;
    }

    public ExceptionInfo findExceptionInfoByMessageObject(final String message) {
	ExceptionInfo info = null;
	info = this.objectNamespace.getResolverInitProvider().getExceptionInfosByMessage().get(message);
	if (null == info) {
	    info = this.objectNamespace.getErrorsStoreProvider().getByMessage(message);
	}
	return info;
    }

    @Override
    public Integer getCode() {
	return Optional.of(findExceptionInfoByMessage(getMessage())).map(info -> info.getCode()).orElse(null);
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
	return Optional.of(findExceptionInfoByMessageObject(getMessage())).map(info -> info.getHelpMessage())
		.orElse(null);
    }

    @Override
    public void setHelpMessage(String helpMessage) {
	Optional.of(findExceptionInfoByMessageObject(getMessage())).map(info -> {
	    info.setHelpMessage(helpMessage);
	    return void.class;
	});
    }

    @Override
    public String getDeveloperMessage() {
	return Optional.of(findExceptionInfoByMessageObject(getMessage())).map(info -> info.getDeveloperMessage())
		.orElse(null);
    }

    @Override
    public void setDeveloperMessage(String developerMessage) {
	Optional.of(findExceptionInfoByMessageObject(getMessage())).map(info -> {
	    info.setDeveloperMessage(developerMessage);
	    return void.class;
	});
    }

}
