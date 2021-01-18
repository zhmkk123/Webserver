package com.webserver.core;

import com.webserver.servlet.HttpServlet;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 该类保存所有服务端共用信息
 */

public class ServerContext {
    private static Map<String, HttpServlet> servletMapping=new HashMap<>();
    static {
        initServletMapping();
    }
    private static void initServletMapping(){
        SAXReader reader=new SAXReader();
        try {
            Document doc=reader.read("./config/servlet.xml");
            Element root=doc.getRootElement();

            List<Element>list=  root.elements("servlet");
            for(Element e:list){
                String key=e.attributeValue("path");
                String value=e.attributeValue("className");
                Class cls=Class.forName(value);
                HttpServlet servlet=(HttpServlet)cls.newInstance();
                servletMapping.put(key,servlet);
            }
            System.out.println(servletMapping);



        } catch (Exception e) {
            e.printStackTrace();
        }

//        Map<String, HttpServlet>servletMapping =new HashMap<>();
//        servletMapping.put("/myweb/regUser",new RegServlet());
//        servletMapping.put("/myweb/loginUser",new LoginServlet());
//        servletMapping.put("/myweb/showAllUser",new ShowAllUserServlet());
        /*
        通过解析config/servlets.xml文件初始化servletMapping
        实现：
        讲servlets.xml文件中根比标签下的所有<servlet>标签获取到，并将其中的属性：
        path的值作为key。class的值利用反射实例化对应的Servlet作为value并存取到servletMapping中完成
        初始化。
        注：反射实例化后返回值是Object类型，需要造型为HttpServlet才能存入Map中。
         */
    }
    public static HttpServlet getServlet(String path){
        return servletMapping.get(path);

    }
}
