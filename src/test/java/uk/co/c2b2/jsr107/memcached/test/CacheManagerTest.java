/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.c2b2.jsr107.memcached.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author steve
 */
public class CacheManagerTest {
    
    @Test
    public void testGetURI() {
        CacheManager manager = Caching.getCachingProvider().getCacheManager();
        assertEquals(manager.getURI(), Caching.getCachingProvider().getDefaultURI());
    }
    
    @Test
    public void testGetNonDefaultURI() throws URISyntaxException {
        CachingProvider provider = Caching.getCachingProvider();
        URI uri = new URI("memcached://differentconfig.properties");
        CacheManager testManager = provider.getCacheManager(uri, provider.getDefaultClassLoader());
        assertEquals(testManager.getURI(), uri);
    }
    
    @Test
    public void testGetProperties() throws URISyntaxException {
        CachingProvider provider = Caching.getCachingProvider();
        Properties p = new Properties();
        p.put("test", "testVal");
        URI uri = new URI("memcached://getproperties.properties");
        CacheManager testManager = provider.getCacheManager(uri, provider.getDefaultClassLoader(), p);
        assertEquals(testManager.getProperties().get("test"),"testVal"); 
    }
    
    @Test
    public void testGetCachingProvider() {
        CacheManager manager = Caching.getCachingProvider().getCacheManager();
        assertEquals(Caching.getCachingProvider(),manager.getCachingProvider());
    }
    
    @Test
    public void testIsClosed() throws URISyntaxException {
        CachingProvider provider = Caching.getCachingProvider();

        URI uri = new URI("memcached://isclosed.properties");
        CacheManager testManager = provider.getCacheManager(uri, provider.getDefaultClassLoader());
        assertFalse(testManager.isClosed());
        testManager.close();
        assertTrue(testManager.isClosed());
        
    }
    
}
