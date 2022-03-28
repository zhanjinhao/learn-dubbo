package ap;

import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;

public class EchoGenericService implements GenericService {

    public Object $invoke(String methodName, String[] parameterTypes, Object[] args) throws GenericException {
        if ("echo".equals(methodName)
                && parameterTypes.length == 1 && parameterTypes[0].equals("java.lang.String")) {
            return "generic provider " + args[0];
        }
        return "";
    }

}