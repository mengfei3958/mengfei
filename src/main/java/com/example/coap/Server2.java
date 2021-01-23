package com.example.coap;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
 
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.OptionSet;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.Exchange;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.server.MessageDeliverer;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.elements.tcp.TcpServerConnector;

public class Server2 {
	 
	public static void main(String[] args) {
		
		final CoapServer server = new CoapServer();
		
		//默认就是ucp实现传输层
		server.addEndpoint(new CoapEndpoint(new InetSocketAddress("127.0.0.1", 5683)));
		
		//加入tcp实现传输层
		server.addEndpoint(new CoapEndpoint(
				new TcpServerConnector(new InetSocketAddress("127.0.0.1", 5683), 4, 20000),
				NetworkConfig.getStandard()));
		
		server.add(new CoapResource("tuyou"){
			@Override
			public void handleGET(CoapExchange exchange) {
				handlePOST(exchange);
			};
			@Override
			public void handlePOST(CoapExchange exchange) { //1
				System.out.println(exchange.getRequestOptions().getUriQueryString());
				System.out.println(exchange.getRequestText().length());
                String path = "D://Download/Redis-x64-3.0.504/redis.windows.conf" ;
                File file = new File(path);
                System.out.println("文件大小为： " + file.length());
//				exchange.respond("第一个endpoint = tuyou");
				Response response = new Response(ResponseCode.CONTENT);
				response.setPayload("e7 ac ac e4 b8 80 e4 b8 aa 65 6e 64 70 6f 69 6e 74 20 3d 20 74 75 79 6f 75");
				response.getOptions().setContentFormat(MediaTypeRegistry.TEXT_XML);
				exchange.respond(response);
				super.handleGET(exchange);
			}
		}.add(new CoapResource("lenovo"){
			@Override
			public void handlePOST(CoapExchange exchange) {  //2
//				exchange.respond("第二个endpoint = lenovo");
				exchange.respond("第二个endpoint = lenovo");
//				super.handlePOST(exchange);
			}
		}));
		
		Executors.newSingleThreadExecutor().execute(() -> {
			server.start();
		});
	}

}
