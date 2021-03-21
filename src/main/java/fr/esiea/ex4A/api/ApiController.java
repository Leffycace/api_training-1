package fr.esiea.ex4A.api;

import fr.esiea.ex4A.agify.AgifyService;
import fr.esiea.ex4A.data.Match;
import fr.esiea.ex4A.data.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    private final AgifyService agifyService;

    public ApiController(AgifyService agifyService) {
        this.agifyService = agifyService;
    }

    @PostMapping("/api/inscription")
    public ResponseEntity<?> addUser(@RequestBody Map<String, String> request) throws IOException {
        String userName = request.get("userName");
        String userCountry = request.get("userCountry");
        Integer userAge = agifyService.getAge(userName, userCountry);

        User user = new User(request.get("userEmail"), userName, request.get("userTweeter"), userCountry, request.get("userSex"), request.get("userSexPref"), userAge);
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
        List<Match> matches = new ArrayList<>();

        for (User user : users) {
            Integer ageDiff = Math.abs(user.getUserAge() - requestUser.getUserAge());
            if (requestUser.getUserSexPref().equalsIgnoreCase(user.getUserSex())
                && requestUser.getUserSex().equalsIgnoreCase(user.getUserSexPref())
                && ageDiff <= 4){
                matches.add(new Match(user.getUserName(), user.getUserTweeter()));
            }
        }

        return new ResponseEntity<>(matches, HttpStatus.OK);
    }
}
