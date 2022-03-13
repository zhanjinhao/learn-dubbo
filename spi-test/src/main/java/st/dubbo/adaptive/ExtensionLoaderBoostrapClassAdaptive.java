package st.dubbo.adaptive;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import st.PrintService;

/**
 * @Author ISJINHAO
 * @Date 2022/3/7 18:08
 */
public class ExtensionLoaderBoostrapClassAdaptive {

    public static void main(String[] args) {
        // 把 DubboPrintServiceImpl 的 @Adaptive 注解打开
        ExtensionLoader<PrintService> loader = ExtensionLoader.getExtensionLoader(PrintService.class);
        PrintService adaptiveExtension = loader.getAdaptiveExtension();
        // 输出的是 DubboPrintServiceImpl
        System.out.println(adaptiveExtension.getClass());
        URL url = URL.valueOf("test://localhost/test?print.service=world");
        adaptiveExtension.printInfo("zjh", url);

    }

}
