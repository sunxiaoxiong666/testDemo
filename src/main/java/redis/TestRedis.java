package redis;


import org.junit.Test;
import redis.clients.jedis.Jedis;

public class TestRedis {

    @Test
    public void testJedis(){
        Jedis jedis=new Jedis("123.59.198.86",6379);
        jedis.auth("Fin@2018");
        jedis.set("sss","中国大黄蜂皮革厂带小姨子跑了");
//        jedis.del("sss");
        String sss = jedis.get("sss");
        String s = jedis.get("dic_ent_property&2152");
        System.out.println(sss);
        jedis.close();
    }
}
