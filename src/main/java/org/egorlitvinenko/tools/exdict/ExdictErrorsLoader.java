package org.egorlitvinenko.tools.exdict;

import java.util.Map;

public interface ExdictErrorsLoader {

    String getProperty();

    void load() throws Exception;

    void load(final Object resource) throws Exception;

    void loadSilent();

    Object getValue(final String code);

    Map<String, Object> getValues();

}
