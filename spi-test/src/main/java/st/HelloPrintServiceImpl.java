package st;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

// order 组可以激活此类，同时激活之后的类中，此类排名第一
@Activate(order = 1, group = {"order"})
public class HelloPrintServiceImpl implements PrintService {

    @Override
    public void printInfo(String msg, URL url) {
        System.out.println("hello: " + msg + ", " + url);
    }
}
