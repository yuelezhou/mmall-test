package com.mmall.test.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TokenCache {
    private static final Logger logger = LoggerFactory.getLogger(TokenCache.class);
    public static final String TOKEN_PREFIX = "token_prefix";

    private static LoadingCache<String,String> loadingCache =
            CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(1000).expireAfterAccess(12, TimeUnit.HOURS)
                    .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return null;
                }
            });

    public static void setValue(String key,String value){
        loadingCache.put(key,value);
    }

    public static String getValue(String key){
        String value = null;

        try {
            value = loadingCache.get(key);
            if("null".equals(value))
                return null;
            return value;
        } catch (ExecutionException e) {
            logger.error("loadingCache get error",e);
        }
        return null;
    }
}
