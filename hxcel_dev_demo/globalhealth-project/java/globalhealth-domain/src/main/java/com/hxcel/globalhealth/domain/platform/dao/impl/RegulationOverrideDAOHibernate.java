package com.hxcel.globalhealth.domain.platform.dao.impl;

import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.platform.model.License;
import com.hxcel.globalhealth.domain.platform.model.Regulation;
import com.hxcel.globalhealth.domain.platform.model.RegulationOverride;
import com.hxcel.globalhealth.domain.platform.model.enums.LicenseStatusCd;
import com.hxcel.globalhealth.domain.platform.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.domain.platform.dao.LicenseDAO;
import com.hxcel.globalhealth.domain.platform.dao.RegulationDAO;
import com.hxcel.globalhealth.domain.platform.dao.RegulationOverrideDAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Disjunction;
import org.apache.commons.lang.StringUtils;

/**
 * User: bjorn
 * Date: Sep 21, 2008
 * Time: 2:08:38 PM
 */
@SuppressWarnings(value = "unchecked")
public class RegulationOverrideDAOHibernate extends AbstractHibernateDAO<RegulationOverride, String> implements RegulationOverrideDAO {

    public List<RegulationOverride> searchForRegulationOverridesByCountry(String countryId, String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(RegulationOverride.class);

        c.add(Restrictions.eq("country.id", countryId));
        c = c.createAlias("original", "original");

        if (StringUtils.isNotBlank(name)) {
            Disjunction d = Restrictions.disjunction();
            d.add(Restrictions.ilike("original.key", name + "%"));
            d.add(Restrictions.ilike("original.value", name + "%"));
            d.add(Restrictions.ilike("value", name + "%"));
            c.add(d);

        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.asc("original.key"));

        return c.list();
    }

    public Integer searchForRegulationOverridesByCountryCount(String countryId, String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(RegulationOverride.class);

        c.add(Restrictions.eq("country.id", countryId));
        c = c.createAlias("original", "original");

        if (StringUtils.isNotBlank(name)) {
            Disjunction d = Restrictions.disjunction();
            d.add(Restrictions.ilike("original.key", name + "%"));
            d.add(Restrictions.ilike("original.value", name + "%"));
            d.add(Restrictions.ilike("value", name + "%"));
            c.add(d);
        }

        c.setProjection(Projections.count("id"));

        return (Integer) c.uniqueResult();
    }

    public List<RegulationOverride> getOverridesByRegulationId(String regulationId) {
        Criteria c = getSession().createCriteria(RegulationOverride.class);

        c.add(Restrictions.eq("original.id", regulationId));

        return c.list();
    }
}