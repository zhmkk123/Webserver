package com.webserver.core;

import com.webserver.http.EmptyRequestException;
import com.webserver.http.HttpRequest;

import java.io.IOException;
import java.io.InputStream;
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




            //3 响应客户端



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















