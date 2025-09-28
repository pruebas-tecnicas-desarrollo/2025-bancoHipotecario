package com.fsole.bh.infrastructure.adapter.out.jsonplaceholder;

import com.fsole.bh.domain.model.Post;
import com.fsole.bh.domain.model.User;
import com.fsole.bh.domain.port.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryAdapter implements UserRepository {
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users/";

    @Override
    public List<User> getAllUsers() {
        log.info("getAllUsers - fetching all users");
        User[] users = restTemplate.getForObject(BASE_URL, User[].class);
        return Arrays.asList(users);
    }

    @Override
    public User getUserById(Long id) {
        log.info("getUserById - fetching user id '{}'", id);
        String url = BASE_URL + id;
        return restTemplate.getForObject(url, User.class);
    }
}
