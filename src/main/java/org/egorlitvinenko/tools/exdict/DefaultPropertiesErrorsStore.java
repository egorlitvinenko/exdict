package org.egorlitvinenko.tools.exdict;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @author Egor Litvinenko
 *
 */
public class DefaultPropertiesErrorsStore extends AbstractErrorsStore implements ExdictErrorsStore {

    private final Properties properties;

    public DefaultPropertiesErrorsStore(final Object resource) {
	super(resource);
	properties = new Properties();
    }

    protected OutputStream getOutputStream() throws Exception {
	return new FileOutputStream(new File(resource.toString()));
    }

    @Override
    public void flush(Map<String, Object> values) throws Exception {
	values.forEach((code, value) -> properties.put(code, value.toString()));
	final OutputStream os = getOutputStream();
	properties.store(os, "Exdict flush properties");
	os.close();
    }

}
