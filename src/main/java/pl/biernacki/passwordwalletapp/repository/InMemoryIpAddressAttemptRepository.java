package pl.biernacki.passwordwalletapp.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.biernacki.passwordwalletapp.entity.IpAddressAttempt;
import pl.biernacki.passwordwalletapp.entity.Password;
import pl.biernacki.passwordwalletapp.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryIpAddressAttemptRepository implements IpAdressAttemptRepository {
    private final Map<Long, IpAddressAttempt> data = new ConcurrentHashMap<>();
    @Override
    public boolean existsByIpAddress(String address) {
        return false;
    }

    @Override
    public IpAddressAttempt findByIpAddress(String address) {
        return data.values()
                .stream()
                .filter( u -> u.getIpAddress().equals(address))
                .findFirst()
                .get();
    }

    @Override
    public void deleteByIpAddress(String address) {

    }

    @Override
    public List<IpAddressAttempt> findAll() {
        return null;
    }

    @Override
    public List<IpAddressAttempt> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<IpAddressAttempt> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<IpAddressAttempt> findAllById(Iterable<Long> iterable) {
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
    public void delete(IpAddressAttempt ipAddressAttempt) {

    }

    @Override
    public void deleteAll(Iterable<? extends IpAddressAttempt> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public IpAddressAttempt save(IpAddressAttempt ipAddressAttempt) {
        IpAddressAttempt tempipAddressAttempt = new IpAddressAttempt(new Random().nextLong(), ipAddressAttempt.getIpAddress(),ipAddressAttempt.getAttemptsCorrect(), ipAddressAttempt.getAttemptsIncorrect(), ipAddressAttempt.getBlockedUntil());
        data.put(tempipAddressAttempt.getId(),tempipAddressAttempt);
        return tempipAddressAttempt;
    }

    @Override
    public <S extends IpAddressAttempt> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<IpAddressAttempt> findById(Long aLong) {
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
    public <S extends IpAddressAttempt> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<IpAddressAttempt> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public IpAddressAttempt getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends IpAddressAttempt> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends IpAddressAttempt> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends IpAddressAttempt> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends IpAddressAttempt> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends IpAddressAttempt> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends IpAddressAttempt> boolean exists(Example<S> example) {
        return false;
    }
}
