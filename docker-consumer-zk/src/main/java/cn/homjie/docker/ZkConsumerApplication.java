package cn.homjie.docker;

import cn.homjie.docker.api.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiehong.jh
 * @date 2018/11/6
 */
@RestController
@ImportResource("classpath:consumer.xml")
@SpringBootApplication
public class ZkConsumerApplication {

    @Autowired
    private HelloService helloService;

    public static void main(String[] args) {
        SpringApplication.run(ZkConsumerApplication.class, args);
    }

    @GetMapping("/hello/{name}")
    public String home(@PathVariable String name) {
        return helloService.sayHello(name);
    }
}
