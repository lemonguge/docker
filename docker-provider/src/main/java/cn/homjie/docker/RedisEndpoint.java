package cn.homjie.docker;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author jiehong.jh
 * @date 2018/11/6
 */
@Component
@WebEndpoint(id = "redis")
public class RedisEndpoint {

    private static final Map<String, String> UPDATE_OK = Collections
        .unmodifiableMap(Collections.singletonMap("message", "Update ok."));

    private static final Map<String, String> DELETE_OK = Collections
        .unmodifiableMap(Collections.singletonMap("message", "Delete ok."));

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ReadOperation
    public Set<String> listAll() {
        return stringRedisTemplate.keys("*");
    }

    @ReadOperation
    public Response select(@Selector String name) {
        return new Response(name, stringRedisTemplate.opsForValue().get(name));
    }

    @DeleteOperation
    public Map<String, String> delete(@Selector String name) {
        stringRedisTemplate.delete(name);
        return DELETE_OK;
    }

    @WriteOperation
    public Map<String, String> update(@Selector String name, @Selector String value) {
        stringRedisTemplate.opsForValue().set(name, value);
        return UPDATE_OK;
    }

    public static final class Response {

        private String key;
        private String value;

        public Response(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}
