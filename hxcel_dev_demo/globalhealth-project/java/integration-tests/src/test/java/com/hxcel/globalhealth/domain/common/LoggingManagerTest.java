package com.hxcel.globalhealth.domain.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hxcel.globalhealth.domain.common.model.News;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.AbstractIntegrationBaseTest;

import java.util.Date;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Sep 14, 2008
 * Time: 8:11:28 PM
 * Description:
 */
public class LoggingManagerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(LoggingManagerTest.class);

    @After
    public void onTearDown() {
        try {
            loggingManager.deleteUserNews(getUser().getId());
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void testNews() {

        try {
            log.info("First we create a news item and save it");
            News news = loggingManager.createNewsEntry(createNews());
            assertNotNull("News is empty", news);
            assertNotNull("News is missing uuid", news.getId());

            log.info("Now we go ahead an retrieve that same news item");
            List<News> list = loggingManager.getNews(getUser().getId(), EntityTypeCd.USER, null, null);
            assertNotNull("No news found", list);
            assertEquals("List contains less or more than one entry", 1, list.size());
            assertEquals("UUID is not the same as the one news entity we saved previously", news.getId(), list.get(0).getId());

            // TODO remove news item here
            log.info("All news tests passed successfully");
        } catch (DomainException e) {
            fail();
        }
    }

    private News createNews() {
        News news = new News();
        news.setAutoEntry(false);
        news.setContent(RandomStringUtils.randomAlphabetic(5000));
        news.setTitle(RandomStringUtils.randomAlphabetic(50));
        news.setEntityId(getUser().getId());
        news.setEntityType(EntityTypeCd.USER);
        news.setPublishDate(new Date());

        return news;
    }

    // Spring IoC
    @Autowired
    private LoggingManager loggingManager;
}
