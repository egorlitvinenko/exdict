package org.egorlitvinenko.tools.exdict;

import java.util.Map;

/**
 * @author Egor Litvinenko
 *
 */
public class DefaultCodeGenerator extends AbstractCodeGenerator implements ExdictCodeGenerator {

    public DefaultCodeGenerator() {
	super();
    }

    public DefaultCodeGenerator(INamespace namespace) {
	super(namespace);
    }

    protected void initGroups(final Map<String, Integer> groupInitialCodes, final Map<String, Integer> groupLastCodes,
	    final ExceptionInfo info) {
	final String group = namespace.getGroupInfoHelper().getGroupName(info);
	if (!groupInitialCodes.containsKey(group)) {
	    groupInitialCodes.put(group, Integer.MAX_VALUE);
	}
	if (!groupLastCodes.containsKey(group)) {
	    groupLastCodes.put(group, Integer.MIN_VALUE);
	}
	Integer code = groupInitialCodes.get(group);
	if (getCodeComparator().compare(code, info.getCode()) > 0) {
	    groupInitialCodes.put(group, info.getCode());
	}
	code = groupLastCodes.get(group);
	if (getCodeComparator().compare(code, info.getCode()) < 0) {
	    groupLastCodes.put(group, info.getCode());
	}
    }

}
