package org.datastd.tools.exdict.context.impl;

import org.datastd.tools.exdict.context.ExdictContext;
import org.datastd.tools.exdict.context.ExdictErrorsLoader;
import org.datastd.tools.exdict.context.ExdictErrorsStore;
import org.datastd.tools.exdict.context.INamespace;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by Egor Litvinenko on 08.10.16.
 */
public class NamespaceInitializer {

    private final INamespace namespace;

    public NamespaceInitializer(String namespaceName) {
        namespace = ExdictContext.createNamespace(namespaceName);
        init();
    }

    public NamespaceInitializer(INamespace namespace) {
        this.namespace = namespace;
        init();
    }

    public void init() {
        initDefault();
        addGroups();
    }

    protected void initDefault() {

        // init it before using with default implementations
        ExdictContext.initWithDefaultProviders(namespace);

        // saved files
        getDefaultExdictErrorsLoader().forEach(loader -> {
            if (loader.getProperty().equals("code"))
                namespace.getResolverInitProvider().setCodeLoader(loader);
            else
                namespace.getResolverInitProvider()
                        .addLoader(loader);
        });

        // new exceptions
        getDefaultExdictErrorsStore().forEach(store -> namespace.getErrorsStoreProvider().addStore(store));

        // load files and init code generator with codes from files
        try {
            namespace.getResolverInitProvider().load();
            namespace.getCodeGenerator().init(namespace.getResolverInitProvider());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public INamespace getNamespace() {
        return namespace;
    }

    protected void addGroups() {
        namespace.getGroupInfoHelper().addGroup("DEFAULT", ExdictContext.guessInitialCode());
    }

    protected Stream<ExdictErrorsLoader> getDefaultExdictErrorsLoader() {
        return Arrays.stream(new ExdictErrorsLoader[]{
                new ClasspathPropertiesErrorsLoader("code", namespace.getName() + "_codes.properties"),
                new ClasspathPropertiesErrorsLoader("message", namespace.getName() + "_message.properties"),
                new ClasspathPropertiesErrorsLoader("developerMessage", namespace.getName() + "_developer_message.properties"),
                new ClasspathPropertiesErrorsLoader("helpMessage", namespace.getName() + "_help_message.properties")});
    }

    protected Stream<ExdictErrorsStore> getDefaultExdictErrorsStore() {
        return Arrays.stream(new ExdictErrorsStore[]{
                new DefaultPropertiesErrorsStore("code", namespace.getName() + "_new_codes.properties"),
                new DefaultPropertiesErrorsStore("message", namespace.getName() + "_new_message.properties"),
                new DefaultPropertiesErrorsStore("developerMessage", namespace.getName() + "_new_developer_message.properties"),
                new DefaultPropertiesErrorsStore("helpMessage", namespace.getName() + "_new_help_message.properties")});
    }

    public static synchronized NamespaceInitializer initDefaultNamespace() {
        if (null == DefaultNamespaceInitializer.INSTANCE) {
            DefaultNamespaceInitializer.INSTANCE = new DefaultNamespaceInitializer();
        }
        return DefaultNamespaceInitializer.INSTANCE;
    }

    public static final class DefaultNamespaceInitializer extends NamespaceInitializer {

        private static volatile DefaultNamespaceInitializer INSTANCE;

        private DefaultNamespaceInitializer() {
            super(ExdictContext.DEFAULT_NAMESPACE);
        }

    }

}
