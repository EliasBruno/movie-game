package br.com.cardgame.movie.component;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

public class CacheComponent {

    @Autowired
    private RedissonClient redissonClient;

    /*public <T> set(String key, T t) {
         cache = redissonClient.getBucket(key);
        if (cache.get() == null) {
            cache.set(t, 5, TimeUnit.MINUTES)
        }
        return cache.get()
    }

    fun <T> get(key: String): T? {
        return redisson.getBucket<T>(key).get()
    }*/
}
