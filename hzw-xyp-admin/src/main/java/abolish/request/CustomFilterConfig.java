package abolish.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义过滤器配置
 */
// @Configuration
public class CustomFilterConfig {
    private Logger logger = LoggerFactory.getLogger(CustomFilterConfig.class);

    @Bean
    public FilterRegistrationBean filterRegistration() {
        logger.info("自定义过滤器配置启动");
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ChannelFilter());
        List<String> urlList = new ArrayList<String>();
        urlList.add("/*");
        registration.setUrlPatterns(urlList);
        registration.setName("ChannelFilter");
        registration.setOrder(1);
        return registration;
    }
}
