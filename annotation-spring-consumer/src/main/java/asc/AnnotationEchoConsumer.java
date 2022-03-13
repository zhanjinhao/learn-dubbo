package asc;

import es.EchoService;
import org.apache.dubbo.config.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author ISJINHAO
 * @Date 2022/3/10 22:51
 */
@Component
public class AnnotationEchoConsumer {

    @DubboReference
    private EchoService echoService;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AnnotationEchoConsumer.class, ConsumerConfiguration.class);
        context.refresh();

        AnnotationEchoConsumer annotationEchoConsumer = context.getBean(AnnotationEchoConsumer.class);
        System.out.println(annotationEchoConsumer.echoService.echo("hello world"));

        context.close();

    }

    @Configuration
    @EnableDubbo
    static class ConsumerConfiguration {

        @Bean
        public ConsumerConfig consumerConfig() {
            ConsumerConfig consumerConfig = new ConsumerConfig();
            return consumerConfig;
        }

        @Bean
        public ApplicationConfig applicationConfig() {
            ApplicationConfig config = new ApplicationConfig();
            config.setQosEnable(false);
            config.setName("annotation-spring-consumer");
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
        public ReferenceConfigBase<EchoService> annotationEchoServiceReferenceConfig(ConsumerConfig consumerConfig) {
            ReferenceConfigBase<EchoService> referenceConfig = new ReferenceConfig<>();
            referenceConfig.setInterface(EchoService.class);
            referenceConfig.setProtocol("dubbo");
            referenceConfig.setConsumer(consumerConfig);
            return referenceConfig;
        }

    }

}
