package org.egorlitvinenko.tools.exdict;

import java.util.Map;

/**
 * @author Egor Litvinenko
 *
 */
public interface IGroupInfoHelper {

    Map<String, GroupInfo> getGroupInfos();

    void addGroup(final String group, final Integer initialCode);

    public String getGroupDelimiter();

    public String getDefaultGroup();

    public Integer getDefaultInitialCode();

    public String getGroupName(final ExceptionInfo info);

    public String getFullMessage(final String group, final String message);

    public void setGroupName(final ExceptionInfo info, final String groupName);

}
