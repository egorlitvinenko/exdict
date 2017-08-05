package org.datastd.tools.exdict.context.impl;

import org.datastd.tools.exdict.context.ExdictCodeGenerator;
import org.datastd.tools.exdict.context.ExdictContext;
import org.datastd.tools.exdict.context.Namespace;
import org.datastd.tools.exdict.context.InitializationProvider;
import org.datastd.tools.exdict.exceptions.ExceptionInfo;
import org.datastd.tools.exdict.exceptions.GroupInfo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Egor Litvinenko
 */
public abstract class AbstractCodeGenerator implements ExdictCodeGenerator {

    protected final Namespace namespace;

    public AbstractCodeGenerator() {
        namespace = ExdictContext.defaultNamespace();
    }

    public AbstractCodeGenerator(Namespace namespace) {
        this.namespace = namespace;
    }

    @Override
    public void init(InitializationProvider resolverProvider) {
        final Map<String, Integer> groupInitialCodes = new HashMap<>();
        final Map<String, Integer> groupLastCodes = new HashMap<>();
        resolverProvider.getExceptionInfosByCode()
                .forEach((codeStr, info) -> this.initGroups(groupInitialCodes, groupLastCodes, info));
        groupInitialCodes.keySet().forEach(group -> {
            namespace.getGroupInfoHelper().addGroup(group, groupInitialCodes.get(group));
            namespace.getGroupInfoHelper().getGroupInfos().get(group).setLastCode(groupLastCodes.get(group));
        });
    }

    @Override
    public Integer next() {
        return nextForGroup(namespace.getGroupInfoHelper().getDefaultGroup());
    }

    @Override
    public Integer nextAfterCode(Integer code) {
        return code + 1;
    }

    @Override
    public Integer nextForGroup(final String group) {
        final GroupInfo info = namespace.getGroupInfoHelper().getGroupInfos().get(group);
        if (null == info) {
            throw new RuntimeException("unknown group - " + group);
        }
        Integer last;
        synchronized (namespace.getGroupInfoHelper()) {
            last = nextAfterCode(info.getLastCode());
            info.setLastCode(last);
        }
        return last;
    }

    @Override
    public Comparator<Integer> getCodeComparator() {
        return integerComparator;
    }

    private final static Comparator<Integer> integerComparator = Integer::compareTo;

    protected abstract void initGroups(final Map<String, Integer> groupInitialCodes,
                                       final Map<String, Integer> groupLastCodes, final ExceptionInfo info);

}
