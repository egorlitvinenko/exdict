package org.egorlitvinenko.tools.exdict;

import java.util.Map;

/**
 * @author Egor Litvinenko
 *
 */
public interface ExdictErrorsStore {

    void flush(final Map<String, Object> values) throws Exception;

    void append(final Map<String, Object> values) throws Exception;

    void remove(final Map<String, Object> values) throws Exception;

}
