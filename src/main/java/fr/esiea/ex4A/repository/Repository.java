package fr.esiea.ex4A.repository;

import fr.esiea.ex4A.data.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Repository {

    private final List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }
}
