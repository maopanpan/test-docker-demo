package com.sinochem.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：TestController<br>
 * 类描述：<br>
 * 创建时间：2019年05月17日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
@RestController
public class TestController {

    @GetMapping(value = "/sayHello")
    public String sayHello(String message) {
        return message;
    }
}
