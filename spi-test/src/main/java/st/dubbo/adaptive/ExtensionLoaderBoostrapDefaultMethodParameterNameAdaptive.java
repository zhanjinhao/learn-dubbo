package st.dubbo.adaptive;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import st.PrintService;

/**
 * @Author ISJINHAO
 * @Date 2022/3/7 18:08
 */
public class ExtensionLoaderBoostrapDefaultMethodParameterNameAdaptive {

    public static void main(String[] args) {
        // 需要将 PrintService 接口里的方法上不带参数的 @Adaptive 注解打开
        ExtensionLoader<PrintService> loader = ExtensionLoader.getExtensionLoader(PrintService.class);
        PrintService adaptiveExtension = loader.getAdaptiveExtension();
        // 输出的是 PrintService$Adaptive
        System.out.println(adaptiveExtension.getClass());
        URL url = URL.valueOf("dubbo://192.168.0.101:20880?print.service=jdk");
        adaptiveExtension.printInfo("zjh", url);

    }

}
