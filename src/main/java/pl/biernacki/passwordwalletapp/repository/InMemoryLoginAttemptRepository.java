package pl.biernacki.passwordwalletapp.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.biernacki.passwordwalletapp.entity.IpAddressAttempt;
import pl.biernacki.passwordwalletapp.entity.LoginAttempt;
import pl.biernacki.passwordwalletapp.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryLoginAttemptRepository implements LoginAttemptRepository {
    private final Map<Long, LoginAttempt> data = new ConcurrentHashMap<>();


    @Override
    public boolean existsByUserLogin(String login) {
        return false;
    }

    @Override
    public List<LoginAttempt> findAll() {
        return null;
    }

    @Override
    public List<LoginAttempt> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<LoginAttempt> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<LoginAttempt> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(LoginAttempt loginAttempt) {

    }

    @Override
    public void deleteAll(Iterable<? extends LoginAttempt> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public LoginAttempt save(LoginAttempt loginAttempt) {
        LoginAttempt tempLoginAttempt = new LoginAttempt(new Random().nextLong(), loginAttempt.isLoginResult(),loginAttempt.getAttempts(),loginAttempt.getSuccessfulLoginTime(),loginAttempt.getFailedLoginTime(),loginAttempt.getBlockedUntil(), loginAttempt.getUser());
        data.put(tempLoginAttempt.getId(),tempLoginAttempt);
        return tempLoginAttempt;
    }

    @Override
    public <S extends LoginAttempt> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<LoginAttempt> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends LoginAttempt> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<LoginAttempt> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public LoginAttempt getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends LoginAttempt> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends LoginAttempt> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends LoginAttempt> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends LoginAttempt> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends LoginAttempt> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends LoginAttempt> boolean exists(Example<S> example) {
        return false;
    }
}
