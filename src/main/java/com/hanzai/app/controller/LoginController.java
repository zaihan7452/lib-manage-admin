package com.hanzai.app.controller;

import com.hanzai.app.dto.LoginRequest;
import com.hanzai.app.dto.LoginResponse;
import com.hanzai.app.model.Result;
import com.hanzai.app.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Login API", description = "Login API")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginController(AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Login to authenticate user and return JWT token
     *
     * @param loginRequest login credentials
     * @return login response with JWT token
     */
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Login to authenticate user and return JWT token")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        // Get user details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generate JWT token
        String token = jwtUtil.generateToken(userDetails.getUsername());

        // Return response with token
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return Result.success(loginResponse);
    }

}
