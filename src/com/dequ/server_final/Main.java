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
 * Socket������Socket���ӣ���ȡ����Э��
 * 
 * ������ӦЭ��
 * 
 * @author ������
 *
 */
public class Main {

	private static ServerSocket socketServer; // ��ȡ����Э��
	private static boolean isRunning; // �����͹رյ�״̬
	private static int connectNUM = 0; // ������

	public static void main(String[] args) {
		System.out.println("��������");
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
			System.out.println("����������ʧ�ܣ�������");
		}
	}
	// �������Ӵ���
	public void receive() {
		try {
			//���͵ķ�����  tcpճ��ճ������
			while (isRunning) {
				Socket client = socketServer.accept();
				System.out.println("���Ӵ�����" + ++connectNUM);

				// ���̴߳���
				new Thread(new Dispatcher(client)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�ͻ��˴���");
		}

	}

	public void stop() throws Exception {
		isRunning = false;
		this.socketServer.close();
		System.out.println("������ֹͣ");
	}
}
