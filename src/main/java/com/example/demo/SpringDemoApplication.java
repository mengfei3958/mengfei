package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.kafka.MyConsumer;
import com.example.demo.kafka.MyProducer;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@MapperScan("com.example.demo.dao")
@ComponentScan(basePackages = "com.example")
@ImportResource(locations={"classpath:spring-database.xml"})
@PropertySource(ignoreResourceNotFound = true, value = {"classpath:user.properties"})
public class SpringDemoApplication extends SpringBootServletInitializer{
	
	@Autowired
    private MyProducer kafkaSender;
	
	public final static Logger logger = LoggerFactory.getLogger(SpringDemoApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringDemoApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringDemoApplication.class);
    }
	
//	//每隔1分钟执行一次
//    @Scheduled(fixedRate = 1000 * 10)
//    public void testKafka() throws Exception {
////    	生产消息
////        kafkaSender.test();
////    	生产消息
//        kafkaSender.sendMessage();
////        消费消息
////        MyConsumer consumerThread = new MyConsumer("test1");   
////        consumerThread.start();
//    }
	
}
