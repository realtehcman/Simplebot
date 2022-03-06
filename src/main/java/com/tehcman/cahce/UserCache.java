package com.tehcman.cahce;

import com.tehcman.entities.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserCache implements Cache<Object> {
    private Map<Long, User> cacheOfAllUsers;

    public UserCache() {
        this.cacheOfAllUsers = new HashMap<>();
    }


    @Override
    public void add(User user) {
        cacheOfAllUsers.put(user.getId(), user);
    }

    @Override
    public void remove(Long id) {
        cacheOfAllUsers.remove(id);
    }

    @Override
    public Object findBy(Long id) {
        return null;
    }

    @Override
    public List<Object> getAll() {
        return new ArrayList<>(cacheOfAllUsers.values());
    }
}

