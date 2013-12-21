package uk.co.c2b2.jsr107.memcached;

import javax.cache.Caching;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println(Caching.getCachingProvider());
    }
}
