/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.c2b2.jsr107.memcached.test;

import java.util.Properties;
import javax.cache.Caching;
import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.c2b2.jsr107.memcached.spi.MemcachedCachingProvider;

/**
 *
 * @author steve
 */
public class CachingProviderTests extends TestCase {

    @Test
    public void testDefaultProvider () {
        assertTrue("Caching Provider is not the memcached provider", Caching.getCachingProvider() instanceof MemcachedCachingProvider);
    }
    
    @Test
    public void testDefaultProperties() {
        Properties props = Caching.getCachingProvider().getDefaultProperties();
        assertEquals(props.getProperty(MemcachedCachingProvider.MEMCACHED_PROPERTIES.ADDRESS.toString()),"127.0.0.1:11211");
        assertEquals(props.getProperty(MemcachedCachingProvider.MEMCACHED_PROPERTIES.PREFIXKEY.toString()),"false");
        assertEquals(props.getProperty(MemcachedCachingProvider.MEMCACHED_PROPERTIES.BINARY.toString()), "true");
    }
}
