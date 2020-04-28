package online.kyralo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2018-11-09
 * \* Time: 下午9:14
 * \* Description:swagger配置 自动生成api文档 //FIXME 完善文档说明哦
 * \
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private final Contact DEFAULT_CONTACT = new Contact(
            "kyralo",
            "https://www.kyralo.online",
            "kyralo721@gmail.com");

    private final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "Aurora API文档",
            "Aurora api文档",
            "1.0",
            "#",
            DEFAULT_CONTACT,
            "",
            "#",
            Collections.emptyList());

    private final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<>(Collections.singletonList("application/json"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}

