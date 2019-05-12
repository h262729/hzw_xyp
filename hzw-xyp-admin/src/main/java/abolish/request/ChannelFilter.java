package abolish.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器
 */
// @WebFilter(urlPatterns = "/*",filterName = "ChannelFilter")
public class ChannelFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(ChannelFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //logger.info("================开始过滤======================");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("================进入ChannelFilter过滤器======================");
        // 防止流读取一次后就没有了, 所以需要将流继续写出去
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        //ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
        //filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {
        //logger.info("================结束过滤======================");
    }
}
