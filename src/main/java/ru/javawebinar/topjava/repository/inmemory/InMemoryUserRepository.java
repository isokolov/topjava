package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public User save(User user) {
        log.info("save {}", user);

        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }

        // treat case: update, but absent in storage
        repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
        return user;
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(repository.get(id)) != null;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> users = (List)repository.values();
        List<User> sortedUsers = users.stream().sorted((u1, u2) -> u1.getName().compareTo(u2.getName())).collect(Collectors.toList());
        return sortedUsers;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        List<User> listUsers = (List)repository.values();
        for (User user: listUsers) {
            User userWithEmail = user;
            if (user.getEmail().equals(email))
                return user;
        }
        return null;
    }
}
