package org.egorlitvinenko.tools.exdict;

/**
 * @author Egor Litvinenko
 *
 */
public class ExdictResolver {

    private boolean inited = false;

    public ExdictResolver() {
	defaultInit();
    }

    public void defaultInit() {
	if (!inited) {
	    initCodesClasspath("exdict_code.properties");
	    initDeveloperMessageClasspath("exdict_developer_message.properties");
	    initHelpMessageClasspath("exdict_help_message.properties");
	    initMessageClasspath("exdict_message.properties");
	    inited = true;
	}
    }

    public void initCodesClasspath(final String codesResource) {

    }

    public void initDeveloperMessageClasspath(final String developerMessageResource) {

    }

    public void initHelpMessageClasspath(final String helpMessageResource) {

    }

    public void initMessageClasspath(final String messageResource) {

    }

}
