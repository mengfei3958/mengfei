package com.example.demo.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.example.demo.entity.Student;

//springboot主程序启动  new MyThread().start();
//往阻塞队列中插入队列  MyThread.getInstance().put(对象例如Student);
public class MyThread {
	
	Thread myThread;
	BlockingQueue<Student> queue = new LinkedBlockingQueue<>();
	public static MyThread instance;
//	单例获取MyThread
	public static synchronized MyThread getInstance(){
		return instance;
	}
	
	public MyThread() {
		// TODO Auto-generated constructor stub
		instance = this;
		myThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					try {
						final Student stu = queue.take();
					} catch (InterruptedException e) {
						System.out.println("thread error");
						// TODO: handle exception
					}
				}
			}
		});
	}
	
	public void start(){
		myThread.start();
	}

}
