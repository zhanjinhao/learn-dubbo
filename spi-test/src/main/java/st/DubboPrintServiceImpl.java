package st;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.extension.Adaptive;

// group1和group2都可以激活此类
@Activate(group = {"group1", "group2"})
//@Adaptive
public class DubboPrintServiceImpl implements PrintService {

    @Override
    public void printInfo(String msg, URL url) {
        System.out.println("dubbo: " + msg + ", " + url);
    }
}
