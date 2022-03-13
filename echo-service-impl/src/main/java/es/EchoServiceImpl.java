package es;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Author ISJINHAO
 * @Date 2022/3/9 22:18
 */
@DubboService(protocol = "dubbo")
public class EchoServiceImpl implements EchoService {

    @Override
    public String echo(String msg) {
        return "echo, " + msg;
    }

}
