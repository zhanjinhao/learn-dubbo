package st;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

// 想要激活此类，需要组为group1且url上的参数带有key为test的键值对
@Activate(value = {"test"}, group = {"group1"})
public class JdkPrintServiceImpl implements PrintService {

    @Override
    public void printInfo(String msg, URL url) {
        System.out.println("jdk: " + msg + ", " + url);
    }
}
