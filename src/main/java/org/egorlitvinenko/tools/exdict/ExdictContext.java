package org.egorlitvinenko.tools.exdict;

import java.util.Optional;

import org.egorlitvinenko.tools.exdict.exceptions.IExdictException;

/**
 * @author Egor Litvinenko
 *
 */
public class ExdictContext implements IExdictException {

    private static ResolverInitProvider resolverInitProvider;
    private static ErrorsStoreProvider errorsStoreProvider;
    private static ExdictCodeGenerator codeGenerator;
    private static IGroupInfoHelper groupInfoHelper;

    public static ResolverInitProvider getResolverInitProvider() {
	return resolverInitProvider;
    }

    public static void setResolverInitProvider(ResolverInitProvider resolverInitProvider) {
	ExdictContext.resolverInitProvider = resolverInitProvider;
    }

    public static ErrorsStoreProvider getErrorsStoreProvider() {
	return errorsStoreProvider;
    }

    public static void setErrorsStoreProvider(ErrorsStoreProvider errorsStoreProvider) {
	ExdictContext.errorsStoreProvider = errorsStoreProvider;
    }

    public static ExdictCodeGenerator getCodeGenerator() {
	return codeGenerator;
    }

    public static void setCodeGenerator(ExdictCodeGenerator codeGenerator) {
	ExdictContext.codeGenerator = codeGenerator;
    }

    public static IGroupInfoHelper getGroupInfoHelper() {
	return groupInfoHelper;
    }

    public static void setGroupInfoHelper(IGroupInfoHelper groupInfoHelper) {
	ExdictContext.groupInfoHelper = groupInfoHelper;
    }

    public static ExdictContext of(IExdictException exception) {
	return new ExdictContext(exception);
    }

    public ExdictContext with(String message) {
	return with(groupInfoHelper.getDefaultGroup(), message);
    }

    public ExdictContext with(String group, String message) {
	ExceptionInfo info = ExdictContext
		.findExceptionInfoByMessage(getGroupInfoHelper().getFullMessage(group, message));
	if (null == info) {
	    info = new ExceptionInfo(codeGenerator.nextForGroup(group));
	    info.setMessage(message);
	    groupInfoHelper.setGroupName(info, group);
	    ExdictContext.getErrorsStoreProvider().add(info);
	} 
	return this;
    }

    public static void initWithDefaults() {
	ExdictContext.setGroupInfoHelper(new DefaultGroupInfoHelper());
	ExdictContext.setCodeGenerator(new DefaultCodeGenerator());
	ExdictContext.setErrorsStoreProvider(new DefaultErrorsStoreProvider());
	ExdictContext.setResolverInitProvider(new DefaultResolverInitProvider());
    }

    private final IExdictException exception;

    private ExdictContext(IExdictException exception) {
	this.exception = exception;
    }

    public static ExceptionInfo findExceptionInfoByMessage(final String message) {
	ExceptionInfo info = ExdictContext.getResolverInitProvider().getExceptionInfosByMessage().get(message);
	if (null == info) {
	    info = ExdictContext.getErrorsStoreProvider().getByMessage(message);
	}
	return info;
    }

    @Override
    public Integer getCode() {
	return Optional.of(ExdictContext.findExceptionInfoByMessage(getMessage())).map(info -> info.getCode())
		.orElse(null);
    }

    @Override
    public String getGroup() {
	return Optional.ofNullable(ExdictContext.findExceptionInfoByMessage(getMessage()))
		.map(info -> groupInfoHelper.getGroupName(info)).orElse(null);
    }

    @Override
    public String getMessage() {
	return exception.getMessage();
    }

    @Override
    public String getHelpMessage() {
	return Optional.of(ExdictContext.findExceptionInfoByMessage(getMessage())).map(info -> info.getHelpMessage())
		.orElse(null);
    }

    @Override
    public void setHelpMessage(String helpMessage) {
	Optional.of(ExdictContext.findExceptionInfoByMessage(getMessage())).map(info -> {
	    info.setHelpMessage(helpMessage);
	    return void.class;
	});
    }

    @Override
    public String getDeveloperMessage() {
	return Optional.of(ExdictContext.findExceptionInfoByMessage(getMessage()))
		.map(info -> info.getDeveloperMessage()).orElse(null);
    }

    @Override
    public void setDeveloperMessage(String developerMessage) {
	Optional.of(ExdictContext.findExceptionInfoByMessage(getMessage())).map(info -> {
	    info.setDeveloperMessage(developerMessage);
	    return void.class;
	});
    }

}
