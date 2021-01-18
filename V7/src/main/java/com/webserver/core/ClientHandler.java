package com.webserver.core;

import com.webserver.http.EmptyRequestException;
import com.webserver.http.HttpRequest;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 每个客户端连接后都会启动一个线程来完成与该客户端的交互。
 * 交互过程遵循HTTP协议的一问一答要求，分三步进行处理。
 * 1:解析请求
 * 2:处理请求
 * 3:响应客户端
 *
 */
public class ClientHandler implements Runnable{
    private Socket socket;
    public ClientHandler(Socket socket){
        this.socket = socket;
    }
    public void run() {
        try{
            //1 解析请求
            HttpRequest request = new HttpRequest(socket);



            //2 处理请求
            String path = request.getUri();
            File file = new File("./webapps" + path);

            //判断用户请求的资源是否存在并且还要求定位的是文件!
            if(file.exists()&&file.isFile()){
                OutputStream out = socket.getOutputStream();
                //1发送状态行
                String line = "HTTP/1.1 200 OK";
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);//发送一个回车符
                out.write(10);//发送一个换行符
                //2发送响应头
                line = "Content-Type: text/html";
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);//发送一个回车符
                out.write(10);//发送一个换行符

                line = "Content-Length: "+file.length();
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);//发送一个回车符
                out.write(10);//发送一个换行符

                //单独发送CRLF表示响应头发送完毕
                out.write(13);//发送一个回车符
                out.write(10);//发送一个换行符

                //3发送响应正文
                FileInputStream fis = new FileInputStream(file);
                int len;//表示每次实际读取到的字节数
                byte[] data = new byte[1024*10];
                while((len = fis.read(data))!=-1){
                    out.write(data,0,len);
                }
                System.out.println("响应发送完毕!");

            }else{

                File notFoundPage = new File("./webapps/root/404.html");
                OutputStream out = socket.getOutputStream();
                //1发送状态行
                String line = "HTTP/1.1 404 NotFound";
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);//发送一个回车符
                out.write(10);//发送一个换行符
                //2发送响应头
                line = "Content-Type: text/html";
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);//发送一个回车符
                out.write(10);//发送一个换行符

                line = "Content-Length: "+notFoundPage.length();
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);//发送一个回车符
                out.write(10);//发送一个换行符

                //单独发送CRLF表示响应头发送完毕
                out.write(13);//发送一个回车符
                out.write(10);//发送一个换行符

                //3发送响应正文
                FileInputStream fis = new FileInputStream(notFoundPage);
                int len;//表示每次实际读取到的字节数
                byte[] data = new byte[1024*10];
                while((len = fis.read(data))!=-1){
                    out.write(data,0,len);
                }
                System.out.println("响应发送完毕!");
            }


            //3 响应客户端
            /*
                发送一个标准的HTTP响应，将刚才写好的页面:
                ./webapps/myweb/index.html

                响应的格式
                HTTP/1.1 200 OK(CRLF)
                Content-Type: text/html(CRLF)
                Content-Length: 2546(CRLF)(CRLF)
                1011101010101010101......
             */






        //单独捕获空请求异常，不需要做任何处理，目的仅是忽略处理工作
        }catch(EmptyRequestException e){

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //最终交互完毕后与客户端断开连接
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}















