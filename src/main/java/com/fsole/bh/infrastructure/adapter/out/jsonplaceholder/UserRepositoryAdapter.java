package com.fsole.bh.infrastructure.adapter.out.jsonplaceholder;

import com.fsole.bh.domain.exception.ExternalServiceTimeoutException;
import com.fsole.bh.domain.exception.NotFoundException;
import com.fsole.bh.domain.model.User;
import com.fsole.bh.domain.port.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
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
        log.info("getAllUsers - fetching all users...");

        try{
            User[] users = restTemplate.getForObject(BASE_URL, User[].class);
            log.debug("getAllUsers - all users were fetched successfully");
            return Arrays.asList(users);
        } catch (ResourceAccessException ex) {
            log.error("getAllUsers - timeout while trying to fetch all users");
            throw new ExternalServiceTimeoutException(BASE_URL);
        }
    }

    @Override
    public User getUserById(Long id) {
        log.info("getUserById - fetching user id '{}'...", id);
        String url = BASE_URL + id;

        try {
            User user = restTemplate.getForObject(url, User.class);
            log.debug("getUserById - the required user was fetched successfully");
            return user;
        } catch (HttpClientErrorException.NotFound ex) {
            // this should never happen since userId comes from the post author, but added for safety
            String error = "the required user was not found";
            log.error("getUserById - {}", error);
            throw new NotFoundException(error);
        } catch (ResourceAccessException ex) {
            log.error("getUserById - timeout while trying to fetch the required user");
            throw new ExternalServiceTimeoutException(url);
        }
    }
}
