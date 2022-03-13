package st.jdk;

import st.PrintService;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @Author ISJINHAO
 * @Date 2022/3/7 17:58
 */
public class ServiceLoaderBootstrap {

    public static void main(String[] args) {

        // 启动的时候需要将resources 目录下的 services_目录 改为 services

        // 在加载类的时候，会使用当前线程的类加载器，所以能破坏双亲委派机制
        ServiceLoader<PrintService> printServices = ServiceLoader.load(PrintService.class);
        // null 表示boostrap classloader
        System.out.println(ServiceLoader.class.getClassLoader());
        System.out.println(printServices.getClass().getClassLoader());
        Iterator<PrintService> iterator = printServices.iterator();
        while (iterator.hasNext()) {
            PrintService printService = iterator.next();
            System.out.println(printService.getClass().getClassLoader());
            printService.printInfo("zjh", null);
        }

    }

}
