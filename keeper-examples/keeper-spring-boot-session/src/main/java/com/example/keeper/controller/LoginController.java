package com.example.keeper.controller;

import com.example.keeper.model.Response;
import io.github.biezhi.keeper.Keeper;
import io.github.biezhi.keeper.core.authc.impl.SimpleAuthorToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class LoginController {

    @PostMapping("/login")
    public Response<String> login(String username, String password) {

        Keeper.getSubject().login(
                new SimpleAuthorToken(username, password));

        return Response.<String>builder().code(200).data("登录成功").build();
    }

    /**
     * 无需登录访问
     *
     * @return
     */
    @GetMapping("/guest")
    public Response<String> guest() {
        return Response.<String>builder().code(200).data("guest!").build();
    }

    /**
     * 需登陆后访问
     *
     * @return
     */
    @GetMapping("/hello")
    public Response<String> hello() {
        return Response.<String>builder().code(200).data("i,m hello!").build();
    }

    @RequestMapping("/logout")
    public Response<String> logout() {
        Keeper.getSubject().logout();
        return Response.<String>builder().code(200).data("注销成功").build();
    }

}
