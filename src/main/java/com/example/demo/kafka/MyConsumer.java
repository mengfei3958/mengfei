package com.example.demo.kafka;

import java.util.HashMap; 
import java.util.List;   
import java.util.Map;   
import java.util.Properties;   
     
import kafka.consumer.ConsumerConfig;   
import kafka.consumer.ConsumerIterator;   
import kafka.consumer.KafkaStream;   
import kafka.javaapi.consumer.ConsumerConnector;  
   
public class MyConsumer extends Thread{ 
        //消费者连接
        private final ConsumerConnector consumer;   
        //要消费的话题
        private final String topic;   
     
        public MyConsumer(String topic) {   
            consumer =kafka.consumer.Consumer   
                    .createJavaConsumerConnector(createConsumerConfig());   
            this.topic =topic;   
        }   
     
    //配置相关信息，128接收消息
//    private static ConsumerConfig createConsumerConfig() {   
//        Properties props = new Properties();   
//        //配置要连接的zookeeper地址与端口
//        props.put("zookeeper.connect","192.168.75.128:2181");           
//        //配置zookeeper的组id (The ‘group.id’ string defines the Consumer Group this process is consuming on behalf of.)
//        props.put("group.id", "test-consumer-group");     
//        //配置zookeeper连接超时间隔
//        props.put("zookeeper.session.timeout.ms","10000"); 
//        props.put("zookeeper.sync.time.ms", "200");
//        props.put("auto.commit.interval.ms", "1000");
//        return new ConsumerConfig(props);   
//    }
    
    //配置相关信息，232接收消息
    private static ConsumerConfig createConsumerConfig() {   
    	Properties props = new Properties();   
    	//配置要连接的zookeeper地址与端口     
        props.put("zookeeper.connect","47.105.53.232:2181");          
    	props.put("group.id", "test-consumer-group");     
    	//配置zookeeper连接超时间隔
    	props.put("zookeeper.session.timeout.ms","15000");
//    	kafka远程主机强迫关闭了一个现有的连接
    	props.put("zookeeper.connection.timeout.ms", "10000000");
    	props.put("zookeeper.sync.time.ms", "200");
    	props.put("auto.commit.interval.ms", "1000");
    	props.put("consumer.timeout.ms", "10000");
    	return new ConsumerConfig(props);   
    }   
     
    public void run(){       
        Map<String,Integer> topickMap = new HashMap<String, Integer>(); 
//       topickMap一次可以处理多个topic 
        topickMap.put(topic, 1);   
        Map<String, List<KafkaStream<byte[],byte[]>>> streamMap = consumer.createMessageStreams(topickMap);        
//         获取每次接收到的数据
        KafkaStream<byte[],byte[]>stream = streamMap.get(topic).get(0);   
        ConsumerIterator<byte[],byte[]> it =stream.iterator();      
//        try {
//        	消费者轮询向kafka请求数据
        	while(true){   
                if(it.hasNext()){ 
                    //打印得到的消息   
                    System.out.println("get data:" +new String(it.next().message()));   
                } 
                try {   
                    Thread.sleep(1000);   
                } catch (InterruptedException e) {   
                    e.printStackTrace();   
                }
            }
//		} finally {
////			关闭消费者
//			consumer.shutdown();
//			System.out.println("Closed consumer");
//		}  
    }  
    
    
    public static void main(String[] args) {   
        MyConsumer consumerThread = new MyConsumer("test1");   
        consumerThread.start();   
    }   
}
