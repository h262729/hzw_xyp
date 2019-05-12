package abolish.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器统一管理
 */
//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);

    //@Resource
    private LogInterceptor logInterceptor;

    /**
     * 添加自定义拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("添加自定义拦截器");
        // 自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
    }

    /**
     * 设置允许跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.info("设置允许跨域");
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
