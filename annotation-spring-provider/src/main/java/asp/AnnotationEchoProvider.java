package asp;

import es.EchoServiceImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Author ISJINHAO
 * @Date 2022/3/10 22:51
 */
public class AnnotationEchoProvider {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(EchoServiceImpl.class, ProviderConfiguration.class);
        context.refresh();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Configuration
    @EnableDubbo(scanBasePackages = {"es"})
    static class ProviderConfiguration {

        @Bean
        public ProviderConfig providerConfig() {
            ProviderConfig providerConfig = new ProviderConfig();
            return providerConfig;
        }

        @Bean
        public ApplicationConfig applicationConfig() {
            ApplicationConfig config = new ApplicationConfig();
            config.setQosEnable(false);
            config.setName("annotation-spring-provider");
            return config;
        }

        @Bean
        public RegistryConfig registryConfig() {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setProtocol("zookeeper");
            registryConfig.setAddress("81.71.14.12");
            registryConfig.setPort(2181);
            registryConfig.setTimeout(30000);
            return registryConfig;
        }

        @Bean
        public ProtocolConfig protocolConfig() {
            ProviderConfig providerConfig = new ProviderConfig();
            ProtocolConfig protocolConfig = new ProtocolConfig();
            protocolConfig.setName("dubbo");
            protocolConfig.setPort(20770);
            providerConfig.setProtocol(protocolConfig);
            return protocolConfig;
        }

    }

}
