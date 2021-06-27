package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Validated
@Slf4j
@RestController
public class DemoController {

    @PostMapping("/demo/{id}")
    public void demo(
            @Valid @Max(10) @PathVariable(name = "id") Integer id,
            @Valid @Size(max = 5, min = 3) @RequestParam(name = "access") String access,
            @Valid @RequestBody DemoRequest request
    ) {
        log.info("id: {}", id);
        log.info("access: {}", access);
        log.info("request: {}", request);
    }
}
