package org.egorlitvinenko.tools.exdict;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Egor Litvinenko
 *
 */
public abstract class AbstractCodeGenerator implements ExdictCodeGenerator {

    @Override
    public void init(ResolverInitProvider resolverProvider) {
	final Map<String, Integer> groupInitialCodes = new HashMap<>();
	final Map<String, Integer> groupLastCodes = new HashMap<>();
	resolverProvider.getExceptionInfosByCode()
		.forEach((codeStr, info) -> this.initGroups(groupInitialCodes, groupLastCodes, info));
	groupInitialCodes.keySet().forEach(group -> {
	    ExdictContext.getGroupInfoHelper().addGroup(group, groupInitialCodes.get(group));
	    ExdictContext.getGroupInfoHelper().getGroupInfos().get(group).setLastCode(groupLastCodes.get(group));
	});
    }

    @Override
    public Integer next() {
	return nextForGroup(ExdictContext.getGroupInfoHelper().getDefaultGroup());
    }

    @Override
    public Integer nextAfterCode(Integer code) {
	return code + 1;
    }

    @Override
    public Integer nextForGroup(final String group) {
	final GroupInfo info = ExdictContext.getGroupInfoHelper().getGroupInfos().get(group);
	if (null == info) {
	    throw new RuntimeException("unknown group - " + group);
	}
	Integer last = null;
	synchronized (info) {
	    final Integer flast = nextAfterCode(info.getLastCode());
	    info.setLastCode(flast);
	    last = new Integer(flast);
	}
	return last;
    }

    @Override
    public Comparator<Integer> getCodeComparator() {
	return integerComparator;
    }

    private final static Comparator<Integer> integerComparator = new Comparator<Integer>() {
	@Override
	public int compare(Integer o1, Integer o2) {
	    return o1.compareTo(o2);
	}
    };

    protected abstract void initGroups(final Map<String, Integer> groupInitialCodes,
	    final Map<String, Integer> groupLastCodes, final ExceptionInfo info);

}
