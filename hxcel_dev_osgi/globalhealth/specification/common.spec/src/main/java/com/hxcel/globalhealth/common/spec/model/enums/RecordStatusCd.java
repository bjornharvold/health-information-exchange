/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.common.spec.model.enums;

/**
 * User: bjorn
 * Date: Nov 18, 2007
 * Time: 8:43:37 PM
 * Record status should be used as part of the record workflow. If a professional writes a record on behalf of the
 * client. The client still needs to approve before it is a validated record
 */
public enum RecordStatusCd {
    ACTIVE, INACTIVE, PENDING_CLIENT_APPROVAL, REMOVED
}
