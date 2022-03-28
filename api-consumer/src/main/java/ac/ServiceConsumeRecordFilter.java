package ac;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author ISJINHAO
 * @Date 2022/3/19 15:41
 */
@Activate(group = CommonConstants.CONSUMER, order = 6000)
public class ServiceConsumeRecordFilter implements Filter, Filter.Listener {

    private static final String REQUEST_START_KEY = "ServiceConsumeRecordStart";
    private static final String REQUEST_END_KEY = "ServiceConsumeRecordEnd";

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        invocation.setAttachment(REQUEST_START_KEY, ServiceConsumeRecord.formatter.format(LocalDateTime.now()));
        Result invoke = invoker.invoke(invocation);
        invocation.setAttachment(REQUEST_END_KEY, ServiceConsumeRecord.formatter.format(LocalDateTime.now()));
        return invoke;
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        publish(invoker, invocation, ServiceConsumeRecord.SUCCESS, null);
    }

    @Override
    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {
        publish(invoker, invocation, ServiceConsumeRecord.EXCEPTION, t);
    }

    private void publish(Invoker<?> invoker, Invocation invocation, String status, Throwable t) {
        ServiceConsumeRecord serviceConsumeRecord = new ServiceConsumeRecord();

        URL url = invoker.getUrl();
        serviceConsumeRecord.setStatus(status);
        serviceConsumeRecord.setConsumer(url.getParameter("application"));
        serviceConsumeRecord.setProvider(url.getParameter("remote.application"));
        serviceConsumeRecord.setServiceName(url.getParameter("interface"));
        serviceConsumeRecord.setServiceMethodName(invocation.getMethodName());
        serviceConsumeRecord.setRequestStart(invocation.getAttachment(REQUEST_START_KEY));
        serviceConsumeRecord.setRequestEnd(invocation.getAttachment(REQUEST_END_KEY));


        if (ServiceConsumeRecord.EXCEPTION.equals(status)) {
            Class<?>[] parameterTypes = invocation.getParameterTypes();
            Object[] arguments = invocation.getArguments();
            serviceConsumeRecord.setException(t.getClass().getSimpleName());
            String errorMsg = t.getMessage();
            if (errorMsg != null && errorMsg.length() > 1000) {
                errorMsg = errorMsg.substring(0, 1000);
            }
            serviceConsumeRecord.setExceptionMsg(errorMsg);
            StringBuilder sb = new StringBuilder();
            for (Class<?> parameterType : parameterTypes) {
                sb.append(parameterType.getSimpleName()).append("; ");
                serviceConsumeRecord.setParameterTypes(sb.toString());
            }
            sb.setLength(0);
            for (Object argument : arguments) {
                sb.append(argument.toString()).append("; ");
                serviceConsumeRecord.setParameterValues(sb.toString());
            }
        }

        doPublish(serviceConsumeRecord);
    }

    private void doPublish(ServiceConsumeRecord serviceConsumeRecord) {
        executorService.submit(() -> {
            System.out.println(serviceConsumeRecord);
        });
    }

}
