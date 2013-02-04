package org.pjr.rulesengine.client;

import java.net.URL;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class PjrCachemanager {
	
	
	private static PjrCachemanager pjrCacheManager;
	
	private CacheManager cacheManager;
	private PjrCachemanager(){
		
	}
	
	public static PjrCachemanager getInstance(){
		if(null == pjrCacheManager){
				pjrCacheManager = new PjrCachemanager();
				URL url = PjrCachemanager.class.getResource("/pjr-ehcache.xml");
				pjrCacheManager.cacheManager = CacheManager.newInstance(url);
		}
		return pjrCacheManager;
	}
	
	
	public  void put(final String key ,final Object o ,final String cacheName){
		getCache(cacheName).put(new Element(key,o)) ;
	}
	
    public Object get(final String key,final String cacheName) 
    {
        Element element = getCache(cacheName).get(key);
        if (element != null) {
            return  element.getValue();
        }
        return null;
    }	
	

    public Ehcache getCache(final String cacheName) 
    {
        return cacheManager.getEhcache(cacheName);
    }
	

    public void shutdown(){
    	cacheManager.shutdown();
    	cacheManager = null;
    }
}
