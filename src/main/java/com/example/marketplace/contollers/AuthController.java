package com.example.marketplace.contollers;

import com.example.marketplace.JWT.JwtUtils;
import com.example.marketplace.Playload.Request.*;
import com.example.marketplace.Playload.Response.MessageResponse;
import com.example.marketplace.Playload.Response.JwtResponse;
import com.example.marketplace.entities.Role;
import com.example.marketplace.entities.User;
import com.example.marketplace.enumerations.ERole;
import com.example.marketplace.repository.IRoleRepository;
import com.example.marketplace.repository.IUserRepository;
import com.example.marketplace.services.IUserServices;
import com.example.marketplace.services.MailerService;
import com.example.marketplace.services.RandomString;
import com.example.marketplace.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")

public class AuthController {

  final AuthenticationManager authenticationManager;
    @Autowired

    IUserRepository userRepository;
    @Autowired
    IRoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RandomString randomString;
    @Autowired
    MailerService mailerService;
    @Autowired
    IUserServices userServices;


    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User u = userRepository.findByUsername(loginRequest.getUsername()).get();

            if (u.getStatus()) {
                if(u.getBanTime() == null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String jwt = jwtUtils.generateJwtToken(authentication);


                    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                    List<String> roles = userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList());

                    return ResponseEntity.ok(new JwtResponse(jwt,
                            userDetails.getId(),
                            userDetails.getUsername(),
                            userDetails.getEmail(),
                            roles));
                }
                else return new ResponseEntity("Account Banned ! ", HttpStatus.BAD_REQUEST);
            } else return new ResponseEntity("Account Non verified ! Please Proceed to Verification Or your Account will be deleted Automatically", HttpStatus.BAD_REQUEST);

    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if(signUpRequest.getUsername() == null){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(signUpRequest.getEmail() == null){

            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
//                    ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create new user's account
        User user = new User(/*signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())*/);
     user.setUsername(signUpRequest.getUsername());
     user.setEmail(signUpRequest.getEmail());
     user.setFirstName(signUpRequest.getFirstName());
     user.setLastName(signUpRequest.getLastName());
     user.setCinUser(signUpRequest.getCinUser());
     user.setPhoneNumber(signUpRequest.getPhoneNumber());
     user.setPassword(encoder.encode(signUpRequest.getPassword()));
        LocalDateTime d = LocalDateTime.now();
     user.setCreatedAt(d);
        String code = randomString.randomGeneratedString(6);
     user.setCode(code);
        mailerService.sendEmail(user.getEmail(),"Account Creation"," Hello "+user.getFirstName()+" "+user.getLastName()+"\n Your account is almost created, please Login and write this code in the right field to complete your inscription !\n Your code is :"+ code);
    user.setStatus(false);

        List<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "moderator":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    case "livreur":
                        Role livRole = roleRepository.findByName(ERole.ROLE_LIVREUR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(livRole);
                        break;
                    case "client":
                        Role clientRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(clientRole);

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    @PostMapping("/verification")
    public ResponseEntity verifyUser(@RequestBody VerificationRequest verificationRequest){
        return userServices.Verification(verificationRequest.getEmail(),verificationRequest.getCode());
        }

    @PostMapping("/forgotPassword")
    ResponseEntity forgotPassword(@RequestBody ForgotPassword forgotPassword) {

        return userServices.forgotPassword(forgotPassword.getEmail());
    }
    @PostMapping("/resetPassword")
    ResponseEntity resetPassword(@RequestBody ResetPassword resetPassword) {
        return userServices.resetPassword(resetPassword.getVerificationCode(),resetPassword.getNewPassword());
    }


}
