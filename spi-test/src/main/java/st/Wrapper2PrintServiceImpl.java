package st;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

@Activate(order = 2)
public class Wrapper2PrintServiceImpl implements PrintService {

    private PrintService printService;

    public Wrapper2PrintServiceImpl(PrintService printService) {
        this.printService = printService;
    }

    @Override
    public void printInfo(String msg, URL url) {
        System.out.println("wrapper2 before");
        printService.printInfo(msg, url);
        System.out.println("wrapper2 after");
    }
}
