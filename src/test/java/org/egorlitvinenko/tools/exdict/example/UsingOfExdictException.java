package org.egorlitvinenko.tools.exdict.example;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.egorlitvinenko.tools.exdict.ClasspathPropertiesErrorsLoader;
import org.egorlitvinenko.tools.exdict.DefaultPropertiesErrorsStore;
import org.egorlitvinenko.tools.exdict.ExdictContext;
import org.egorlitvinenko.tools.exdict.exceptions.ExdictException;

/**
 * @author Egor Litvinenko
 *
 */
public class UsingOfExdictException {

    static {

	// init it before using
	ExdictContext.initWithDefaults();

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

	ExdictContext.getErrorsStoreProvider().flushAll();

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

}
