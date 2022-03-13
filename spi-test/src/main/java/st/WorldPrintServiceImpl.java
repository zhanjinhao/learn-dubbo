package st;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

@Activate(order = 2, group = {"order"})
public class WorldPrintServiceImpl implements PrintService {

    @Override
    public void printInfo(String msg, URL url) {
        System.out.println("world: " + msg + ", " + url);
    }
}
