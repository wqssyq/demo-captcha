package win.leizhang.demo.captcha.common.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;

/**
 * @author <a href="mailto:wengyongyi@crc.com.hk">wengyongyi</a>
 * @version 1.0.0
 * @description Wrap the Object instance to be written to log file or log
 * system.
 * @since 2016年11月24日
 */
public class ObjectLoggingWrapper {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String DATE_OUTPUT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private Object wrappedObject;

    private ObjectLoggingWrapper(Object object) {
        this.wrappedObject = object;
    }

    public static ObjectLoggingWrapper wrap(Object object) {
        if (null != object && object instanceof ObjectLoggingWrapper) {
            object = ((ObjectLoggingWrapper) object).getWrappedObject();
        }
        return new ObjectLoggingWrapper(object);
    }

    public Object getWrappedObject() {
        return wrappedObject;
    }

    @Override
    public String toString() {
        if (null == wrappedObject) {
            return "";
        }
        if (BeanUtils.isSimpleValueType(Object.class)) {
            return wrappedObject.toString();
        }

        String str = null;
        try {
            ObjectWriter ow = jsonMapper.writer(new SimpleDateFormat(DATE_OUTPUT_FORMAT));
            str = ow.writeValueAsString(wrappedObject);
        } catch (Exception e) {
            log.warn("Cannot serialize the object to json, object: {}, error: {}", Object.class.getName(),
                    e.getMessage(), e);
        }

        if (null == str) {
            str = "";
        }
        return str;
    }

}
