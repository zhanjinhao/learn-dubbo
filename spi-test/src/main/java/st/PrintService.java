package st;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

// @SPI注解仅在Dubbo的SPI实现下有意义
@SPI("hello")
public interface PrintService {

    //    @Adaptive({"test"})
    @Adaptive
    void printInfo(String msg, URL url);

}