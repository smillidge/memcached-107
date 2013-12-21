/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.c2b2.jsr107.memcached;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.HashMap;
import java.util.Properties;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;

/**
 *
 * @author steve
 */
public class MemcachedCacheManager implements CacheManager {
    private Properties properties;
    private URI uri;
    private CachingProvider provider;
    WeakReference<ClassLoader> loader;
    private final HashMap<String, MemcachedCache<?, ?>> caches = new HashMap<String, MemcachedCache<?, ?>>();

    public MemcachedCacheManager(Properties properties, URI uri, CachingProvider provider, ClassLoader loader) {
        this.properties = properties;
        this.uri = uri;
        this.provider = provider;
        this.loader = new WeakReference<ClassLoader>(loader);
    }

    public CachingProvider getCachingProvider() {
        return provider;
    }

    public URI getURI() {
        return uri;
    }

    public ClassLoader getClassLoader() {
        return loader.get();
    }

    public Properties getProperties() {
        return properties;
    }

    public <K, V, C extends Configuration<K, V>> Cache<K, V> createCache(String cacheName, C configuration) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public <K, V> Cache<K, V> getCache(String cacheName, Class<K> keyType, Class<V> valueType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public <K, V> Cache<K, V> getCache(String cacheName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Iterable<String> getCacheNames() {
        return caches.keySet();
    }

    public void destroyCache(String cacheName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void enableManagement(String cacheName, boolean enabled) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void enableStatistics(String cacheName, boolean enabled) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isClosed() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public <T> T unwrap(Class<T> clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
