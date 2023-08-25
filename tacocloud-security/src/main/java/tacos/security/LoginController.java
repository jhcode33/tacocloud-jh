package tacos.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.User;
import tacos.data.UserRepository;

@RestController
@RequestMapping("/customLogin")
@CrossOrigin("*")
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping(consumes="application/json")
    @ResponseBody
    public ResponseEntity<User> processLogin(@RequestBody User user, HttpSession httpSession) {
        try {
            // 회원가입한 유저인지 확인
            User loginUser = loginUser(user);
            if (loginUser != null) {
                return ResponseEntity.ok(loginUser);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(user);
    }

    private User loginUser(User user) throws Exception {
        User loginUser = null;

        loginUser = userRepository.findByUsername(user.getUsername());
        if (loginUser != null && loginUser.getPassword().equals(user.getPassword())) {
            return loginUser;
        }
        return loginUser;
    }
}
