package st.dubbo.adaptive;

import org.apache.dubbo.common.extension.ExtensionLoader;

public class AdaptiveCode {

}

// MethodAdaptive
class PrintService$Adaptive1 implements st.PrintService {
    public void printInfo(java.lang.String arg0, org.apache.dubbo.common.URL arg1) {
        if (arg1 == null) throw new IllegalArgumentException("url == null");
        org.apache.dubbo.common.URL url = arg1;
        // 传入的参数是test
        String extName = url.getParameter("test", "hello");
        if (extName == null)
            throw new IllegalStateException("Failed to get extension (st.PrintService) name from url (" + url.toString() + ") use keys([test])");
        st.PrintService extension = (st.PrintService) ExtensionLoader.getExtensionLoader(st.PrintService.class).getExtension(extName);
        extension.printInfo(arg0, arg1);
    }
}

// DefaultMethodParameterNameAdaptive
class PrintService$Adaptive2 implements st.PrintService {
    public void printInfo(java.lang.String arg0, org.apache.dubbo.common.URL arg1) {
        if (arg1 == null) throw new IllegalArgumentException("url == null");
        org.apache.dubbo.common.URL url = arg1;
        // 默认的参数是接口名的点小写形式
        String extName = url.getParameter("print.service", "hello");
        if (extName == null)
            throw new IllegalStateException("Failed to get extension (st.PrintService) name from url (" + url.toString() + ") use keys([print.service])");
        st.PrintService extension = (st.PrintService) ExtensionLoader.getExtensionLoader(st.PrintService.class).getExtension(extName);
        extension.printInfo(arg0, arg1);
    }
}