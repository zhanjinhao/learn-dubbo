package xsradp.impl;

import es.EchoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import xsradp.DemoController;
import xsradp.HelloResult;

import java.time.LocalDateTime;

/**
 * @Author ISJINHAO
 * @Date 2022/3/26 17:32
 */
@DubboService(protocol = "rest")
public class DemoControllerImpl implements DemoController {

    @Autowired
    private EchoService echoService;

    @Override
    public HelloResult test(String msg) {
        HelloResult helloResult = new HelloResult();
        helloResult.setDt(LocalDateTime.now());
        helloResult.setResult(echoService.echo(msg));
        return helloResult;
    }

    @Override
    public HelloResult test2(Long msg) {
        HelloResult helloResult = new HelloResult();
        helloResult.setDt(LocalDateTime.now());
        helloResult.setResult(echoService.echo(String.valueOf(msg)));
        return helloResult;
    }
}
