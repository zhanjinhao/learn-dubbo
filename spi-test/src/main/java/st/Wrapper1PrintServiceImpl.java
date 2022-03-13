package st;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

@Activate(order = 1)
public class Wrapper1PrintServiceImpl implements PrintService {

    private PrintService printService;

    public Wrapper1PrintServiceImpl(PrintService printService) {
        this.printService = printService;
    }

    @Override
    public void printInfo(String msg, URL url) {
        System.out.println("wrapper1 before");
        printService.printInfo(msg, url);
        System.out.println("wrapper1 after");
    }
}
