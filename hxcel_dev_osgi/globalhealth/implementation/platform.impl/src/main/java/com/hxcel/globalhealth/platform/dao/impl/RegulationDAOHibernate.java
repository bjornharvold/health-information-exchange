package com.hxcel.globalhealth.platform.dao.impl;

import com.hxcel.globalhealth.common.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.model.Regulation;
import com.hxcel.globalhealth.platform.dao.RegulationDAO;

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
public class RegulationDAOHibernate extends AbstractHibernateDAO<Regulation, String> implements RegulationDAO {

    public List<Regulation> searchForRegulations(String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Regulation.class);

        if (StringUtils.isNotBlank(name)) {
            Disjunction d = Restrictions.disjunction();
            d.add(Restrictions.ilike("key", name + "%"));
            d.add(Restrictions.ilike("value", name + "%"));
            d.add(Restrictions.ilike("name", name + "%"));

            c.add(d);
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.asc("name"));

        return c.list();
    }

    public Integer searchForRegulationsCount(String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(Regulation.class);

        if (StringUtils.isNotBlank(name)) {
            Disjunction d = Restrictions.disjunction();
            d.add(Restrictions.ilike("key", name + "%"));
            d.add(Restrictions.ilike("value", name + "%"));
            d.add(Restrictions.ilike("name", name + "%"));
        }

        c.setProjection(Projections.count("id"));

        return (Integer) c.uniqueResult();
    }

    public Regulation getRegulation(String id) throws PersistenceException {
        return get(Regulation.class, id);
    }

    public List<Regulation> getLastModifiedRegulations(Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Regulation.class);

        if (maxResults != null) {
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.desc("lastUpdate"));

        return c.list();
    }
}