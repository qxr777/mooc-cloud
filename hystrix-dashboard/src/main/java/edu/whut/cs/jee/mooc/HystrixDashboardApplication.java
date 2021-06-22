package edu.whut.cs.jee.mooc;

import cn.hutool.core.net.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author qixin on 2021/6/22.
 * @version 1.0
 */
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboardApplication {

    public static void main(String[] args) {
        int port = 8070;

        if(!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n", port );
            System.exit(1);
        }

        new SpringApplicationBuilder(HystrixDashboardApplication.class).properties("server.port=" + port).run(args);

    }
}
