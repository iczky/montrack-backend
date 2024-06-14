package com.montrackBackend.montrack.users;

import com.montrackBackend.montrack.auth.helper.GetContext;
import com.montrackBackend.montrack.response.Response;
import com.montrackBackend.montrack.auth.dto.ForgotPasswordRequestDto;
import com.montrackBackend.montrack.users.dto.RegisterRequestDto;
import com.montrackBackend.montrack.users.dto.SetPinReqDto;
import com.montrackBackend.montrack.users.service.UserService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Log
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto registerRequestDto){
        return Response.successResponse("User successfully register", userService.register(registerRequestDto));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(){
        SecurityContext context = SecurityContextHolder.getContext();
        log.info(context.toString());
        Authentication authentication = context.getAuthentication();
        String name = authentication.getName();
        return Response.successResponse("User Profile", userService.findByEmail(name));
    }

    @PostMapping("/set-pin")
    public ResponseEntity<?> setPin(@RequestBody SetPinReqDto reqDto){
        var claims = GetContext.claims();
        return Response.successResponse("Pin created", userService.setPin(reqDto, (String) claims.get("sub")));
    }
}
