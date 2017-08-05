package org.datastd.tools.exdict.context.impl;

import org.datastd.tools.exdict.context.ExdictErrorsLoader;
import org.datastd.tools.exdict.context.InitializationProvider;
import org.datastd.tools.exdict.exceptions.ExceptionInfo;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Egor Litvinenko
 */
public class DefaultInitializationProvider implements InitializationProvider {

    private List<ExdictErrorsLoader> loaders;
    private Map<String, Integer> exceptionInfosCodeByRecordCode;
    private Map<Integer, String> exceptionInfosRecordCodeByCode;
    private Map<Integer, ExceptionInfo> exceptionInfosByCode;
    private Map<String, ExceptionInfo> exceptionInfosByMessage;
    private ExdictErrorsLoader defaultCodeLoader;

    public DefaultInitializationProvider() {
        loaders = new ArrayList<>();
    }

    @Override
    public void load() throws Exception {
        loadCodes();
        loadExistingExceptionInfos();
    }

    @Override
    public ExdictErrorsLoader getCodeLoader() {
        return defaultCodeLoader;
    }

    @Override
    public void setCodeLoader(ExdictErrorsLoader defaultCodeLoader) {
        this.defaultCodeLoader = defaultCodeLoader;
    }

    @Override
    public List<ExdictErrorsLoader> getLoaders() {
        return loaders;
    }

    @Override
    public void addLoader(ExdictErrorsLoader loader) {
        if (null != loader) {
            loaders.add(loader);
        }
    }

    @Override
    public Map<Integer, ExceptionInfo> getExceptionInfosByCode() {
        if (null == exceptionInfosByCode) {
            throw new RuntimeException("load first");
        }
        return exceptionInfosByCode;
    }

    @Override
    public Map<String, ExceptionInfo> getExceptionInfosByMessage() {
        if (null == exceptionInfosByCode) {
            throw new RuntimeException("load first");
        }
        return exceptionInfosByMessage;
    }

    @Override
    public Map<Integer, String> getExceptionInfosRecordCodeByCode() {
        if (null == exceptionInfosRecordCodeByCode) {
            throw new RuntimeException("load first");
        }
        return exceptionInfosRecordCodeByCode;
    }

    protected void loadCodes() {
        exceptionInfosCodeByRecordCode = new HashMap<>();
        exceptionInfosRecordCodeByCode = new HashMap<>();
        exceptionInfosByCode = new HashMap<>();
        try {
            getCodeLoader().load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        getCodeLoader().getValues().forEach((recordCode, value) -> {
            final Integer code = Integer.valueOf(value.toString());
            exceptionInfosCodeByRecordCode.put(recordCode, code);
            exceptionInfosRecordCodeByCode.put(code, recordCode);
            ExceptionInfo info = exceptionInfosByCode.get(code);
            if (null == info) {
                info = new ExceptionInfo(code);
                exceptionInfosByCode.put(code, info);
            }
            setPropertyValue(info, getCodeLoader().getProperty(), code);
        });
    }

    protected void loadExistingExceptionInfos() {
        loaders.forEach(loader -> {
            try {
                loader.load();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            loader.getValues().forEach((recordCode, value) -> {
                final Integer code = exceptionInfosCodeByRecordCode.get(recordCode);
                ExceptionInfo info = exceptionInfosByCode.get(code);
                if (null == info) {
                    info = new ExceptionInfo(code);
                    exceptionInfosByCode.put(code, info);
                }
                setPropertyValue(info, loader.getProperty(), value.toString());
            });
        });
        exceptionInfosByMessage = new HashMap<>();
        exceptionInfosByCode.forEach((code, info) -> exceptionInfosByMessage.put(info.getMessage(), info));
    }

    protected void setPropertyValue(final ExceptionInfo info, final String fieldName, final Object value) {
        try {
            final PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, info.getClass());
            descriptor.getWriteMethod().invoke(info, value);
        } catch (SecurityException | IntrospectionException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
