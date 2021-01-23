package com.example.protobuf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.protobuf.ProtoBufEntity.EarTagMsg;
import com.example.protobuf.ProtoBufEntity.EarTagMsgBody;
import com.example.protobuf.ProtoBufEntity.EarTagMsgBody.Builder;

public class SerialAndDesial {

	public static void main(String[] args) {
		Builder build = ProtoBufEntity.EarTagMsgBody.newBuilder();
		build.setDeviceId("1234566");
		build.setModuleiccid("test");
		build.setStepNum(11);
		build.setLng(124l);
		build.setLat(345l);
		build.setReportTime("2020-01-10");
		build.setModuleimei("rtreb");
		build.setModulerssi(2);
		EarTagMsgBody earTagMsgBodyEntity = build.build();
		com.example.protobuf.ProtoBufEntity.EarTagMsg.Builder earTagMsg = 
				ProtoBufEntity.EarTagMsg.newBuilder();
		earTagMsg.addEarTagMsgBody(earTagMsgBodyEntity);	
		earTagMsg.setDescription("desciption");
		earTagMsg.setMsgId(5);
		earTagMsg.setCode(789);
		EarTagMsg earTagMsgEntity = earTagMsg.build();
		FileOutputStream output;
		try {
			// write
			output = new FileOutputStream("proto.ser");
			earTagMsgEntity.writeTo(output);
//			earTagMsgBodyEntity.writeTo(output);
			output.close();
			 //read
			EarTagMsg protoDemo = ProtoBufEntity.EarTagMsg.parseFrom(new FileInputStream("proto.ser"));
//			EarTagMsgBody protoBodyDemo = ProtoBufEntity.EarTagMsgBody.parseFrom(new FileInputStream("proto.ser"));
            System.out.println(protoDemo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
