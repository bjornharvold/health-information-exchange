package com.hxcel.globalhealth.platform.tiles;

/**
 * User: Bjorn Harvold
 * Date: Apr 4, 2009
 * Time: 10:19:48 PM
 * Responsibility:
 */
import javax.servlet.ServletContext;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.factory.AbstractTilesContainerFactory;
import org.apache.tiles.servlet.context.wildcard.WildcardServletTilesApplicationContext;
import org.apache.tiles.startup.BasicTilesInitializer;
import org.apache.tiles.startup.TilesInitializer;
import org.apache.tiles.web.startup.TilesListener;

/**
 * Test Tiles listener for Tiles initialization of the default container.
 *
 * @version $Rev: 733753 $ $Date: 2009-01-12 20:52:54 +0700 (Mon, 12 Jan 2009) $
 */
public class CustomTilesListener extends TilesListener {

    /** {@inheritDoc} */
    @Override
    protected TilesInitializer createTilesInitializer() {
        return new CustomTilesListenerInitializer();
    }

    /**
     * Test Tiles initializer for Tiles initialization of the default container.
     */
    private static class CustomTilesListenerInitializer extends BasicTilesInitializer {

        /** {@inheritDoc} */
        @Override
        protected AbstractTilesContainerFactory createContainerFactory(
                TilesApplicationContext context) {
            return new CustomTilesContainerFactory();
        }

        /** {@inheritDoc} */
        @Override
        protected TilesApplicationContext createTilesApplicationContext(
                TilesApplicationContext preliminaryContext) {
            return new WildcardServletTilesApplicationContext(
                    (ServletContext) preliminaryContext.getContext());
        }
    }
}
