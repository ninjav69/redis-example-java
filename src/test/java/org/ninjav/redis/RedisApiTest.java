/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 *
 * @author ninjav
 */
public class RedisApiTest {
    @Test
    public void canSetValue() {
        Jedis jedis = new Jedis("localhost");
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        
        assertThat(value, is(equalTo("bar")));
    }
}
