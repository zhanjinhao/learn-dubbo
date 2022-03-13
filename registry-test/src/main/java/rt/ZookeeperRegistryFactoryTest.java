package rt;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.registry.NotifyListener;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.zookeeper.ZookeeperRegistryFactory;

import java.io.IOException;
import java.util.List;

/**
 * @Author ISJINHAO
 * @Date 2022/3/8 22:16
 */
public class ZookeeperRegistryFactoryTest {

    public static void main(String[] args) {
        URL registryUrl = URL.valueOf("zookeeper://81.71.14.12:2181/ConfigCenterConfig?check=true&config-file=dubbo.properties&group=dubbo&highest-priority=false&timeout=30000");
        ZookeeperRegistryFactory zookeeperRegistryFactory = new ZookeeperRegistryFactory();
        Registry registry = zookeeperRegistryFactory.createRegistry(registryUrl);

        // category=providers,configurators,routers 表示订阅
        // dubbo/interfaceName/providers
        // dubbo/interfaceName/configurators
        // dubbo/interfaceName/routers
        // 三个节点的变更
        URL consumerUrl = URL.valueOf("consumer://192.168.43.11/es.EchoService?application=api-consumer&category=providers,configurators,routers&dubbo=2.0.2&interface=es.EchoService&methods=echo&pid=18180&qos.enable=false&release=2.7.15&side=consumer&sticky=false&timestamp=1647011329687");
        registry.register(consumerUrl);

        registry.subscribe(consumerUrl, new NotifyListener() {
            @Override
            public void notify(List<URL> urls) {
                System.out.println("-----------------------------------------------------------");
                urls.forEach(item -> {
                    System.out.println("listen : " + item);
                });
            }
        });

        for (int i = 0; i < 10000; i++) {
            // 写入 dubbo/interfaceName/providers 节点
            registry.register(URL.valueOf("dubbo://192.168.43.11:20880/es.EchoService?anyhost=true&application=api-provider&default=true&deprecated=false&dubbo=2.0.2&dynamic=true&generic=false&interface=es.EchoService&metadata-type=remote&methods=echo&pid=19728&release=2.7.15&service.name=ServiceBean:/es.EchoService&side=provider&timestamp=" + i));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
