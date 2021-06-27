package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Validated
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DemoRequest {

    @NotBlank
    private String name;

    @Max(100)
    private Integer age;
}
