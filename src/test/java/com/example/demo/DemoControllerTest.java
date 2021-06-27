package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DemoController.class)
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("正常系")
    void demo_test() throws Exception {
        mockMvc.perform(
                post("/demo/5?access=hoge")
                        .content("{\"name\": \"foo\", \"age\": \"12\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("異常系: パスパラのテスト: 不正なID")
    void demo_id_test() throws Exception {
        mockMvc.perform(
                post("/demo/11?access=hoge")
                        .content("{\"name\": \"foo\", \"age\": \"12\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .json("{\"error\":\"Validation error.\", "
                                + "\"msg\": \"demo.id: must be less than or equal to 10\"}"));
    }

    @Test
    @DisplayName("異常系: クエリパラメータのテスト: 不正な住所")
    void demo_access_test() throws Exception {
        mockMvc.perform(
                post("/demo/10?access=hogehoge")
                        .content("{\"name\": \"foo\", \"age\": \"12\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .json("{\"error\":\"Validation error.\", "
                                + "\"msg\": \"demo.access: size must be between 3 and 5\"}"));
    }

    @Test
    @DisplayName("異常系: リクエストボディのテスト: 不正な名前")
    void demo_name_test() throws Exception {
        mockMvc.perform(
                post("/demo/10?access=hoge")
                        .content("{\"name\": null, \"age\": \"12\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .json("{\"error\":\"Validation error.\", "
                                + "\"msg\": \"name\"}"));
    }

    @Test
    @DisplayName("異常系: リクエストボディのテスト: 不正な年齢")
    void demo_age_test() throws Exception {
        mockMvc.perform(
                post("/demo/10?access=hoge")
                        .content("{\"name\": \"foo\", \"age\": \"101\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .json("{\"error\":\"Validation error.\", "
                                + "\"msg\": \"age\"}"));
    }
}