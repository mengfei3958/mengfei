package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties.Jwt;
import org.springframework.context.annotation.Scope;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.example.demo.entity.Student1;
import com.example.demo.entity.TestTable;
import com.example.demo.service.StuServiceImp;
//import com.example.demo.service.StudentPlusMapper;
import com.example.demo.service.StudentService;
//import com.example.demo.service.Impl.StudentPlusServiceImpl;
//import com.example.demo.service.Impl.StudentPlusServiceImpl;
//import com.example.demo.service.Impl.StudentPlusServiceImpl;
//import com.example.demo.service.Impl.StudentServiceImpl;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "pet", description = "mengfei API")
@RestController
@RequestMapping(value = "/api")
//@Scope("prototype")
public class TestSwagger{

	@Autowired
	private StudentService studentService;
//	@Autowired
//	private StuServiceImp stuServiceImp;
////	@Autowired
////    private  KafkaTemplate<String,String> kafkaTemplate;//kafkaTemplate相当于生产者
//	public final static Logger logger = LoggerFactory.getLogger(TestSwagger.class);
//	
////	private IService<TestTable> studentPlusServiceImpl = new StudentPlusServiceImpl();
//	
//	//hibernate获取
//	 @ApiOperation(value = "获取学生信息", notes = "学生信息")
//	 @RequestMapping(value = "/student", method = RequestMethod.POST)
//	 public String saveStudent(){
//		 Gson gson = new Gson();
//		 List<Student1> list = studentService.getAll();
//		 return gson.toJson(list);
//	 }
//	 
//	//hibernate获取,权限控制
//	 @ApiOperation(value = "获取所有学生信息", notes = "获取所有学生信息")
////	 权限配置位于shiroRealm的protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection)方法中
////	 SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
////	 info.addStringPermission("system:user:exportMeng");
//	 @RequiresPermissions("system:user:exportMeng")
//	 @RequestMapping(value = "/student1", method = RequestMethod.POST)
//	 public String saveStudent1(){		 
//		 Gson gson = new Gson();
//		 List<Student1> list = studentService.getAll();
//		 return gson.toJson(list);
//	 }
//	 
//	 //hibernate获取
//	 @ApiOperation(value = "根据姓名查询信息", notes = "根据姓名查询信息")
//	 @RequestMapping(value = "/findAllByName", method = RequestMethod.POST)
//	 public String findAllByName(@RequestParam String name){		 
//		 Gson gson = new Gson();
//		 List<Student1> list = studentService.findAllByName(name);
//		 return gson.toJson(list);
//	 }
//	 
//	 //mybatis获取
//	 @ApiOperation(value = "获取学生信息", notes = "获取学生信息")
//	 @RequestMapping(value = "/findAll", method = RequestMethod.GET)
//	 @ResponseBody
//	 public String findAll(@RequestParam List<String> ids, @RequestParam List<String> deviceIds){		 
//		 Gson gson = new Gson();
//		 List<Student1> list = stuServiceImp.findAll();
//		 return gson.toJson(list);
//	 }
//	 
////	 //mybatis-plus获取
////	 @ApiOperation(value = "mybatis-plus查询学生信息", notes = "mybatis-plus查询学生信息")
////	 @RequestMapping(value = "/findAllStudent", method = RequestMethod.POST)
////	 public String findAllStudent(){		 
////		 Gson gson = new Gson();
////		 List<TestTable> list = studentPlusServiceImpl.selectList(new EntityWrapper<TestTable>());
////		 return gson.toJson(list);
////	 }
//	 
//	 @ApiOperation(value = "登陸", notes = "登陸")
//	 @RequestMapping(value = "/login", method = RequestMethod.POST)
//	    public String login(
//	            @RequestParam(value = "username", required = true) String userName,
//	            @RequestParam(value = "password", required = true) String password
//	    ) {
//	        System.out.println("==========" + userName + password);
//	        Subject subject = SecurityUtils.getSubject();
////	        subject.login();
//	        UsernamePasswordToken token = new UsernamePasswordToken(userName, password, true);
//	        token.setRememberMe(true);
//
//	        try {
//	            subject.login(token);
//	        } catch (AuthenticationException e) {
//	            e.printStackTrace();
////	            rediect.addFlashAttribute("errorText", "您的账号或密码输入错误!");
//	            return "{\"Msg\":\"您的账号或密码输入错误\",\"state\":\"failed\"}";
//	        }
//	        return "{\"Msg\":\"登陆成功\",\"state\":\"success\"}";
//	    }
//	 
////	 @RequestMapping(value = "/{topic}/send",method = RequestMethod.GET)
////	    public void sendMeessage(
////	            @RequestParam(value = "message",defaultValue = "hello world") String message,
////	            @PathVariable final String topic) {
////	        logger.info("start sned message to {}",topic);
////	        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic,message);//发送消息，topic不存在将自动创建新的topic
////	        listenableFuture.addCallback(//添加成功发送消息的回调和失败的回调
////	                result -> logger.info("send message to {} success",topic),
////	                ex -> logger.info("send message to {} failure,error message:{}",topic,ex.getMessage()));
////	    }
////
////	    @RequestMapping(value = "/default/send",method = RequestMethod.GET)
////	    public void sendMeessagedefault() {//发送消息到默认的topic
////	        logger.info("start send message to default topic");
////	        kafkaTemplate.sendDefault("你好，世界");
////	    }
//	 
//	 
//	 @RequestMapping(value = "/testSysnised", method = RequestMethod.POST)
//	 public synchronized String testSysnised(){
//		 System.out.println("当前对象为： "+ this);
//		 System.out.println("进入同步方法的时间!" + new Date(System.currentTimeMillis()));
//		 try {
//			Thread.sleep(5000);
//			System.out.println("正在休眠： "+ this);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 return "success";
//	 }
	 
	@ApiOperation(value = "获取学生信息", notes = "获取学生信息")
	 @RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public String test(){
//		System.out.println(this);
//		System.out.println(studentService);
		return "success";
	}
	
	@ApiOperation(value = "获取学生信息", notes = "获取学生信息")
	 @RequestMapping(value = "/findAll1", method = RequestMethod.GET)
	public String test1(){
		System.out.println(this);
		System.out.println(studentService);
		return "success";
	}
}
