package win.leizhang.demo.captcha.bootstrap.config;

import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zealous on 2017/10/28.
 */
@Configuration
public class DubboProviderConfig {

    @Value("${spring.dubbo.registry.address}")
    String registryAddress;

    private static final String PATH_ZKCACHE = "/app/demo-captcha/conf/ZKCache/service/";

    @Bean
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();

        // 协议
        providerConfig.setProtocol(getDubbo());

        providerConfig.setRegistry(getProviderRegistry());
        providerConfig.setVersion("1.0.0");
        providerConfig.setTimeout(5000);
        providerConfig.setRetries(0);
        return providerConfig;
    }

    @Bean(value = "dubboPROT")
    ProtocolConfig getDubbo() {
        ProtocolConfig ptc = new ProtocolConfig();
        ptc.setName("dubbo");
        ptc.setThreadpool("fixed");
        ptc.setThreads(500);
        return ptc;
    }

    @Bean
    public RegistryConfig getProviderRegistry() {
        RegistryConfig regConfig = new RegistryConfig();
        regConfig.setProtocol("zookeeper");
        regConfig.setAddress(registryAddress);
        regConfig.setFile(PATH_ZKCACHE + "dubbo-provider.cache");
        return regConfig;
    }

}
