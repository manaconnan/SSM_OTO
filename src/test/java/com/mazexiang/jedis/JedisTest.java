package com.mazexiang.jedis;


import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Set;

public class JedisTest {

    @Test
    public void testRedis(){
        Jedis jedis  = new Jedis("127.0.0.1",6379);
        jedis.set("name1","mazexiang");
        String name1 = jedis.get("name1");
        System.out.println("-------------------------------------------------------");
        System.out.println("============>name1的值是： "+name1  );
        jedis.close();
    }

    /**
     * 连接池的方式
     */
    @Test
    public void testJedisPool(){
         //连接池配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(3);


        JedisPool jedisPool = new JedisPool(config,"127.0.0.1",6379);
        Jedis jedis =null;
        try {
            jedis = jedisPool.getResource();
            jedis.set("addr","北京");
            jedis.sadd("myset","a","b","c","12");
            Set<String> myset = jedis.smembers("myset");
            for (String str: myset){
                System.out.println("============>str的值是： " + str);
            }


            jedis.lpush("mylist","a","b","c","1","w");
            List<String> mylist = jedis.lrange("mylist", 0, -1);
            for(String str:mylist){
                System.out.println("============>的值是： " + str);
            }
            jedis.rename("mylist","myList1");


            Set<String> keys = jedis.keys("list");
            for (String str: keys){
                System.out.println("-------------------------------------------------------");
                System.out.println("============>str key的值是： " + str);
            }


            String addr = jedis.get("addr");
            System.out.println("-------------------------------------------------------");
            System.out.println("============>name1的值是： "+addr  );


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
            if (jedisPool!=null){
                jedisPool.close();
            }
        }


    }

}
