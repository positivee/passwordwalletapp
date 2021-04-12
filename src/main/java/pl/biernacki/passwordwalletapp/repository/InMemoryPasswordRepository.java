package pl.biernacki.passwordwalletapp.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.biernacki.passwordwalletapp.entity.Password;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPasswordRepository implements PasswordRepository {

    private final Map<Long, Password> data = new ConcurrentHashMap<>();

    @Override
    public List<Password> findAll() {
        return null;
    }

    @Override
    public List<Password> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Password> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Password> findAllById(Iterable<Long> iterable) {
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
    public void delete(Password password) {

    }

    @Override
    public void deleteAll(Iterable<? extends Password> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public  Password save(Password password) {
        Password tempPass = new Password(new Random().nextLong(), password.getLogin(), password.getPasswordWeb(), password.getWebAddress(), password.getDescription(), password.getUsers(),password.getSharePasswords(),password.getPasswordHistoryList());
        data.put(tempPass.getId(), tempPass);
        return tempPass;
    }

    @Override
    public <S extends Password> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Password> findById(Long aLong) {
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
    public <S extends Password> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Password> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Password getOne(Long aLong) {
        return data.get(aLong);
    }

    @Override
    public <S extends Password> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Password> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Password> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Password> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Password> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Password> boolean exists(Example<S> example) {
        return false;
    }
}
