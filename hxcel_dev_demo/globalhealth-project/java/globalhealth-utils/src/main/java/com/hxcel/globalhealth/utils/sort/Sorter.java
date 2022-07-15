/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.utils.sort;

import java.util.List;

/**
 * <p>Title: </p>
 * <p>Description: </p>


 *
 * @author Bjorn Harvold

 */

public interface Sorter {
    public String getOrderByClause();

    public void setSortField(String field);

    public void clearSort();

    public boolean containsFields();

    public int size();

    public SortElement getSortElement(int index);

    public List<SortElement> getList();
}
