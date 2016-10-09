package org.datastd.tools.exdict.example;

import org.datastd.tools.exdict.context.ExdictContext;
import org.datastd.tools.exdict.context.impl.NamespaceInitializer;
import org.datastd.tools.exdict.exceptions.ExdictException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Egor Litvinenko
 */
public class UsingOfExdictException {

    static {
        NamespaceInitializer.initDefaultNamespace();
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
        final NewNamespaceInitializerExample exampleExdictContext = new NewNamespaceInitializerExample();
        ExdictException e = exampleExdictContext.getNamespace().getException("Exception1");
        e.setHelpMessage("Help message go to url");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        e.setDeveloperMessage(sw.toString());
        sw.close();
        pw.close();
        throw e;
    }

    static class NewNamespaceInitializerExample extends NamespaceInitializer {

        static final String GROUP = "Some Group";
        static final String NAMESPACE = "Some Group Namespace";
        static final int GROUP_INITIAL_CODE = 30000;

        NewNamespaceInitializerExample() {
            super(NAMESPACE);
        }

        @Override
        protected void addGroups() {
            getNamespace().getGroupInfoHelper().addGroup(GROUP, GROUP_INITIAL_CODE);
        }

    }

}
