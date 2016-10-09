package org.datastd.tools.exdict.context;

import org.datastd.tools.exdict.exceptions.ExceptionInfo;
import org.datastd.tools.exdict.exceptions.GroupInfo;

import java.util.Map;

/**
 * @author Egor Litvinenko
 *
 */
public interface IGroupInfoHelper {

    Map<String, GroupInfo> getGroupInfos();

    void addGroup(final String group, final Integer initialCode);

    String getGroupDelimiter();

    String getDefaultGroup();

    Integer getDefaultInitialCode();

    String getGroupName(final ExceptionInfo info);

    String getFullMessage(final String group, final String message);

    void setGroupName(final ExceptionInfo info, final String groupName);

}
