package app.base.interceptor;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;


/** 
 * 不必在Controller中对异常进行处理，抛出即可，由此异常解析器统一控制。<br> 
 * ajax请求（有@ResponseBody的Controller）发生错误，输出JSON。<br> 
 * 页面请求（无@ResponseBody的Controller）发生错误，输出错误页面。<br> 
 *  
 * */ 
@ControllerAdvice
public class ExceptionInterceptor   {
	private static Logger log = LoggerFactory.getLogger(ExceptionInterceptor.class);
	@ExceptionHandler(value = { Exception.class })
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) {
        // 判断是否ajax请求
        if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
                .getHeader("X-Requested-With") != null && request.getHeader(
                "X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
            // 如果不是ajax，JSP格式返回
            // 为安全起见，只有业务异常我们对前端可见，否则否则统一归为系统异常
            Map<String, Object> map = new HashMap<String, Object>();
//            if (exception instanceof DicException) {
//                map.put("errorMsg", exception.getMessage());
//            } else {
//                map.put("errorMsg", "系统异常！");
//            }
            //这里需要手动将异常打印出来，由于没有配置log，实际生产环境应该打印到log里面
            //对于非ajax请求，我们都统一跳转到error.jsp页面
            log.error(exception.getMessage(), exception);
            map.put("errorMsg", exception.getMessage());
            return new ModelAndView("component/pub/exception", map);
        } else {
            // 如果是ajax请求，JSON格式返回
            try {
            	log.error(exception.getMessage(), exception);
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("status", "failure");
                map.put("errorMsg", exception.getMessage());
                ModelAndView mv=new ModelAndView("jsonView");
                mv.addAllObjects(map);
                return mv;
             /*   PrintWriter writer = response.getWriter();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("status", "failure");
                // 为安全起见，只有业务异常我们对前端可见，否则统一归为系统异常
               
//                if (exception instanceof DicException) {
//                    map.put("errorMsg", exception.getMessage());
//                } else {
//                    map.put("errorMsg", "系统异常！");
//                }
                log.error(exception.getMessage(), exception);
                Gson gson = new Gson();
                writer.write(gson.toJson(map));
                writer.flush();
                writer.close();*/
            } catch (Exception e) {
            	log.error("异常过滤器返回失败",e);
            }
        }
        return null;
    }
      
  
} 
