package ac;

import java.time.format.DateTimeFormatter;

/**
 * @Author ISJINHAO
 * @Date 2022/3/19 17:26
 */
public class ServiceConsumeRecord {

    public static final String SUCCESS = "SUCCESS";
    public static final String EXCEPTION = "EXCEPTION";

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private String requestId;

    private String requestStart;
    private String requestEnd;

    private String consumer;

    private String provider;

    private String serviceName;

    private String serviceMethodName;

    private String parameterTypes;

    private String parameterValues;

    private String status;

    private String exception;

    private String exceptionMsg;

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceMethodName() {
        return serviceMethodName;
    }

    public void setServiceMethodName(String serviceMethodName) {
        this.serviceMethodName = serviceMethodName;
    }

    public String getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(String parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(String parameterValues) {
        this.parameterValues = parameterValues;
    }


    public String getRequestStart() {
        return requestStart;
    }

    public void setRequestStart(String requestStart) {
        this.requestStart = requestStart;
    }

    public String getRequestEnd() {
        return requestEnd;
    }

    public void setRequestEnd(String requestEnd) {
        this.requestEnd = requestEnd;
    }

    @Override
    public String toString() {
        return "ServiceConsumeRecord{" +
                "consumer='" + consumer + '\'' +
                ", provider='" + provider + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceMethodName='" + serviceMethodName + '\'' +
                ", parameterTypes='" + parameterTypes + '\'' +
                ", parameterValues='" + parameterValues + '\'' +
                ", status='" + status + '\'' +
                ", exception='" + exception + '\'' +
                ", exceptionMsg='" + exceptionMsg + '\'' +
                '}';
    }

}

