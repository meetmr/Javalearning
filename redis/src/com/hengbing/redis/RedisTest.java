package com.hengbing.redis;

import com.hengbing.util.JedisPoolUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * jedis
 */
public class RedisTest {
    Jedis jedis = new Jedis("127.0.0.1",6379);
    @Test
    public void setString(){
        // 1、获取连接

        // 2、操作
        jedis.set("username","战士");
        // 3、释放
        jedis.close();
    }

    @Test
    public void setExtring(){
        // 1、获取连接

        // 2、操作x
        jedis.setex("username",20,"战士"); // 20 秒后自动删除
        // 3、释放
        jedis.close();
    }

    @Test
    public void getString(){
        String username = jedis.get("username");
        System.out.println(username);
    }

    // 操作hash
    @Test
    public void setHash(){
        jedis.hset("user","name","李四");
        jedis.hset("user","age","30");
        jedis.hset("user","sex","男");

        Map<String, String> user = jedis.hgetAll("user"); // 获取所有
        System.out.println(user);
        Set<String> strings = user.keySet();
        for (String string : strings) {
            System.out.println(user.get(string));
        }

    }

    @Test
    public void getHash(){
        String name = jedis.hget("user", "name");
        String age = jedis.hget("user", "age");
        String sex = jedis.hget("user", "sex");
        System.out.println(name+age+sex);
    }

    @Test // list 操作
    public void setList(){
        // 存储
        jedis.lpush("mylist","a","c");
        jedis.rpush("mylist","m","a");

        List<String> mylist = jedis.lrange("mylist", 0, -1);
        System.out.println(mylist);
    }

    @Test
    public void jedisPool(){
        JedisPool jedisPool = new JedisPool();
        Jedis resource = jedisPool.getResource();
        resource.set("use","xxxx");
        String use = resource.get("use");
        System.out.println(use);
        resource.close();
    }

    @Test
    public void TestjedisPoolUtil(){
        Jedis resource = JedisPoolUtil.getJedis();
        resource.set("ming","xi");
        String ming = resource.get("ming");
        System.out.println(ming);

    }
}
