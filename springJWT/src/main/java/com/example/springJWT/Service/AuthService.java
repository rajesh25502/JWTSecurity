package com.example.springJWT.Service;

import com.example.springJWT.DTO.ReqRes;
import com.example.springJWT.Entity.OurUsers;
import com.example.springJWT.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.util.HashMap;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private  JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private  AuthenticationManager authenticationManager;

    public ReqRes signUp(ReqRes registrationRequest)
    {
        ReqRes resp=new ReqRes();
        try {
            OurUsers ourUsers=new OurUsers();
            ourUsers.setEmail(registrationRequest.getEmail());
            ourUsers.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            ourUsers.setRole(registrationRequest.getRole());
            OurUsers ourUsersResult=userRepo.save(ourUsers);

            if(ourUsersResult!=null && ourUsersResult.getId()>0)
            {
                resp.setOurUsers(ourUsersResult );
                resp.setMessage("User saved Successfully");
                resp.setStatusCode(200);
            }
        }
        catch (Exception e)
        {
            resp.setStatusCode(500);
            resp.setMessage(e.getMessage());
        }
        return  resp;
    }

    public ReqRes signIn(ReqRes signInRequest)
    {
        ReqRes response=new ReqRes();
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),signInRequest.getPassword()));
            var user=userRepo.findByEmail(signInRequest.getEmail()).orElseThrow();
            var jwt=jwtUtils.generateToken(user);
            var refreshToken=jwtUtils.generateRefreshToken(new HashMap<>(),user);

            response.setStatusCode(200);
            response.setMessage("Signed In Successfully ");
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");

        }
        catch (Exception e)
        {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return  response;
    }

    public ReqRes refreshToken(ReqRes refreshTokenRequest)
    {
        ReqRes response=new ReqRes();
        String email=jwtUtils.extractUserName(refreshTokenRequest.getToken());
        OurUsers user=userRepo.findByEmail(email).orElseThrow();
        if(jwtUtils.isTokenValid(refreshTokenRequest.getToken(),user))
        {
            var jwt=jwtUtils.generateToken(user);

            response.setStatusCode(200);
            response.setMessage("Token Successfully refreshed ");
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            response.setExpirationTime("24Hr");
        }
        else {
            response.setStatusCode(500);
        }
        return response;
    }

}
