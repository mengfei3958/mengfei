package com.example.demo.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaConsumerDemo {

	public static void main(String[] args) {
        //新建配置文件
        Properties properties = new Properties();
        //设置kafka服务器地址
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"47.105.53.232:9092");
        //设置key的反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        //设置value的反序列化
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        //设置groupid
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test-consumer-group");

        //创建消费者对象
        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(properties);
        //订阅主题
        consumer.subscribe(Arrays.asList("test1"));

        while (true) {
            //消费消息
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)

               System.out.println("消息的主题是：" + record.topic()+"，消息的key是：" + record.key()+",消息的value是："+record.value());
        }
    }
}
