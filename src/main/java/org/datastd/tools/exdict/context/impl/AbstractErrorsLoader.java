package org.datastd.tools.exdict.context.impl;

import org.datastd.tools.exdict.context.ExdictErrorsLoader;

import java.util.*;

/**
 * @author Egor Litvinenko
 */
public abstract class AbstractErrorsLoader implements ExdictErrorsLoader {

    protected final String property;
    protected Set<String> loadedResource = new HashSet<>();
    protected String resource;
    protected Map<String, Object> values;

    public AbstractErrorsLoader(final String property) {
        this.property = property;
        this.values = new HashMap<>();
    }

    public AbstractErrorsLoader(final String property, final Object resource) {
        this(property);
        this.resource = null == resource ? null : String.valueOf(resource);
    }

    @Override
    public String getProperty() {
        return property;
    }

    @Override
    public void load() throws Exception {
        load(this.resource);
    }

    @Override
    public void load(Object resource) throws Exception {
        if (null == resource) {
            throw new NullPointerException("resource is null");
        }
        if (!loadedResource.contains(String.valueOf(resource))) {
            loadInternal(resource);
            loadedResource.add(String.valueOf(resource));
        }
    }

    protected abstract void loadInternal(Object resource) throws Exception;

    @Override
    public void loadSilent() {
        try {
            load();
        } catch (Exception e) {
            // ignore
        }
    }

    @Override
    public Object getValue(String code) {
        return Optional.of(code).map(cd -> Optional.of(values).map(values -> values.get(code))).orElse(null);
    }

    @Override
    public Map<String, Object> getValues() {
        return Collections.unmodifiableMap(values);
    }

}
