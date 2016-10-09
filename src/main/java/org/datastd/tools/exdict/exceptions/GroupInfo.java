package org.datastd.tools.exdict.exceptions;

/**
 * @author Egor Litvinenko
 */
public class GroupInfo {

    private final String name;
    private Integer initialCode;
    private Integer lastCode;

    public GroupInfo(final String name, Integer initialCode, Integer lastCode) {
        this.name = String.copyValueOf(name.toCharArray());
        this.initialCode = initialCode;
        this.lastCode = lastCode;
    }

    public String getName() {
        return name;
    }

    public Integer getInitialCode() {
        return initialCode;
    }

    public void setInitialCode(Integer initialCode) {
        this.initialCode = initialCode;
    }

    public Integer getLastCode() {
        return lastCode;
    }

    public void setLastCode(Integer lastCode) {
        this.lastCode = lastCode;
    }

}
