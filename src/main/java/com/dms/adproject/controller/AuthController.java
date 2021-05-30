package com.dms.adproject.controller;

import com.dms.adproject.dto.request.LoginRequest;
import com.dms.adproject.dto.request.SignUpRequest;
import com.dms.adproject.dto.response.JwtResponse;
import com.dms.adproject.dto.response.MessageResponse;
import com.dms.adproject.model.User;
import com.dms.adproject.repository.UserRepository;
import com.dms.adproject.security.JwtUtils;
import com.dms.adproject.service.impl.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1/app/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Login Request
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> userLogin(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()
        ));
    }

    /**
     * Add User
     *
     * @param signupRequest
     * @return
     */
    @PostMapping("/sing-up")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignUpRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken"));
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use"));
        }

        User user = signupRequest.getUser();
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Sign Up Successful successfully!"));
    }
}
