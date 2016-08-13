package org.egorlitvinenko.tools.exdict;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Egor Litvinenko
 *
 */
public class DefaultErrorsStoreProvider implements ErrorsStoreProvider {

    private final Map<String, ExdictErrorsStore> stores;
    private final Map<String, ExceptionInfo> infos;

    public DefaultErrorsStoreProvider() {
	stores = new HashMap<>();
	infos = new HashMap<>();
    }

    @Override
    public void addStore(String property, ExdictErrorsStore store) {
	stores.put(property, store);
    }

    @Override
    public void store(String property, Map<String, Object> values) throws Exception {
	final ExdictErrorsStore store = stores.get(property);
	if (null == store) {
	    throw new Exception("There is not store for property = \"" + property + "\".");
	}
	store.flush(values);
    }

    @Override
    public void flushAll() throws Exception, RuntimeException {
	final PropertyDescriptor[] descriptors = Introspector.getBeanInfo(getExceptionInfoClass())
		.getPropertyDescriptors();
	final Map<String, Map<String, Object>> propertyValues = new HashMap<>();
	final Map<String, String> errors = prepareValues(descriptors, propertyValues);
	if (!errors.isEmpty()) {
	    throw new RuntimeException(String.format("0.300%s", errors));
	}
	propertyValues.forEach((property, values) -> {
	    if (!getExcludedProperties().contains(property)) {
		try {
		    this.store(property, values);
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
	    }
	});
    }

    protected Set<String> getExcludedProperties() {
	final Set<String> ecp = new HashSet<>();
	ecp.add("class");
	return ecp;
    }

    protected Map<String, String> prepareValues(final PropertyDescriptor[] descriptors,
	    final Map<String, Map<String, Object>> propertyValues) {
	final Map<String, String> errors = new HashMap<>();
	for (PropertyDescriptor pd : descriptors) {
	    final Map<String, Object> values = new HashMap<>();
	    propertyValues.put(pd.getName(), values);
	}
	infos.forEach((message, info) -> {
	    for (PropertyDescriptor pd : descriptors) {
		Object value = null;
		try {
		    value = pd.getReadMethod().invoke(info);
		} catch (Exception e) {
		    errors.put(getRecordKeyValue(info), e.getMessage());
		    continue;
		}
		if (null != value) {
		    propertyValues.get(pd.getName()).put(getRecordKeyValue(info), value);
		}
	    }
	});
	return errors;
    }

    protected String getRecordKeyValue(final ExceptionInfo info) {
	String recordCode = ExdictContext.getResolverInitProvider().getExceptionInfosRecordCodeByCode()
		.get(info.getCode());
	if (null == recordCode) {
	    recordCode = String.valueOf(info.getCode());
	}
	return recordCode;
    }

    protected Class<?> getExceptionInfoClass() {
	return ExceptionInfo.class;
    }

    protected void updateValues(final Map<Integer, Object> codeValues, final Map<Integer, Object> messageValues,
	    final Map<Integer, Object> helpMessageValues, final Map<Integer, Object> developerMessageValues) {
	infos.forEach((message, info) -> {
	    codeValues.put(info.getCode(), String.valueOf(info.getCode()));
	    messageValues.put(info.getCode(), info.getMessage());
	    Optional.ofNullable(info.getHelpMessage())
		    .map(msg -> helpMessageValues.put(info.getCode(), info.getHelpMessage()));
	    Optional.ofNullable(info.getDeveloperMessage())
		    .map(msg -> developerMessageValues.put(info.getCode(), info.getDeveloperMessage()));
	});
    }

    @Override
    public void add(ExceptionInfo info) {
	infos.put(info.getMessage(), info);
    }

    @Override
    public ExceptionInfo getByMessage(String message) {
	return infos.get(message);
    }

}