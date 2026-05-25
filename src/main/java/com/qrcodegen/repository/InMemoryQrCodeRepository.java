package com.qrcodegen.repository;

import com.qrcodegen.model.QrCode;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InMemoryQrCodeRepository implements QrCodeRepository {

    private final Map<String, QrCode> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong();

    // ── Custom query ──────────────────────────────────────────────────────────

    @Override
    public List<QrCode> findByLabel(String label) {
        return store.values().stream()
                .filter(q -> label.equals(q.getLabel()))
                .collect(Collectors.toList());
    }

    // ── MongoRepository ───────────────────────────────────────────────────────

    @Override
    public <S extends QrCode> S insert(S entity) { return save(entity); }

    @Override
    public <S extends QrCode> List<S> insert(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        entities.forEach(e -> result.add(save(e)));
        return result;
    }

    @Override
    public <S extends QrCode> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not supported in-memory");
    }

    @Override
    public <S extends QrCode> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException("Example queries not supported in-memory");
    }

    // ── ListCrudRepository ────────────────────────────────────────────────────

    @Override
    public <S extends QrCode> S save(S entity) {
        if (entity.getId() == null) entity.setId(String.valueOf(idGen.incrementAndGet()));
        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends QrCode> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        entities.forEach(e -> result.add(save(e)));
        return result;
    }

    @Override
    public Optional<QrCode> findById(String id) { return Optional.ofNullable(store.get(id)); }

    @Override
    public boolean existsById(String id) { return store.containsKey(id); }

    @Override
    public List<QrCode> findAll() { return new ArrayList<>(store.values()); }

    @Override
    public List<QrCode> findAllById(Iterable<String> ids) {
        List<QrCode> result = new ArrayList<>();
        ids.forEach(id -> Optional.ofNullable(store.get(id)).ifPresent(result::add));
        return result;
    }

    @Override
    public long count() { return store.size(); }

    @Override
    public void deleteById(String id) { store.remove(id); }

    @Override
    public void delete(QrCode entity) { store.remove(entity.getId()); }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) { ids.forEach(store::remove); }

    @Override
    public void deleteAll(Iterable<? extends QrCode> entities) {
        entities.forEach(e -> store.remove(e.getId()));
    }

    @Override
    public void deleteAll() { store.clear(); }

    // ── ListPagingAndSortingRepository ────────────────────────────────────────

    @Override
    public List<QrCode> findAll(Sort sort) { return findAll(); }

    @Override
    public Page<QrCode> findAll(Pageable pageable) {
        List<QrCode> all = findAll();
        return new PageImpl<>(all, pageable, all.size());
    }

    // ── QueryByExampleExecutor ────────────────────────────────────────────────

    @Override
    public <S extends QrCode> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not supported in-memory");
    }

    @Override
    public <S extends QrCode> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException("Example queries not supported in-memory");
    }

    @Override
    public <S extends QrCode> long count(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not supported in-memory");
    }

    @Override
    public <S extends QrCode> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not supported in-memory");
    }

    @Override
    public <S extends QrCode, R> R findBy(Example<S> example,
            Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException("Example queries not supported in-memory");
    }
}
