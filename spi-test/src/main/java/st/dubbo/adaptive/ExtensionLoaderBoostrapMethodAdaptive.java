package st.dubbo.adaptive;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import st.PrintService;

/**
 * @Author ISJINHAO
 * @Date 2022/3/7 18:08
 */
public class ExtensionLoaderBoostrapMethodAdaptive {

    public static void main(String[] args) {

        ExtensionLoader<PrintService> loader = ExtensionLoader.getExtensionLoader(PrintService.class);
        PrintService adaptiveExtension = loader.getAdaptiveExtension();
        // 输出的是 PrintService$Adaptive
        System.out.println(adaptiveExtension.getClass());
        URL url = URL.valueOf("test://localhost/test?test=jdk");
        adaptiveExtension.printInfo("zjh", url);

    }

}
