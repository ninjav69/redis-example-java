/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.redis;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.pubsub.RedisPubSubListener;
import com.lambdaworks.redis.pubsub.StatefulRedisPubSubConnection;
import com.lambdaworks.redis.pubsub.api.sync.RedisPubSubCommands;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 *
 * @author ninjav
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        
        //tryLetuce();
        
        tryJedis();
    }

    private static void tryLetuce() throws InterruptedException {
        RedisClient client = RedisClient.create("redis://localhost:6379/0");
        StatefulRedisPubSubConnection<String, String> connection = client.connectPubSub();
        connection.addListener(new RedisPubSubListener<String, String>() {
            @Override
            public void message(String k, String v) {
                System.out.println("Got a message: " + k + " => " + v);
            }

            @Override
            public void message(String k, String k1, String v) {
                System.out.println("Got a message: " + k + " => " + k1 + " => " + v);
            }

            @Override
            public void subscribed(String k, long l) {
                System.out.println("Subscribed to: " + k);
            }

            @Override
            public void psubscribed(String k, long l) {
            }

            @Override
            public void unsubscribed(String k, long l) {
                System.out.println("Unsubscribed to: " + k);
            }

            @Override
            public void punsubscribed(String k, long l) {
            }
        });

        RedisPubSubCommands<String, String> sync = connection.sync();
        sync.subscribe("chat");
        
        Thread.sleep(60 * 1000);
    }

    private static void tryJedis() {
        Jedis jedis = new Jedis("localhost");
        RedisListener l = new RedisListener();
        jedis.subscribe(l, "chat");
    }

    public static class RedisListener extends JedisPubSub {

        @Override
        public void onUnsubscribe(String channel, int subscribedChannels) {
            System.out.println("Unsubscribe to channel[" + channel + "]");
        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {
            System.out.println("Subscribe to channel[" + channel + "]");
        }

        @Override
        public void onMessage(String channel, String message) {
            System.out.println("Mesage[" + message + "] from channel[" + channel + "]");
        }
    }
}
