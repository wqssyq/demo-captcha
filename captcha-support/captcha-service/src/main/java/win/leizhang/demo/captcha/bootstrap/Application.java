package win.leizhang.demo.captcha.bootstrap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@ServletComponentScan("win.leizhang.demo.captcha")
@ImportResource({"classpath:spring/applicationContext.xml"})
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String VM_OPTIONS_DUBBO = "dubbo.protocol.dubbo.port";
    private static final String DEFAULT_DUBBO_PORT = "20880";

    private static final String APPLICATION_NAME = "captcha-service";

    public static void main(String[] args) {
        // ############################ 设置Dubbo参数 ############################
        // JVM参数：-Ddubbo.protocol.dubbo.port=20880
        String dubboPort = getDubboPort();

        System.setProperty("crtCurrentApplicationName", APPLICATION_NAME);
        System.setProperty("crtDubboPort", dubboPort);

        System.setProperty("dubbo.protocol.dubbo.port", dubboPort);
        System.setProperty("spring.dubbo.application.name", "captcha_service");
        System.setProperty("spring.dubbo.scan", "win.leizhang.demo.captcha");

        // ############################ 启动spring-boot ############################
        printConsoleInfoLog("开始启动" + APPLICATION_NAME + "应用，dubbo协议端口号：" + dubboPort);
        long startTime = System.currentTimeMillis();

        // fluent API 方式
        new SpringApplicationBuilder()
                .sources(Application.class)
                .bannerMode(Banner.Mode.OFF)
                .web(false)
                .registerShutdownHook(true)
                .run(args);

        long elasticsTime = System.currentTimeMillis() - startTime;

        // ############################ 最终输出 ############################
        String startedMsg = APPLICATION_NAME + " started, dubboPort=" + dubboPort + ", elapsedTime=" + elasticsTime + "ms";
        printConsoleInfoLog(startedMsg);
        logger.info(startedMsg);
    }

    private static String getNow() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DATE_TIME_FORMATTER);
    }

    private static void printConsoleInfoLog(String msg) {
        System.out.println(getNow() + " " + msg);
    }

    private static void printConsoleErrorLog(String msg) {
        System.err.println(getNow() + " " + msg);
    }

    private static String getDubboPort() {
        String dubboPort = System.getProperty(VM_OPTIONS_DUBBO);
        if (StringUtils.isBlank(dubboPort)) {
            dubboPort = DEFAULT_DUBBO_PORT;
            System.setProperty(VM_OPTIONS_DUBBO, dubboPort);
        } else {
            try {
                Integer.parseInt(dubboPort);
            } catch (Exception e) {
                printConsoleErrorLog(APPLICATION_NAME + "应用启动失败！错误的dubbo端口号参数：" + VM_OPTIONS_DUBBO + "=" + dubboPort);
                System.exit(1);
            }
        }
        return dubboPort;
    }

}
