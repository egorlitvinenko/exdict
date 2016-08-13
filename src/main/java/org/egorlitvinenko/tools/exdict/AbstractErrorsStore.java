package org.egorlitvinenko.tools.exdict;

import java.util.Map;

/**
 * @author Egor Litvinenko
 *
 */
public abstract class AbstractErrorsStore implements ExdictErrorsStore {

    protected Object resource;

    public AbstractErrorsStore(final Object resource) {
	this.resource = resource;
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
