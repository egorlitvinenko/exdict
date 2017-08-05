package org.datastd.tools.exdict.context.impl;

import org.datastd.tools.exdict.context.ExdictErrorsLoader;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Egor Litvinenko
 */
public class ClasspathPropertiesErrorsLoader extends AbstractErrorsLoader implements ExdictErrorsLoader {

    public ClasspathPropertiesErrorsLoader(final String property) {
        super(property);
    }

    public ClasspathPropertiesErrorsLoader(final String property, final String resource) {
        super(property, resource);
    }

    protected String getPath(final Object resource) {
        return resource.toString();
    }

    protected ClassLoader getClassLoader() {
        return this.getClass().getClassLoader();
    }

    @Override
    protected void loadInternal(Object resource) throws Exception {
        final String path = getPath(resource);
        final ClassLoader classLoader = getClassLoader();
        final InputStream is = classLoader.getResourceAsStream(path);
        if (null != is) {
            final Properties properties = new Properties();
            properties.load(is);
            is.close();
            properties.forEach((code, value) -> values.put(code.toString(), value));
        }
    }

}
