package sso.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class WebUtils {
    //将数据以Json格式响应到客户端
    public static void writeJsonToClient(HttpServletResponse response,
                                         Map<String,Object> dataMap) throws IOException {
        //1.设置响应数据的编码
        response.setCharacterEncoding("utf-8");
        //2.告诉浏览器设置响应数据的内容类型以及编码
        response.setContentType("application/json;charset=utf-8");
        //3.将数据转化成Json格式字符串
        String jsonStr =
                new ObjectMapper().writeValueAsString(dataMap);
        //4.获取输出流对象,将Json数据写到客户端
        PrintWriter out = response.getWriter();
        out.println(jsonStr);
        out.flush();
    }
}
