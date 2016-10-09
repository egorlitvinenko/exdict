package org.datastd.tools.exdict.context;

import org.datastd.tools.exdict.exceptions.ExceptionInfo;

import java.util.Map;

/**
 * @author Egor Litvinenko
 */
public interface ErrorsStoreProvider {

    void addStore(final ExdictErrorsStore store);

    void store(final String property, final Map<String, Object> values) throws Exception;

    void flushAll() throws Exception;

    void add(final ExceptionInfo info);

    ExceptionInfo getByMessage(final String message);

}
