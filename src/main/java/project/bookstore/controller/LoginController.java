package project.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.bookstore.dto.login.LoginParamDto;
import project.bookstore.service.CustomMemberDetailsService;
import project.bookstore.service.LoginService;
import project.bookstore.tokenmanager.JwtTokenProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final JwtTokenProvider jwtTokenProvider;
    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody LoginParamDto dto) {
        return loginService.login(dto);
    }
}
