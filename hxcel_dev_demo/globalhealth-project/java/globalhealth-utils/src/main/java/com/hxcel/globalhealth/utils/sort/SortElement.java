/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.utils.sort;

/**
 * <p/>
 * Title: SortElement
 * </p><p>
 * Description: This sorter tracks on column to sort on. Every time that column
 * is checked (comes in from the GUI), the sort order reverses itself
 * </p><p>

 * </p><p>
 * Health XCEL Inc.
 * </p>
 *
 * @author Bjorn Harvold

 */

public class SortElement {
    public final static String ASCENDING = "ASC";
    public final static String DESCENDING = "DESC";
    private String _currentSortOrder = null;
    private String _field = null;

    public SortElement() {
    }

    public static void main(String[] args) {
        SortElement sortElement1 = new SortElement();
    }

    public void setField(String fieldName) {
        _field = fieldName;

        if (_currentSortOrder == null) {
            _currentSortOrder = ASCENDING;
        } else if (_currentSortOrder.equals(ASCENDING)) {
            _currentSortOrder = DESCENDING;
        } else {
            _currentSortOrder = ASCENDING;
        }
    }

    public String getField() {
        return _field;
    }

    public String getSortOrder() {
        return _currentSortOrder;
    }

    public String getSortString() {
        return _field;
    }
}




