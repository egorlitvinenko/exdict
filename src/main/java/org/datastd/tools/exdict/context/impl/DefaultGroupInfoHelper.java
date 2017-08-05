package org.datastd.tools.exdict.context.impl;

import org.datastd.tools.exdict.context.GroupInfoHelper;
import org.datastd.tools.exdict.exceptions.ExceptionInfo;
import org.datastd.tools.exdict.exceptions.GroupInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Egor Litvinenko
 */
public class DefaultGroupInfoHelper implements GroupInfoHelper {

    protected Map<String, GroupInfo> groupInfos;

    public static String DEFAULT_GROUP = "DEFAULT_GROUP";
    public static String DEFAULT_GROUP_DELIMITER = ":";
    public static int DEFAULT_GROUP_INITIAL_CODE = 10000;

    public DefaultGroupInfoHelper() {
        groupInfos = new HashMap<>();
//        TODO delete in future?
        addGroup(getDefaultGroup(), getDefaultInitialCode());
    }

    @Override
    public Map<String, GroupInfo> getGroupInfos() {
        return groupInfos;
    }

    @Override
    public String getGroupDelimiter() {
        return DEFAULT_GROUP_DELIMITER;
    }

    @Override
    public String getDefaultGroup() {
        return DEFAULT_GROUP;
    }

    @Override
    public Integer getDefaultInitialCode() {
        return DEFAULT_GROUP_INITIAL_CODE;
    }

    @Override
    public void addGroup(String group, Integer initialCode) {
        if (!groupInfos.containsKey(group)) {
            final String localGroup = String.copyValueOf(group.toCharArray());
            groupInfos.put(group, new GroupInfo(localGroup, initialCode, initialCode));
        }
    }

    @Override
    public String getGroupName(final ExceptionInfo info) {
        return Optional.of(info).map(ExceptionInfo::getMessage).map(message -> {
            final int indexOfGroupDelimiter = message.indexOf(getGroupDelimiter());
            return -1 == indexOfGroupDelimiter ? null : message.substring(0, indexOfGroupDelimiter).trim();
        }).orElse(getDefaultGroup());
    }

    @Override
    public String getFullMessage(String group, String message) {
        if (group.equals(getDefaultGroup())) {
            return message;
        }
        return group + getGroupDelimiter() + message;
    }

    @Override
    public void setGroupName(final ExceptionInfo info, final String groupName) {
        final String currentGroup = getGroupName(info);
        if (!currentGroup.equals(groupName)) {
            final StringBuilder sb = new StringBuilder(info.getMessage());
            if (!currentGroup.equals(getDefaultGroup())) {
                sb.delete(0, currentGroup.length() + getGroupDelimiter().length());
            }
            if (!groupName.equals(getDefaultGroup())) {
                sb.insert(0, groupName + getGroupDelimiter());
            }
            info.setMessage(sb.toString());
        }
    }

}
