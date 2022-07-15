package com.hxcel.globalhealth.domain.phr.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;

/**
 * User: bjorn
 * Date: Sep 1, 2008
 * Time: 3:46:28 PM
 * Helps create common criteria for phr section
 */
public class PhrCriteriaHelper {
    public static Criteria getRecords(Criteria c, String phrId) {
        c.add(Restrictions.eq("phr.id", phrId));

        return c;
    }

    public static Criteria getRecordsByCreator(Criteria c, String phrId, String recordCreatorId, EntityTypeCd entityType) {
        c.add(Restrictions.eq("phr.id", phrId));
        c.add(Restrictions.eq("recordCreatorId", recordCreatorId));
        c.add(Restrictions.eq("recordCreatorType", entityType));

        return c;
    }

    public static Criteria getRecordsWithPermissions(Criteria c, String phrId, String entityId, EntityTypeCd entityType) {
        c.add(Restrictions.eq("phr.id", phrId));
        c.createAlias("permissions", "perms")
                .add(Restrictions.eq("perms.entityType", entityType))
                .add(Restrictions.eq("perms.entityId", entityId));
        
        return c;
    }
}
