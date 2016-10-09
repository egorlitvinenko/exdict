package org.datastd.tools.exdict.context;

import org.datastd.tools.exdict.exceptions.ExceptionInfo;

import java.util.List;
import java.util.Map;

/**
 * @author Egor Litvinenko
 */
public interface ResolverInitProvider {

    ExdictErrorsLoader getCodeLoader();

    void setCodeLoader(ExdictErrorsLoader loader);

    List<ExdictErrorsLoader> getLoaders();

    void addLoader(final ExdictErrorsLoader loader);

    void load() throws Exception;

    Map<Integer, ExceptionInfo> getExceptionInfosByCode();

    Map<String, ExceptionInfo> getExceptionInfosByMessage();

    Map<Integer, String> getExceptionInfosRecordCodeByCode();

}
