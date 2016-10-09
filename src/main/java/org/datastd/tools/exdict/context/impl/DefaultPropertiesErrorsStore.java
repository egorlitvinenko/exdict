package org.datastd.tools.exdict.context.impl;

import org.datastd.tools.exdict.context.ExdictErrorsStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @author Egor Litvinenko
 */
public class DefaultPropertiesErrorsStore extends AbstractErrorsStore implements ExdictErrorsStore {

    private final Properties properties;

    public DefaultPropertiesErrorsStore(final String property, final Object resource) {
        super(property, resource);
        properties = new Properties();
    }

    @Override
    public void flush(Map<String, Object> values) throws Exception {
        values.forEach((code, value) -> properties.put(code, value.toString()));
        try (final OutputStream os = new FileOutputStream(new File(resource.toString()))) {
            properties.store(os, "Exdict flush properties");
        }
    }

}
