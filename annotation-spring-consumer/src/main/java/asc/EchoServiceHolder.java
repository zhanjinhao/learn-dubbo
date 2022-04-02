package asc;

import es.EchoService;
import org.apache.dubbo.config.annotation.DubboReference;

/**
 * @Author ISJINHAO
 * @Date 2022/3/28 23:18
 */
public class EchoServiceHolder {

    @DubboReference
    private EchoService echoService;

    public void echo(String msg) {
        System.out.println(echoService.echo(msg));
    }


}
