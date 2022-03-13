package st.dubbo.active;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.extension.LoadingStrategy;
import st.PrintService;

import java.util.List;

/**
 * @Author ISJINHAO
 * @Date 2022/3/7 18:08
 */
public class ExtensionLoaderBoostrapRemoveDefault {

    public static void main(String[] args) {

        // LoadingStrategy 是用ServiceLoader加载的
        List<LoadingStrategy> loadingStrategies = ExtensionLoader.getLoadingStrategies();
        System.out.println(loadingStrategies);
        ExtensionLoader<PrintService> extensionLoader = ExtensionLoader.getExtensionLoader(PrintService.class);
        URL url = URL.valueOf("test://localhost/test");
        // -default 会把指定的组里的实现都干掉
        List<PrintService> default_group = extensionLoader.getActivateExtension(url, new String[]{"-default", "jdk"}, "order");
        System.out.println(default_group.size());
        System.out.println(default_group.get(0).getClass());

    }

}
