## 使用 DubboBootstrap 开发服务端

```java
public class ApiEchoProvider {

    public static void main(String[] args) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("api-provider");
        applicationConfig.setQosEnable(false);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://81.71.14.12:2181");
        registryConfig.setTimeout(30000);
        registryConfig.setUseAsConfigCenter(false);
        registryConfig.setUseAsMetadataCenter(false);


        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setRetries(3);
        providerConfig.setTimeout(3000);


        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(12345);
        protocol.setThreads(4);


        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口
        // 服务提供者暴露服务配置
        ServiceConfig<EchoService> echoServiceConfig = new ServiceConfig<>();
        echoServiceConfig.setInterface(EchoService.class);
        echoServiceConfig.setRef(new EchoServiceImpl());
        echoServiceConfig.setVersion("1.0.0");
        echoServiceConfig.setProtocol(protocol);


        // 通过DubboBootstrap简化配置组装，控制启动过程
        DubboBootstrap.getInstance()
                .application(applicationConfig)         // 应用配置
                .registry(registryConfig)               // 注册中心配置
                .provider(providerConfig)
                .protocol(protocol)                     // 全局默认协议配置
                .service(echoServiceConfig)             // 添加ServiceConfig
                .start()                                // 启动Dubbo
                .await();                               // 挂起等待(防止进程退出）
    }
    
}
```

## DubboBootstrap

```java
public DubboBootstrap start() {
    if (started.compareAndSet(false, true)) {
        destroyed.set(false);
        ready.set(false);
        initialize();
        if (logger.isInfoEnabled()) {
            logger.info(NAME + " is starting...");
        }
        // 1. export Dubbo Services
        exportServices();

        // Not only provider register
        if (!isOnlyRegisterProvider() || hasExportedServices()) {
            // 2. export MetadataService
            exportMetadataService();
            //3. Register the local ServiceInstance if required
            registerServiceInstance();
        }

        referServices();
        if (asyncExportingFutures.size() > 0) {
            new Thread(() -> {
                try {
                    this.awaitFinish();
                } catch (Exception e) {
                    logger.warn(NAME + " exportAsync occurred an exception.");
                }
                ready.set(true);
                if (logger.isInfoEnabled()) {
                    logger.info(NAME + " is ready.");
                }
                ExtensionLoader<DubboBootstrapStartStopListener> exts = getExtensionLoader(DubboBootstrapStartStopListener.class);
                exts.getSupportedExtensionInstances().forEach(ext -> ext.onStart(this));
            }).start();
        } else {
            ready.set(true);
            if (logger.isInfoEnabled()) {
                logger.info(NAME + " is ready.");
            }
            ExtensionLoader<DubboBootstrapStartStopListener> exts = getExtensionLoader(DubboBootstrapStartStopListener.class);
            exts.getSupportedExtensionInstances().forEach(ext -> ext.onStart(this));
        }
        if (logger.isInfoEnabled()) {
            logger.info(NAME + " has started.");
        }
    }
    return this;
}
```

### 初始化配置文件

```java
public void initialize() {
    if (!initialized.compareAndSet(false, true)) {
        return;
    }

    ApplicationModel.initFrameworkExts();

    startConfigCenter();

    loadRemoteConfigs();

    checkGlobalConfigs();

    // @since 2.7.8
    startMetadataCenter();

    initMetadataService();

    if (logger.isInfoEnabled()) {
        logger.info(NAME + " has been initialized!");
    }
}
```

#### 初始化配置中心



```java
private void startConfigCenter() {

    useRegistryAsConfigCenterIfNecessary();

    Collection<ConfigCenterConfig> configCenters = configManager.getConfigCenters();

    // check Config Center
    if (CollectionUtils.isEmpty(configCenters)) {
        ConfigCenterConfig configCenterConfig = new ConfigCenterConfig();
        configCenterConfig.refresh();
        if (configCenterConfig.isValid()) {
            configManager.addConfigCenter(configCenterConfig);
            configCenters = configManager.getConfigCenters();
        }
    } else {
        for (ConfigCenterConfig configCenterConfig : configCenters) {
            configCenterConfig.refresh();
            ConfigValidationUtils.validateConfigCenterConfig(configCenterConfig);
        }
    }

    if (CollectionUtils.isNotEmpty(configCenters)) {
        CompositeDynamicConfiguration compositeDynamicConfiguration = new CompositeDynamicConfiguration();
        for (ConfigCenterConfig configCenter : configCenters) {
            compositeDynamicConfiguration.addConfiguration(prepareEnvironment(configCenter));
        }
        environment.setDynamicConfiguration(compositeDynamicConfiguration);
    }
    configManager.refreshAll();
}

```









