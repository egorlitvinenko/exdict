package org.datastd.tools.exdict.context.impl;

import org.datastd.tools.exdict.context.LocaleResolver;

import java.util.Locale;

/**
 * Created by Egor Litvinenko on 04.02.17.
 */
public class DefaultLocaleResolver implements LocaleResolver {

    @Override
    public Locale getCurrentLocale() {
        return Locale.getDefault();
    }

}
