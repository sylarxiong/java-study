package main.java.server;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class Response {
    private BufferedWriter bw;
    private StringBuilder stringBuilder;
    private int len;
    private StringBuilder headInfo;
    private String blank = " ";
    private String CRLF = "\r\n";
    public Response(){
        stringBuilder = new StringBuilder();
        headInfo = new StringBuilder();
        len = 0;
    }
    public Response(Socket client){
        this();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public Response(OutputStream outputStream){
        this();
        bw = new BufferedWriter(new OutputStreamWriter(outputStream));
    }
    //动态添加内容
    public Response print(String info){
        stringBuilder.append(info);
        len+=info.getBytes().length;
        return this;
    }
    private void createHeadInfo(int code){

        //返回
        //1 响应行HTTP/1.1 200 ok
        headInfo.append("HTTP/1.1").append(blank);
        headInfo.append(code).append(blank);
        switch (code){
            case 200:
                headInfo.append("OK").append(CRLF);
                break;
            case 404:
                headInfo.append("NOT FOUND").append(CRLF);
                break;
            case 505:
                headInfo.append("SERVER ERROR").append(CRLF);
                break;
        }
        //2 响应头
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Server:").append("shsxt Server/0.0.1;charset=UTF-8").append(CRLF);
        headInfo.append("Content-type:text/html").append(CRLF);
        headInfo.append("Content-length:").append(len).append(CRLF);
        headInfo.append(CRLF);
    }
    //推送信息
    public void pushToBrowser(int code){
        if(headInfo == null){
            code = 505;
        }
        createHeadInfo(code);
        try {
            bw.append(headInfo);
            bw.append(stringBuilder);
            bw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
