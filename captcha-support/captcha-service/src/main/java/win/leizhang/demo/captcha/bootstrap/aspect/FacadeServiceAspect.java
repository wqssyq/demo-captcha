package win.leizhang.demo.captcha.bootstrap.aspect;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import win.leizhang.demo.captcha.api.dto.base.AbstractPublicInputDTO;
import win.leizhang.demo.captcha.api.dto.base.MainOutputDTO;
import win.leizhang.demo.captcha.api.exception.CaptchaResultCode;
import win.leizhang.demo.captcha.api.exception.CaptchaServiceException;
import win.leizhang.demo.captcha.api.utils.TidManager;
import win.leizhang.demo.captcha.common.logger.ObjectLoggingWrapper;

/**
 * Facade层拦截器
 * Created by zealous on 2017/12/29.
 */
@Aspect
@Component
public class FacadeServiceAspect {

    private static final Logger logger = LogManager.getLogger(FacadeServiceAspect.class);
    private static final String RETURN_TYPE_VOID = "void";

    @Around("execution(* win.leizhang.demo.captcha.facade..*.*(..))")
    public Object aroundFacadeMethod(ProceedingJoinPoint joinPoint) {
        final long startTimeMs = System.currentTimeMillis();
        String facadeClzShortName = joinPoint.getTarget().getClass().getSimpleName();
        String facadeMethodName = joinPoint.getSignature().getName();
        String apiName = facadeClzShortName.concat(".").concat(facadeMethodName).concat("()");

        Object outputObject = null;
        try {
            Object[] parameters = joinPoint.getArgs();
            this.setMDC(parameters);
            if (logger.isDebugEnabled()) {
                logger.debug("DubboProvider[{}], Input: {}", apiName, ObjectLoggingWrapper.wrap(parameters));
            }
            outputObject = joinPoint.proceed();

        } catch (Throwable e) {
            String exMsg = apiName.concat(" : ");
            if (StringUtils.isBlank(e.getMessage())) {
                exMsg += "[exception message is empty...]";
            } else {
                exMsg += e.getMessage();
            }

            if (e instanceof CaptchaServiceException) {
                // 业务异常不需要出现在错误日志里
                logger.info("[BusinessException] {}, detail==>{} {}", exMsg, ((CaptchaServiceException) e).getCode(), e.getMessage());
            } else {
                // 加warn级别
                // 加error级别
                logger.error("[SystemException] {}, detail==>{}", exMsg, e);
            }

            MethodSignature method = (MethodSignature) joinPoint.getSignature();
            Class returnType = method.getReturnType();
            Class returnTypeP = returnType.getSuperclass();

            if (null != returnType && !RETURN_TYPE_VOID.equals(returnType.getName())) {
                // 处理业务异常
                if (e instanceof CaptchaServiceException) {
                    CaptchaServiceException bzException = (CaptchaServiceException) e;
                    String msg = "";
                    if (MainOutputDTO.class.equals(returnType)) {
                        msg = bzException.getMessage();
                    }
                    outputObject = new MainOutputDTO(bzException.getCode(), msg);
                } else {
                    // 处理其他异常，统一返回<操作失败、系统繁忙>信息
                    CaptchaResultCode errorCode = CaptchaResultCode.SYSTEM_GENERAL_ERROR;
                    if (MainOutputDTO.class.equals(returnType)) {
                        outputObject = new MainOutputDTO(errorCode.code(), errorCode.msg());
                    } else if (String.class.equals(returnType)) {
                        // 同上
                    } else {
                        // 默认的
                    }
                }
            }
        } finally {
            try {
                this.resetMDC(apiName, startTimeMs, outputObject, joinPoint);
            } catch (Exception e) {
                logger.warn("Exception occurred when invoke resetMDC() in facade finally block, msg: {}", e.getMessage(), e);
            }
        }

        return outputObject;
    }

    public static void setMDC(Object[] parameters) {
        String transactionUuid = null;
        if (parameters.length > 0) {
            if (parameters[0] instanceof AbstractPublicInputDTO) {
                AbstractPublicInputDTO baseDTO = (AbstractPublicInputDTO) parameters[0];
                transactionUuid = baseDTO.getTransactionUuid();
            }
        }

        if (StringUtils.isBlank(transactionUuid)) {
            transactionUuid = TidManager.getTransactionUuid();
        }
        // 加uuid的跟踪
    }

    private void resetMDC(String apiName, long startTimeMs, Object outputObject, ProceedingJoinPoint joinPoint) {
        if (logger.isDebugEnabled()) {
            MethodSignature method = (MethodSignature) joinPoint.getSignature();
            Long elapsedTimeMs = System.currentTimeMillis() - startTimeMs;

            logger.debug("DubboProvider[{}], Output: {}, elapsedTime: {}ms", apiName, ObjectLoggingWrapper.wrap(outputObject), elapsedTimeMs);

        }
        // 重置uuid
    }

}
