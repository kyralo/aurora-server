package online.kyralo.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * \* Created with IntelliJ IDEA.
 * \* @author 王宸
 * \* Date: 2018-11-10
 * \* Time: 下午3:05
 * \* Description:spring web mvc 配置 [开发环境使用]
 * \
 */
@Configuration
@Profile({"dev","test","github"})
public class WebMvcDevConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //主页重定向到api文档
        registry.addRedirectViewController("/api", "/docs.html");
    }


    /**
     * 跨域设置
     * 开发环境配置, 生产环境使用nginx进行跨域处理
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .exposedHeaders("Authorization");
    }

}
