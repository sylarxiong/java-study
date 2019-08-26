package main.java.tread;

import main.java.handle.WebContext;
import main.java.handle.WebHandle;
import main.java.server.Request;
import main.java.server.Response;
import main.java.servlet.Servlet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Dispatcher implements Runnable {
    private Socket client;
    private Request request;
    private Response response;
    public Dispatcher(Socket client){
        this.client = client;

        try {
            request = new Request(client.getInputStream());
            response = new Response(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            release();
        }

    }

    @Override
    public void run() {

        try {
            //SAX解析
            //1 获取解析工厂
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //2 从解析工厂获取解析器
            SAXParser parser = factory.newSAXParser();

            //3 编写处理器
            //4 加载文档 注册处理器
            WebHandle handler = new WebHandle();
            //5 解析
            parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("main/resource/web.xml"), handler);

            WebContext webContext = new WebContext(handler.getServletEntities(),handler.getMappingEntities());
            String className = webContext.getClz(request.getUrl());
            Class c = Class.forName(className);
            Servlet servlet =  (Servlet) c.getConstructor().newInstance();
            servlet.service(request,response);
            response.pushToBrowser(200);

        }catch (Exception e){
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("main/resource/404.html");
            try {
                response.print(new String(inputStream.readAllBytes()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            response.pushToBrowser(404);
        }
        release();
    }
    //方式资源
    private void release(){
        try {
            client.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
