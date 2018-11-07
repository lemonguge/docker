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
import org.springframework.data.redis.connection.DataType;
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
        DataType type = stringRedisTemplate.type(name);
        switch (type) {
            case STRING:
                return new Response(name, stringRedisTemplate.opsForValue().get(name));
            case LIST:
                return new Response(name, stringRedisTemplate.opsForList().range(name, 0, -1));
            case HASH:
                return new Response(name, stringRedisTemplate.opsForHash().entries(name));
            case SET:
                return new Response(name, stringRedisTemplate.opsForSet().members(name));
            case ZSET:
                return new Response(name, stringRedisTemplate.opsForZSet().rangeWithScores(name, 0, -1));
            default:
                return new Response(name, null);
        }
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
        private Object value;

        public Response(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }
    }
}
