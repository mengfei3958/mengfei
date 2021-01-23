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
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.scandium.dtls.NULLClientKeyExchange;
 
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;


public class Hello {

	public static void main(String[] args) {
        final CoapServer server = new CoapServer();//主机为localhost 端口为默认端口5683
        final MessageDeliverer messageDeliverer = new MessageDeliverer() {
            @Override
            public void deliverRequest(Exchange exchange) {
                System.out.println("get Request  " + exchange.getRequest().getCode());
                if(exchange.getRequest().getCode() == CoAP.Code.POST) {
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
//                                option block1： 主要用于客户端发出请求时，分块传输，比如需要上传一个大的数据包到服务器上。
//                                option block2： 主要用于服务器端响应时，分块传输， 比如设备端发起资源发现式，由于服务器上资源较多，就要启动分块传输。
//                                int num = exchange.getRequest().getOptions().getBlock2().getNum();
//                                int size = exchange.getRequest().getOptions().getBlock2().getSize();
                                int num = 0;
                                int size = 1;
                                Response response = null;
                                RandomAccessFile ran = null;
                                try{
                                    int hasread;
                                    ran=new RandomAccessFile (file,"r");
                                    ran.seek(num*size);
                                    byte[] b=new byte[size];
                                    if(ran.length() -num*size <= 0)
                                    {
                                        System.out.println("bad size");
                                        response = new  Response(ResponseCode.NOT_FOUND);
                                        exchange.sendResponse(response);
                                        return;
                                    }
                                    if (ran.length() > (num+1)*size) {
                                        optionSet.setBlock2(5, true, num);
                                    }
                                    else {
                                        optionSet.setBlock2(5, false, num);
                                    }
                                    hasread = ran.read(b);
                                    byte[] payload = new byte[hasread];
                                    System.arraycopy(b,0,payload,0,hasread);
                                    response = new Response(ResponseCode.CONTENT);
                                    response.setPayload(payload);
 
                                }catch (IOException e) {
                                    e.printStackTrace();
                                }finally {
                                    try {
                                        ran.close();
                                    }catch (IOException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                                response.setOptions(optionSet);
                                exchange.sendResponse(response);
                            }
                        } else {
                        System.out.println("get file" + file.getPath().toString() + " failed");
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
