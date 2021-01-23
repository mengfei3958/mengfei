package com.example.coap;


import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
 
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.elements.tcp.TcpServerConnector;

public class Server {
	 
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
				exchange.respond("第一个endpoint = tuyou");
				super.handlePOST(exchange);
			}
		}.add(new CoapResource("lenovo"){
			@Override
			public void handlePOST(CoapExchange exchange) {  //2
				exchange.respond("第二个endpoint = lenovo");
				super.handlePOST(exchange);
			}
		}));
		Executors.newSingleThreadExecutor().execute(() -> {
			server.start();
		});
	}

}
