/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.utils.sort.impl;

import java.util.List;
import java.util.ArrayList;
import com.hxcel.globalhealth.utils.sort.*;

/**
 * <p>Title: SorterImpl</p>
 * <p>Description: SorterImpl takes a bunch of search criteria and creates an "order by" statement</p>


 *
 * @author Bjorn Harvold

 */

public class SorterImpl implements Sorter {
    private List<SortElement> _sortMap = null;

    public SorterImpl() {
    }

    public static void main(String[] args) {
        Sorter sif = new SorterImpl();
        sif.setSortField("last_name");
        sif.setSortField("first_name");
        sif.setSortField("middle_name");
        sif.setSortField("last_name");
        System.out.println(sif.getOrderByClause());

    }

    /**
     * clearSort
     */
    public void clearSort() {
        _sortMap.clear();
    }

    /**
     * getOrderByClause
     *
     * @return String
     */
    public String getOrderByClause() {
        StringBuffer result = new StringBuffer();
        int counter = 0;

        if (_sortMap != null) {
            for (SortElement orderBy : _sortMap) {
                result.append(orderBy.getSortString());

                if (counter + 1 < _sortMap.size()) {
                    result.append(", ");
                }
                counter++;
            }
        }
        return result.toString();
    }

    public boolean containsFields() {
        boolean result = false;
        if (_sortMap != null) {
            if (_sortMap.size() > 0) {
                result = true;
            }
        }
        return result;
    }

    /**
     * setSortField
     *
     * @param field String
     */
    public void setSortField(String field) {
        SortElement se;
        int index;

        if (_sortMap == null) {
            _sortMap = new ArrayList<SortElement>();
        }

        index = getIndex(field);
        if (index > -1) {
            se = _sortMap.get(getIndex(field));
            se.setField(field);
            // need to remove the old one first
            _sortMap.remove(index);
            // now add
            _sortMap.add(index, se);
        } else {
            se = new SortElement();
            se.setField(field);
            _sortMap.add(_sortMap.size(), se);
        }
    }

    /**
     * returns the current index of the current field.
     * This method is not very efficient. To get the index of a field, it has to run an enumeration through all the fields
     * every time. This beacause we do not know the index of the field and we can't equal a field with the SortElement object
     * that contains the field
     *
     * @param field String
     * @return String
     */
    private int getIndex(String field) {
        int result = -1;

        if (_sortMap != null) {
            for (SortElement se : _sortMap) {
                if (se.getField().trim().equals(field.trim())) {
                    result = _sortMap.indexOf(se);
                }
            }
        }

        return result;
    }

    /**
     * getMessage
     *
     * @return String
     */
    public String toString() {
        return getOrderByClause();
    }

    public int size() {
        int result = -1;

        if (_sortMap != null) {
            result = _sortMap.size();
        }

        return result;
    }

    public SortElement getSortElement(int index) {
        SortElement result = null;

        if (_sortMap != null) {
            result = _sortMap.get(index);
        }

        return result;
    }

    public List<SortElement> getList() {
        return _sortMap;
    }
}
