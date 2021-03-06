package cn.homjie.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author jiehong.jh
 * @date 2018/11/6
 */
@SpringBootApplication
@ImportResource("classpath:provider.xml")
public class RedisProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisProviderApplication.class, args);
    }
}
