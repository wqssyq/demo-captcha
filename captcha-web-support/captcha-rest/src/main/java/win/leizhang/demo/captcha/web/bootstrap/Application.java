package win.leizhang.demo.captcha.web.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@ServletComponentScan("win.leizhang.demo.captcha.web")
@ImportResource({"classpath:spring/applicationContext.xml"})
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String APPLICATION_NAME = "captcha-rest";

    public static void main(String[] args) {
        // ############################ 设置参数 ############################
        // JVM参数：-Dserver.port=8888
        String serverPort = System.getProperty("server.port");

        // log4j用
        System.setProperty("crtCurrentApplicationName", APPLICATION_NAME);
        System.setProperty("crtServerPort", serverPort);

        // 设置1
        System.setProperty("server.context-path", "/demo/captcha");
        System.setProperty("server.servlet-path", "*.html");

        // 设置2
        System.setProperty("spring.autoconfigure.exclude", "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration");
        System.setProperty("spring.application.name", APPLICATION_NAME);
        System.setProperty("spring.dubbo.application.name", "captcha_rest");
        System.setProperty("spring.dubbo.scan", "win.leizhang.demo.captcha.web");

        // ############################ 启动REST服务 ############################
        printConsoleInfoLog("开始启动" + APPLICATION_NAME + "应用，当前应用节点HTTP端口号：" + serverPort);
        long startTime = System.currentTimeMillis();

        new SpringApplicationBuilder()
                .sources(Application.class)
                .registerShutdownHook(true)
                .run(args);

        long elasticsTime = System.currentTimeMillis() - startTime;

        // ############################ 最终输出 ############################
        String startedMsg = APPLICATION_NAME + " started, serverPort=" + serverPort + ", elapsedTime=" + elasticsTime + "ms";
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


}
