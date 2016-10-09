package org.datastd.tools.exdict.context.impl;

import org.datastd.tools.exdict.context.ExdictErrorsStore;

import java.util.Map;

/**
 * @author Egor Litvinenko
 */
public abstract class AbstractErrorsStore implements ExdictErrorsStore {

    protected final Object resource;
    protected final String property;

    public AbstractErrorsStore(final String property, final Object resource) {
        this.resource = resource;
        this.property = property;
    }

    @Override
    public String exceptionInfoAttributeName() {
        return property;
    }

    @Override
    public void flush(Map<String, Object> values) throws Exception {
        throw new Exception("flushing is not supported");
    }

    @Override
    public void append(Map<String, Object> values) throws Exception {
        throw new Exception("appending is not supported");
    }

    @Override
    public void remove(Map<String, Object> values) throws Exception {
        throw new Exception("removing is not supported");
    }

}
