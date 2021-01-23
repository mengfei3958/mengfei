package com.example.demo.kafka;

import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import scala.util.Random;

@Component
public class MyProducer {
	
//	192.168.75.128测试发送数据
	public void test(){
		Properties props = new Properties();
//        props.put("zk.connect", "192.168.75.128:2181");
        // serializer.class为消息的序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
       // 配置metadata.broker.list, 为了高可用, 最好配两个broker实例
       props.put("metadata.broker.list", "192.168.75.128:9092");
        // 设置Partition类, 对队列进行合理的划分
        // ACK机制, 消息发送需要kafka服务端确认
//     props.put("request.required.acks", "1");
       props.put("num.partitions", "2");
        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);
        for (int i = 0; i < 5; i++)
        {
          String msg = "hello" + new Date();
          producer.send(new KeyedMessage<String, String>("test1",msg));
          System.out.println("i:"+i+" msg:"+msg);
        }
	}
	
//	47.105.53.232测试发送数据
	public void sendMessage(){
//		long events = 5;
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "47.105.53.232:9092");
//		//“所有”设置将导致记录的完整提交阻塞，最慢的，但最持久的设置。
//        props.put("acks", "all");
//		//如果请求失败，生产者也会自动重试，即使设置成０
//        props.put("retries", 0);
//        props.put("batch.size", 16384);
//		//默认立即发送，这里这是延时毫秒数
//        props.put("linger.ms", 1);
//		//生产者缓冲大小，当缓冲区耗尽后，额外的发送调用将被阻塞。时间超过max.block.ms将抛出TimeoutException
//        props.put("buffer.memory", 33554432);
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        KafkaProducer producer = new KafkaProducer<>(props);
//        for (long nEvents = 0; nEvents < events; nEvents++) {
//            long runtime = new Date().getTime();
//            String ip = "47.105.53.232";
//            String msg = runtime + ",www.ztewelink.com," + ip;
//            ProducerRecord<String, String> data = new ProducerRecord<String, String>("test1", msg);
//            producer.send(data);
//            System.out.println(nEvents);
//        }
//        producer.close();
		
		
		//创建properties文件
        Properties properties = new Properties();
        //设置kafka服务器地址
       properties.put("bootstrap.servers", "47.105.53.232:9092");
       //设置key进行序列化
       properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
       //设置value进行序列化
       properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
       properties.put("retries", 0);
       properties.put("batch.size", 16384);
       properties.put("linger.ms", 1);
       properties.put("buffer.memory", 33554432);
       //创建消息生产者
       KafkaProducer<String,String> producer = new KafkaProducer<>(properties);
       //创建消息实体 制定主题、key、value
       ProducerRecord<String,String> record = new ProducerRecord<>("test1","test1","from java client");
       //发送消息
       producer.send(record);
       System.out.println("消息发送成功");
       //关闭生产者
       producer.close();
	}
	
//	没有监听到消息
//	@KafkaListener(topics = "test1")
//    public void receive(ConsumerRecord<?, ?> consumer) {
//        System.out.println("{} - {}:{}" +  consumer.topic() + consumer.key() + consumer.value());
//    }
	
}
