package redisprototype.redistest.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {
    private final RedisTemplate<String, Object> redisTemplate;

    @GetMapping(value="/{key}")
    public ResponseEntity<Object> getValue(@PathVariable String key) {
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        Object value = vop.get(key);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    @PostMapping(value="/")
    public ResponseEntity<Object> add(@RequestBody AdditionalValue additionalValue) throws URISyntaxException {
        
        URI uri = new URI(additionalValue.getKey());
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set(additionalValue.getKey(), additionalValue.getValue());

        return ResponseEntity.created(uri).build();
    }
    
}