/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.c2b2.jsr107.memcached.spi;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Properties;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.cache.CacheManager;
import javax.cache.configuration.OptionalFeature;
import javax.cache.spi.CachingProvider;
import uk.co.c2b2.jsr107.memcached.MemcachedCacheManager;

/**
 *
 * @author steve
 */
public class MemcachedCachingProvider implements CachingProvider {
    
    public static enum MEMCACHED_PROPERTIES {
        ADDRESS,
        BINARY,
        PREFIXKEY
    }
    
    
    public MemcachedCachingProvider() {
        cacheManagerMap = new WeakHashMap<ClassLoader, HashMap<URI, CacheManager>>();
    }

    public CacheManager getCacheManager(URI uri, ClassLoader classLoader, Properties properties) {
        CacheManager result = null;
        HashMap<URI,CacheManager> cacheManagers4ClassLoader = cacheManagerMap.get(classLoader);
        if (cacheManagers4ClassLoader == null ) {
            cacheManagers4ClassLoader = new HashMap<URI,CacheManager>();
            result = new MemcachedCacheManager(properties,uri,this,classLoader);
            cacheManagers4ClassLoader.put(uri, result);
        } else {
            result = cacheManagers4ClassLoader.get(uri);
            if (result == null) {
             result = new MemcachedCacheManager(properties,uri,this,classLoader);
             cacheManagers4ClassLoader.put(uri, result);
            }
        }
        return result;
    }

    public ClassLoader getDefaultClassLoader() {
        return getClass().getClassLoader();
    }

    public URI getDefaultURI() {
        try {
            return new URI("memcached://default-cache-config.xml");
        } catch (URISyntaxException ex) {
            Logger.getLogger(MemcachedCachingProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Properties getDefaultProperties() {
        Properties result = new Properties();
        result.put(MEMCACHED_PROPERTIES.ADDRESS, "127.0.0.1:11211");
        result.put(MEMCACHED_PROPERTIES.BINARY, "true");
        return result;
    }

    public CacheManager getCacheManager(URI uri, ClassLoader classLoader) {
        return getCacheManager(uri, classLoader, getDefaultProperties());
    }

    public CacheManager getCacheManager() {
        return getCacheManager(getDefaultURI(), getDefaultClassLoader(), getDefaultProperties());
    }

    public void close() {
        for (HashMap<URI,CacheManager> map : cacheManagerMap.values()) {
            for (CacheManager cm : map.values()) {
                cm.close();
            }
        }
        cacheManagerMap.clear();
    }

    public void close(ClassLoader classLoader) {
        HashMap<URI,CacheManager> cacheManagers = cacheManagerMap.get(classLoader);
        if (cacheManagers != null) {
            for (CacheManager manager : cacheManagers.values()) {
                manager.close();
            }
            // remove the classloader from the map of cache managers
            cacheManagerMap.remove(classLoader);
        }
    }

    public void close(URI uri, ClassLoader classLoader) {
        CacheManager cm = getCacheManager(uri, classLoader);
        if (cm != null) {
            // remove the closed cachemanager from the map
            cacheManagerMap.get(classLoader).remove(uri);
            cm.close();
        }
    }

    public boolean isSupported(OptionalFeature optionalFeature) {
        if (optionalFeature == OptionalFeature.STORE_BY_REFERENCE) {
            return false;
        }
        return true;
    }
    
    
    WeakHashMap<ClassLoader, HashMap<URI, CacheManager>> cacheManagerMap;
    
}
