package com.hzw.xyp.base;

import com.hzw.xyp.base.tools.MapTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;

/**
 * 日志拦截器
 */
@Component
public class LogInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    private String mapToStringByRequest(Map<String, String[]> map) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String[]> e : map.entrySet()) {
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if (value != null && value.length == 1) {
                sb.append(value[0]).append("\t");
            } else {
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long startTime = System.currentTimeMillis();
        request.setAttribute("startTime",startTime);
        try {
            if(handler instanceof HandlerMethod){
                StringBuilder sb = new StringBuilder();
                sb.append("----------------------开始时间：")
                        .append(new SimpleDateFormat("hh:mm:ss.SSS").format(startTime)+"")
                        .append("------------\n");
                sb.append("Controller：").append(((HandlerMethod) handler).getBean().getClass().getName()).append("\n");
                sb.append("Method：").append(((HandlerMethod) handler).getMethod().getName()).append("\n");
                sb.append("RequestMethod：").append(request.getMethod()).append("\n");
                //通过输入流获取POST请求中的参数
                sb.append("Params：").append(new BodyReaderHttpServletRequestWrapper(request).getBodyString()).append("\n");
                sb.append("Params：").append(mapToStringByRequest(request.getParameterMap())).append("\n");
                sb.append("URL：").append(request.getRequestURL()).append("\n");
                sb.append("SessionID：").append(request.getSession().getId()).append("\n");
                sb.append("ApplyID：").append(request.getSession().getAttribute("applyId")).append("\n");
                sb.append("OpenID：").append(request.getSession().getAttribute("openid")).append("\n");
                //logger.info(sb.toString());

                logger.info("请求数据：" + MapTools.getRequestJson(request.getParameterMap()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Long startTime = (Long)request.getAttribute("startTime");
        Long endTime = System.currentTimeMillis();
        Long costTime = endTime - startTime;
        if(handler instanceof HandlerMethod){
            StringBuilder sb = new StringBuilder();
            sb.append("CostTime：").append(costTime).append("ms");
            logger.info(sb.toString());
        }
    }
}
