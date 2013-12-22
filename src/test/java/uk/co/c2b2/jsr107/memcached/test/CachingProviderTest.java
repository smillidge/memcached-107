/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.c2b2.jsr107.memcached.test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.OptionalFeature;
import javax.cache.spi.CachingProvider;
import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.c2b2.jsr107.memcached.MemcachedCacheManager;
import uk.co.c2b2.jsr107.memcached.spi.MemcachedCachingProvider;

/**
 *
 * @author steve
 */
public class CachingProviderTest {

    @Test
    public void testDefaultProvider () {
        assertTrue("Caching Provider is not the memcached provider", Caching.getCachingProvider() instanceof MemcachedCachingProvider);
    }
    
    @Test
    public void testDefaultProperties() {
        Properties props = Caching.getCachingProvider().getDefaultProperties();
        assertEquals(props.getProperty(MemcachedCachingProvider.MEMCACHED_PROPERTIES.ADDRESS.name()),"127.0.0.1:11211");
        assertEquals(props.getProperty(MemcachedCachingProvider.MEMCACHED_PROPERTIES.PREFIXKEY.name()),"false");
        assertEquals(props.getProperty(MemcachedCachingProvider.MEMCACHED_PROPERTIES.BINARY.name()), "true");
    }
    
    @Test
    public void testDefaultClassLoader() {
        assertEquals(Caching.getCachingProvider().getDefaultClassLoader(), Caching.getCachingProvider().getClass().getClassLoader());
    }
    
    @Test 
    public void testDefaultURI() {
        assertEquals(Caching.getCachingProvider().getDefaultURI().toASCIIString(), "memcached://default-cache-config.properties");
       
    }
    
    @Test
    public void testGetDefaultCacheManager() throws URISyntaxException {
        // first test default
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager defaultManager = provider.getCacheManager();
        assertTrue(defaultManager instanceof MemcachedCacheManager);
        
        // check we now have the default one and it matches the default URI
        assertEquals(defaultManager, provider.getCacheManager(provider.getDefaultURI(), provider.getDefaultClassLoader()));
    }
    
    @Test
    public void testGetNonDefaultCacheManager() throws URISyntaxException {
        // first test default
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager defaultManager = provider.getCacheManager();

        // check we get a different one
        URI uri = new URI("memcached://differentconfig.properties");
        CacheManager testManager = provider.getCacheManager(uri, provider.getDefaultClassLoader());
        assertNotEquals(defaultManager, testManager);
        
        assertEquals(testManager, provider.getCacheManager(uri, provider.getDefaultClassLoader()));
    }
    
    @Test
    public void testIsSupported() {
        CachingProvider provider = Caching.getCachingProvider();
        assertFalse(provider.isSupported(OptionalFeature.STORE_BY_REFERENCE));
    }
    
    @Test
    public void testCloseSpecific() throws URISyntaxException {
        // first test default
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager defaultManager = provider.getCacheManager();
        URI uri = new URI("memcached://differentconfig.properties");
        CacheManager testManager = provider.getCacheManager(uri, provider.getDefaultClassLoader());
        provider.close(uri, provider.getDefaultClassLoader());
        assertTrue(testManager.isClosed());
        assertFalse(defaultManager.isClosed());
        assertNotEquals(testManager,provider.getCacheManager(uri, provider.getDefaultClassLoader()));      
    }
    
    @Test
    public void testCloseAll() throws URISyntaxException {
        // first test default
        CachingProvider provider = Caching.getCachingProvider();
        URI uri = new URI("memcached://differentconfig.properties");
        CacheManager testManager = provider.getCacheManager(uri, provider.getDefaultClassLoader());
        CacheManager defaultManager = provider.getCacheManager();
        provider.close();
        assertTrue(testManager.isClosed());
        assertNotEquals(testManager,provider.getCacheManager(uri, provider.getDefaultClassLoader()));      
        assertTrue(defaultManager.isClosed());
        assertNotEquals(defaultManager,provider.getCacheManager());      
    }
    
    @Test
    public void testCloseClassloader() throws URISyntaxException, MalformedURLException {
        // first test default
        CachingProvider provider = Caching.getCachingProvider();
        URI uri = new URI("memcached://differentconfig.properties");
        CacheManager testManager = provider.getCacheManager(uri, provider.getDefaultClassLoader());
        URL urls[] = { new URL("http://127.0.0.1") };
        URLClassLoader testloader = new URLClassLoader(urls);
        CacheManager testManager2 = provider.getCacheManager(uri,testloader);
        CacheManager defaultManager = provider.getCacheManager();
        provider.close(testloader);
        assertFalse(testManager.isClosed());
        assertFalse(defaultManager.isClosed());
        assertTrue(testManager2.isClosed());
        assertNotEquals(testManager2,provider.getCacheManager(uri,testloader));      
    }
}
