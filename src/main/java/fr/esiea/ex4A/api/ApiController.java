package fr.esiea.ex4A.api;

import fr.esiea.ex4A.data.Match;
import fr.esiea.ex4A.data.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @GetMapping("/api/matches")
    public ResponseEntity<?> getMatches(@RequestParam("userName") String userName, @RequestParam("userCountry") String userCountry) {
        User requestUser = null;

        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(userName) && user.getUserCountry().equalsIgnoreCase(userCountry)) {
                requestUser = user;
                break;
            }
        }

        // user not found
        if (requestUser == null) {
            return new ResponseEntity<>(new ArrayList<User>(), HttpStatus.OK);
        }

        // find matches for user
        List<Match> matches = Arrays.asList(
            new Match("Michel", "michelbgdu77"),
            new Match("Amandine", "amandinedu36")
        );

        return new ResponseEntity<>(matches, HttpStatus.OK);
    }
}
