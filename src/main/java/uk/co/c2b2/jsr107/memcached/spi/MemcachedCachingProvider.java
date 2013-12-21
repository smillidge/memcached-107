/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.c2b2.jsr107.memcached.spi;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.cache.CacheManager;
import javax.cache.configuration.OptionalFeature;
import javax.cache.spi.CachingProvider;

/**
 *
 * @author steve
 */
public class MemcachedCachingProvider implements CachingProvider {

    public CacheManager getCacheManager(URI uri, ClassLoader classLoader, Properties properties) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ClassLoader getDefaultClassLoader() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        result.put("addresses", "127.0.0.1:11211");
        result.put("binary", "true");
        return result;
    }

    public CacheManager getCacheManager(URI uri, ClassLoader classLoader) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public CacheManager getCacheManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void close(ClassLoader classLoader) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void close(URI uri, ClassLoader classLoader) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isSupported(OptionalFeature optionalFeature) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
