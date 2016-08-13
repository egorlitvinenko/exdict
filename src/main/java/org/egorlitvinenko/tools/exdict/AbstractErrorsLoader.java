package org.egorlitvinenko.tools.exdict;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Egor Litvinenko
 *
 */
public abstract class AbstractErrorsLoader implements ExdictErrorsLoader {

    protected final String property;
    protected Set<String> loadedResource = new HashSet<>();
    protected String resource;
    protected Map<String, Object> values;

    public AbstractErrorsLoader(final String property) {
	this.property = property;
	values = new HashMap<>();
    }

    public AbstractErrorsLoader(final String property, final String resource) {
	this(property);
	this.resource = resource;
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
	    throw new Exception("resource is null");
	}
	if (!loadedResource.contains(resource)) {
	    loadExternal(resource);
	    loadedResource.add(resource.toString());
	}
    }

    protected abstract void loadExternal(Object resource) throws Exception;

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
