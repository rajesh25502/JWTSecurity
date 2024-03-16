package com.example.springJWT.Controller;


import com.example.springJWT.DTO.ReqRes;
import com.example.springJWT.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController  {

    @Autowired
    private AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<ReqRes> signUp(@RequestBody ReqRes signUpRequest)
    {
        return  ResponseEntity.ok(authService.signUp(signUpRequest));
    }
    @PostMapping("/signIn")
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes signInRequest)
    {
        return  ResponseEntity.ok(authService.signIn(signInRequest));
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest)
    {
        return  ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
}
