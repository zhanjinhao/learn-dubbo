package st.dubbo._default;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.extension.LoadingStrategy;
import st.PrintService;

import java.util.List;

/**
 * @Author ISJINHAO
 * @Date 2022/3/7 18:08
 */
public class ExtensionLoaderBoostrapDefaultValue {

    public static void main(String[] args) {

        // LoadingStrategy 是用ServiceLoader加载的
        List<LoadingStrategy> loadingStrategies = ExtensionLoader.getLoadingStrategies();
        System.out.println(loadingStrategies);
        ExtensionLoader<PrintService> extensionLoader = ExtensionLoader.getExtensionLoader(PrintService.class);

        PrintService defaultExtension = extensionLoader.getDefaultExtension();
        System.out.println(defaultExtension.getClass());
        defaultExtension.printInfo("zjh", null);

    }

}
