package com.shivam.todoBackend.controller;

import com.shivam.todoBackend.DataTransferObjects.ApiResponse;
import com.shivam.todoBackend.model.User;
import com.shivam.todoBackend.repository.UserRepository;
import com.shivam.todoBackend.util.JwtUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;



    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Validated @RequestBody User user){
        if(userRepository.existsByUsername(user.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "Username already taken!"));
        }
        if(userRepository.existsByEmail(user.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "Email already in use!"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity
                .ok(new ApiResponse<>(true, "User registered successfully"));
    }

    @PostMapping("/signin")
    public ResponseEntity authenticateUser(@Validated @RequestBody Map<String, String> loginRequest){

        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())){
            return ResponseEntity.badRequest().body(new ApiResponse<>(
                            false,
                            "Invalid usename or password"
                    ));
        }


        Map<String, String> response = new HashMap<>();

        String token = jwtUtil.generateToken(username);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
