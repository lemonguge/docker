package cn.homjie.docker.api;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiehong.jh
 * @date 2018/11/6
 */
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        log.info("response: {}", name);
        return "Hello " + name;
    }
}
