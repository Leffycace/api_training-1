package fr.esiea.ex4A.api;

import fr.esiea.ex4A.data.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class ApiController {

    /**
     * ArrayList acting as the database table for storing users
     */
    private final ArrayList<User> users = new ArrayList<>();

    @PostMapping("/api/inscription")
    public ResponseEntity<?> addUser(@RequestBody Map<String, String> request) {
        User user = new User(request.get("userEmail"), request.get("userName"), request.get("userTweeter"), request.get("userCountry"), request.get("userSex"), request.get("userSexPref"));
        users.add(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
