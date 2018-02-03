package win.leizhang.demo.captcha.utils;

import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.UUID;

/**
 * 拦截所有外部Dubbo接口调用，打印输入参数、返回数据、耗时，用uuid跟踪每个请求
 * Created by zealous on 2017/12/28.
 */
public class DubboConsumerFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(DubboConsumerFilter.class);

    private static final Random random = new Random();
    private static final int RANDOM_BOUND = 100; // 随机打印的概率
    private static final int MAX_OUTPUT_STRING_LENGTH = 1024 * 3; // 字符串长度大于3072就随机打印，不用每次都打印

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = null;
        String txnUuid = UUID.randomUUID().toString().replace("-", "");

        Class<?> facade = invoker.getInterface();
        String interfaceName = facade.getName();
        String methodName = invocation.getMethodName();

        interfaceName = interfaceName.substring(interfaceName.lastIndexOf(".") + 1, interfaceName.length());
        String apiName = interfaceName.concat(".").concat(methodName).concat("()");

        long beginTime = System.currentTimeMillis();
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("[DubboConsumer-begin]|{}|{}|{}", apiName, txnUuid, JSON.toJSONString(invocation.getArguments()));
            }
            result = invoker.invoke(invocation);

        } finally {
            if (LOGGER.isDebugEnabled()) {
                long elapsedTime = System.currentTimeMillis() - beginTime;
                String resultValue = result == null ? "[null]" : JSON.toJSONString(result.getValue());
                // 出参数据过大的，按一定概率打印日志
                if (resultValue.length() > MAX_OUTPUT_STRING_LENGTH) {
                    int randomInt = random.nextInt(RANDOM_BOUND);
                    // 1/RANDOM_BOUND 的命中率
                    if (randomInt != 0) {
                        resultValue = "[DubboConsumerFilter, skip...]";
                    }
                }
                LOGGER.debug("[DubboConsumer-end]|{}|{}|{}ms|{}", apiName, txnUuid, elapsedTime, resultValue);
            }
        }

        return result;
    }

}
