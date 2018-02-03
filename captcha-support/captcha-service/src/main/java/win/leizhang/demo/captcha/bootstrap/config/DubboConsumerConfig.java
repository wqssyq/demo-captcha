package win.leizhang.demo.captcha.bootstrap.config;

import com.alibaba.dubbo.config.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboConsumerConfig {

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

}
