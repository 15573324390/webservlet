package com.dequ.server_final;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Socket：建立Socket连接，获取请求协议
 * 
 * 返回响应协议
 * 
 * @author 赖金荣
 *
 */
public class Main {

	private static ServerSocket socketServer; // 获取请求协议
	private static boolean isRunning; // 启动和关闭的状态
	private static int connectNUM = 0; // 连接数

	public static void main(String[] args) {
		System.out.println("启动服务！");
		Main server = new Main();
		isRunning = true;
		server.start();
	}
	public void start() {
		try {
			socketServer = new ServerSocket(8888);
			receive();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("服务器启动失败！！！！");
		}
	}
	// 接受连接处理
	public void receive() {
		try {
			//典型的非阻塞  tcp粘包粘包问题
			while (isRunning) {
				Socket client = socketServer.accept();
				System.out.println("连接次数：" + ++connectNUM);

				// 多线程处理
				new Thread(new Dispatcher(client)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("客户端错误！");
		}

	}

	public void stop() throws Exception {
		isRunning = false;
		this.socketServer.close();
		System.out.println("服务器停止");
	}
}
