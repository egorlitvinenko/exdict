package org.datastd.tools.exdict.context;

import java.util.Comparator;

/**
 * @author Egor Litvinenko
 */
public interface ExdictCodeGenerator {

    void init(final InitializationProvider resolverProvider);

    Comparator<Integer> getCodeComparator();

    Integer next();

    Integer nextAfterCode(final Integer code);

    Integer nextForGroup(final String group);

}
