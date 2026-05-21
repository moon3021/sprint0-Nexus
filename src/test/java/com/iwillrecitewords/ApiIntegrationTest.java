package com.iwillrecitewords;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

// 🔥 核心修复：只保留必要的测试监听器，彻底移除Mockito相关的监听器
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testGetRandomWord_ShouldReturnValidWord() {
        given()
                .when()
                .get("/api/word/random")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("word", notNullValue())
                .body("meaning", notNullValue());
    }

    @Test
    void testGetWordCount_ShouldBePositive() {
        // 先提取响应体为整数，再进行断言
        Integer wordCount = given()
                .when()
                .get("/api/word/count")
                .then()
                .statusCode(200)
                .extract()
                .as(Integer.class);

        // 手动断言数字大于0
        org.hamcrest.MatcherAssert.assertThat(wordCount, org.hamcrest.Matchers.greaterThan(0));
    }

    @Test
    void testAddAndGetWrongWord_ShouldWork() {
        String requestBody = """
                {
                    "word": "test",
                    "phonetic": "/test/",
                    "partOfSpeech": "v.",
                    "meaning": "测试",
                    "exampleEn": "This is a test.",
                    "exampleCn": "这是一个测试。"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/wrong-word/add")
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/api/wrong-word/list")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("word", hasItem("test"));
    }
}