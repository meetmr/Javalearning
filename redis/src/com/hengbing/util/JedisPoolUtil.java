package com.hengbing.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 */
public class JedisPoolUtil {
    private static final JedisPool jedisPool;

    static {
        InputStream is = JedisPoolUtil.class.getClassLoader().getResourceAsStream("jedis.properties");
        Properties po = new Properties();
        try {
            po.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JedisPoolConfig cf = new JedisPoolConfig();
        cf.setMaxTotal(Integer.parseInt(po.getProperty("maxIdle")));
        cf.setMaxIdle(Integer.parseInt(po.getProperty("maxTotal")));
        jedisPool = new JedisPool(cf,po.getProperty("host"), Integer.parseInt(po.getProperty("port")));
    }

    public static JedisPool getjedisPool(){
        return jedisPool;
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

}
