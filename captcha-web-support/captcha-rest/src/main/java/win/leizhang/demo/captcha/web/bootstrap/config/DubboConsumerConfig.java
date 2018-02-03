package win.leizhang.demo.captcha.web.bootstrap.config;

import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboConsumerConfig {

    @Value("${spring.dubbo.registry.address}")
    String registryAddress;

    private static final String PATH_ZKCACHE = "/app/demo-captcha/conf/ZKCache/rest/";

    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setFilter("DubboConsumerFilter");
        consumerConfig.setCheck(false);
        consumerConfig.setRetries(0);
        consumerConfig.setTimeout(10000);
        consumerConfig.setVersion("1.0.0");
        return consumerConfig;
    }

    @Bean
    public RegistryConfig defaultRegistry() {
        RegistryConfig regConfig = new RegistryConfig();
        regConfig.setAddress(registryAddress);
        regConfig.setFile(PATH_ZKCACHE + "dubbo-registry.cache.cache");
        return regConfig;
    }

}
