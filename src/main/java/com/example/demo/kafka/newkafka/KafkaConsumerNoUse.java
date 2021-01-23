package com.example.demo.kafka.newkafka;
//package com.example.demo.kafka.newkafka;
//
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//import kafka.consumer.ConsumerConfig;
//import kafka.consumer.ConsumerIterator;
//import kafka.consumer.KafkaStream;
//import kafka.javaapi.consumer.ConsumerConnector;
//import kafka.serializer.StringDecoder;
//import kafka.utils.VerifiableProperties;
//
//@Component
//public class KafkaConsumer {
//
//	// /**
//	// * 监听test主题,有消息就读取
//	// * @param message
//	// */
//	// @KafkaListener(topics = {"test1"})
//	// public void consumer(String message){
//	// System.out.println("接收消息 :test topic message : {}" + message);
//	// }
//	private final ConsumerConnector consumer;
//
//	public KafkaConsumer() {
//		Properties props = new Properties();
//		// zookeeper 配置
////		props.put("zookeeper.connect", "47.105.53.232:2181");
//		props.put("zookeeper.connect", "192.168.75.128:2181");
//		// group 代表一个消费组
//		props.put("group.id", "test-consumer-group");
//
//		// zk连接超时
//		props.put("zookeeper.session.timeout.ms", "4000");
//		props.put("zookeeper.sync.time.ms", "200");
//		props.put("auto.commit.interval.ms", "1000");
//		props.put("auto.offset.reset", "smallest");
//		// 序列化类
//		props.put("serializer.class", "kafka.serializer.StringEncoder");
//
//		ConsumerConfig config = new ConsumerConfig(props);
//
//		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
//	}
//
//	public void consume() {
//		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
//		topicCountMap.put("test1", 1);
//
//		StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
//		StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
//
//		Map<String, List<KafkaStream<String, String>>> consumerMap = consumer.createMessageStreams(topicCountMap,
//				keyDecoder, valueDecoder);
//		KafkaStream<String, String> stream = consumerMap.get("test1").get(0);
//		ConsumerIterator<String, String> it = stream.iterator();
//		while (it.hasNext())
//			System.out.println("consumer消息体----" + it.next().message());
//	}
//	
//	public static void main(String[] args) {
//		new KafkaConsumer().consume();
//	}
//}
