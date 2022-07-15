/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.common.propertyeditor;

import java.beans.PropertyEditorSupport;

/**
 * User: Bjorn Harvold
 * Date: Aug 2, 2007
 * Time: 1:17:13 PM
 */
public final class EnumPropertyEditor extends PropertyEditorSupport {

    private final Class<? extends Enum> c;

    public EnumPropertyEditor(Class<? extends Enum> c) {
        this.c = c;
    }

    public String[] getTags() {
        try {
            Object[] values = (Object[]) c.getMethod("values").invoke(null); // NOI18N
            String[] tags = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                tags[i] = values[i].toString();
            }
            return tags;
        } catch (Exception x) {
            throw new AssertionError(x);
        }
    }

    public String getAsText() {
        Object o = getValue();
        return o != null ? o.toString() : "";
    }

    @SuppressWarnings("unchecked")
    public void setAsText(String text) throws IllegalArgumentException {
        if (text.length() > 0) {
            setValue(Enum.valueOf(c, text));
        } else {
            setValue(null);
        }
    }

    public String getJavaInitializationString() {
        Enum e = (Enum) getValue();
        return e != null ? c.getName().replace('$', '.') + '.' + e.name() : "null"; // NOI18N
    }

}
