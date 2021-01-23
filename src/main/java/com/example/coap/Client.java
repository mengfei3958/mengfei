package com.example.coap;

import java.time.Clock;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
 
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.network.EndpointManager.ClientMessageDeliverer;


public class Client {

public static void main(String[] args) throws InterruptedException {
		
		int count = 1;
		CountDownLatch countDown = new CountDownLatch(count);
		long start = Clock.systemUTC().millis();
		CoapClient client = new CoapClient("coap://127.0.0.1:5683/tuyou?appdata=mengfei");
		for(int i = 0; i < count; i++){
		
//			post请求
			client.post(new CoapHandler() {
				
				@Override
				public void onLoad(CoapResponse response) {
					System.out.println(Utils.prettyPrint(response));
					countDown.countDown();
				}
				
				@Override
				public void onError() {
					
				}
			}, "payload message", MediaTypeRegistry.TEXT_XML);
			
			
//			get请求
//			client.get(new CoapHandler() {
//				
//				@Override
//				public void onLoad(CoapResponse response) {
//					// TODO Auto-generated method stub
//					System.out.println("client响应时间为 " + new Date());
//					System.out.println(Utils.prettyPrint(response));
//					countDown.countDown();
//				}
//				
//				@Override
//				public void onError() {
//					
//				}
//			});
		}
		countDown.await();
		long end = Clock.systemUTC().millis();
		System.out.println("耗时为 " + (end - start));
	}

}
