package com.example.coap;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.OptionSet;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.network.EndpointManager.ClientMessageDeliverer;
import org.eclipse.californium.core.network.Exchange;
import org.eclipse.californium.core.server.MessageDeliverer;
import org.eclipse.californium.core.server.ServerMessageDeliverer;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.scandium.dtls.NULLClientKeyExchange;
 
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;


public class Hello2{

	public static void main(String[] args) {
//		Endpoint负责与网络进行通信，MessageDeliverer负责分发请求，Resource负责处理请求。接着让我们看看启动方法start()又做了哪些事：
        final CoapServer server = new CoapServer();//主机为localhost 端口为默认端口5683
        ServerMessageDeliverer serverMessageDeliverer = new ServerMessageDeliverer(null);
        final MessageDeliverer messageDeliverer = new MessageDeliverer() {
            @Override
            public void deliverRequest(Exchange exchange) {
                System.out.println("get Request  " + exchange.getRequest().getCode());
                if(exchange.getRequest().getCode() == CoAP.Code.GET) {
                	System.out.println("exchange.getRequest().getCode() == CoAP.Code.GET");
                    if(!exchange.getRequest().getOptions().hasBlock2())
                    {
                        Response response = new Response(ResponseCode.NOT_FOUND);
                        exchange.sendResponse(response);
//                        return;
                    }
                    String uriQueryString = exchange.getRequest().getOptions().getUriQueryString();
                    System.out.println("参数为： " + uriQueryString);
//                    String Path = "D://Download/" + exchange.getRequest().getOptions().getUriPathString();
                    String path = "D://Download/Redis-x64-3.0.504/redis.windows.conf" ;
                    File file = new File(path);
                    System.out.println("文件大小为： " + file.length());
                    if (file.exists()) {
                        if(file.canRead()) {
                                OptionSet optionSet = new OptionSet();
                                optionSet.setUriPath(exchange.getRequest().getOptions().getUriPathString());
                                Response response = new Response(ResponseCode.NOT_FOUND);
                                exchange.sendResponse(response);
                                return;
                            }
                        } else {
                        Response response = new Response(ResponseCode.NOT_FOUND);
                        exchange.sendResponse(response);
                        System.out.println("hello响应时间为 " + new Date());
                        return;
                    }
                }
            }
            @Override
            public void deliverResponse(Exchange exchange, Response response) {
                exchange.sendResponse(response);
                System.out.println("send Request");
                System.out.println("hello deliverResponse响应时间为 " + new Date());
            }
        };
        server.setMessageDeliverer(messageDeliverer);
        server.start();
    }


}
