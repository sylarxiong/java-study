package main.java;

import main.java.tread.Dispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebStart {
    private ServerSocket serverSocket;
    private Boolean isRunning = false;

    public static void main(String[] args) throws Exception {
        WebStart main = new WebStart();
        main.start();
        main.receive();
    }

    public  void start(){
        try {
            serverSocket = new ServerSocket(8889);
            isRunning = true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("启动失败");
        }
    }
    public void receive() throws Exception{
        while (isRunning) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("一个客户端建立了连接");
                //多线程处理
                new Thread(new Dispatcher(client)).start();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("连接失败");
            }
        }
    }
    public void stop(){
        isRunning = false;
        try {
            this.serverSocket.close();
            System.out.println("服务器停止");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
