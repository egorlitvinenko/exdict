package org.egorlitvinenko.tools.exdict.example;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.egorlitvinenko.tools.exdict.ClasspathPropertiesErrorsLoader;
import org.egorlitvinenko.tools.exdict.DefaultPropertiesErrorsStore;
import org.egorlitvinenko.tools.exdict.ExdictContext;
import org.egorlitvinenko.tools.exdict.INamespace;
import org.egorlitvinenko.tools.exdict.exceptions.ExdictException;

/**
 * @author Egor Litvinenko
 *
 */
public class UsingOfExdictException {

    static {

	// init it before using with default implementations
	ExdictContext.initWithDefaultProviders();

	ExdictContext.getResolverInitProvider()
		.setCodeLoader(new ClasspathPropertiesErrorsLoader("code", "exdict_codes.properties"));

	ExdictContext.getResolverInitProvider()
		.addLoader(new ClasspathPropertiesErrorsLoader("message", "exdict_message.properties"));
	ExdictContext.getResolverInitProvider()
		.addLoader(new ClasspathPropertiesErrorsLoader("developerMessage", "exdict_dev_message.properties"));
	ExdictContext.getResolverInitProvider()
		.addLoader(new ClasspathPropertiesErrorsLoader("helpMessage", "exdict_help_message.properties"));

	ExdictContext.getErrorsStoreProvider().addStore("code",
		new DefaultPropertiesErrorsStore("new_codes.properties"));
	ExdictContext.getErrorsStoreProvider().addStore("message",
		new DefaultPropertiesErrorsStore("new_message.properties"));
	ExdictContext.getErrorsStoreProvider().addStore("helpMessage",
		new DefaultPropertiesErrorsStore("new_help_message.properties"));
	ExdictContext.getErrorsStoreProvider().addStore("developerMessage",
		new DefaultPropertiesErrorsStore("new_dev_message.properties"));

	try {
	    ExdictContext.getResolverInitProvider().load();
	    ExdictContext.getCodeGenerator().init(ExdictContext.getResolverInitProvider());
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}

	new NamespaceExampleExdictContext().init();

    }

    public static void main(String[] args) throws Exception {

	try {
	    someBadMethod();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	try {
	    someBadMethod2();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	try {
	    someBadMethod3();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	try {
	    someBadMethod4();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	ExdictContext.flushAll();

    }

    public static void someBadMethod() throws Exception {
	throw new ExdictException("Exception1");
    }

    public static void someBadMethod2() throws Exception {
	ExdictContext.getGroupInfoHelper().addGroup("Group2", 100000);
	ExdictException e = new ExdictException("Group2", "Exception2");
	e.setHelpMessage("Help message go to url");
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	e.printStackTrace(pw);
	e.setDeveloperMessage(sw.toString());
	sw.close();
	pw.close();
	throw e;
    }

    public static void someBadMethod3() throws Exception {
	ExdictContext.getGroupInfoHelper().addGroup("Group2", 100000);
	ExdictException e = new ExdictException("Group2", "Exception1");
	e.setHelpMessage("Help message go to url");
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	e.printStackTrace(pw);
	e.setDeveloperMessage(sw.toString());
	sw.close();
	pw.close();
	throw e;
    }

    public static void someBadMethod4() throws Exception {
	ExdictException e = NamespaceExampleExdictContext.newException("Exception1");
	e.setHelpMessage("Help message go to url");
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	e.printStackTrace(pw);
	e.setDeveloperMessage(sw.toString());
	sw.close();
	pw.close();
	throw e;
    }

    public static class NamespaceExampleExdictContext {

	public static final String GROUP = "Some Group";
	public static final String NAMESPACE = "Some Group Namespace";
	public static final int GROUP_INITIAL_CODE = 30000;

	public static final ExdictException newException(String message) {
	    return new ExdictException(NAMESPACE, GROUP, message);
	}

	public void init() {
	    initDefault();
	    addGroups();
	}

	public void initDefault() {

	    // init it before using with default implementations
	    final INamespace namespace = ExdictContext.createNamespace(NAMESPACE);
	    ExdictContext.initWithDefaultProviders(namespace);

	    { // saved files
		namespace.getResolverInitProvider()
			.setCodeLoader(new ClasspathPropertiesErrorsLoader("code", "namespace2_codes.properties"));
		namespace.getResolverInitProvider()
			.addLoader(new ClasspathPropertiesErrorsLoader("message", "namespace2_message.properties"));
		namespace.getResolverInitProvider().addLoader(new ClasspathPropertiesErrorsLoader("developerMessage",
			"userservice_developer_message.properties"));
		namespace.getResolverInitProvider().addLoader(
			new ClasspathPropertiesErrorsLoader("helpMessage", "namespace2_help_message.properties"));
	    }

	    { // new exceptions
		namespace.getErrorsStoreProvider().addStore("code",
			new DefaultPropertiesErrorsStore("new_namespace2_codes.properties"));
		namespace.getErrorsStoreProvider().addStore("message",
			new DefaultPropertiesErrorsStore("new_namespace2_message.properties"));
		namespace.getErrorsStoreProvider().addStore("helpMessage",
			new DefaultPropertiesErrorsStore("new_namespace2_help_message.properties"));
		namespace.getErrorsStoreProvider().addStore("developerMessage",
			new DefaultPropertiesErrorsStore("new_namespace2_developer_message.properties"));
	    }

	    try {
		namespace.getResolverInitProvider().load();
		namespace.getCodeGenerator().init(namespace.getResolverInitProvider());
	    } catch (Exception e) {
		throw new RuntimeException(e);
	    }

	}

	protected void addGroups() {
	    ExdictContext.namespace(NAMESPACE).getGroupInfoHelper().addGroup(GROUP, GROUP_INITIAL_CODE);
	}

    }

}
