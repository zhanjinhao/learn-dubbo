package xsc;

import es.EchoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author ISJINHAO
 * @Date 2022/3/9 22:15
 */
public class XmlEchoConsumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:/xsc/spring-dubbo-consumer-context.xml");

        context.start();

        EchoService xmlEchoService = context.getBean(EchoService.class);

        while (true) {
            System.out.println(xmlEchoService.echo("hello world"));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
