package win.leizhang.demo.captcha.web.bootstrap.config;

import com.spring4all.swagger.EnableSwagger2Doc;
import com.spring4all.swagger.SwaggerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zealous on 2018/1/29.
 */
@EnableSwagger2Doc
@Configuration
public class Swagger2Config {

    private static List<String> listBasePath = new ArrayList();
    private static List<String> listExcludePath = new ArrayList();
    private List<SwaggerProperties.GlobalOperationParameter> listOperationParameter = new ArrayList<>();

    static {
        listBasePath.add("/**");
        listExcludePath.add("/error");
        listExcludePath.add("/ops/**");
    }

    @Bean
    public SwaggerProperties defaultProperties() {
        SwaggerProperties properties = new SwaggerProperties();
        properties.setEnabled(true);
        properties.setTitle("spring-boot-starter-swagger");
        properties.setDescription("Starter for swagger 2.x");

        properties.setVersion("1.4.0");
        properties.setLicense("Apache License, Version 2.0");
        properties.setLicenseUrl("https://www.apache.org/licenses/LICENSE-2.0.html");
        properties.setTermsOfServiceUrl("https://github.com/dyc87112/spring-boot-starter-swagger");

        properties.setBasePackage("win.leizhang.demo.captcha.web");
        properties.setBasePath(listBasePath);
        properties.setExcludePath(listExcludePath);

        SwaggerProperties.GlobalOperationParameter operationParameter0 = new SwaggerProperties.GlobalOperationParameter();
        operationParameter0.setName("name one");
        operationParameter0.setDescription("some description one");
        operationParameter0.setModelRef("string");
        operationParameter0.setParameterType("head");
        operationParameter0.setRequired("true");
        properties.setGlobalOperationParameters(listOperationParameter);

        return properties;
    }

}
