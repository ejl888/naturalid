package nl.codeable.naturalid.service;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class TestService {

    @PersistenceContext
    private EntityManager entityManager;

    public void doInJpa(Consumer<EntityManager> entityManagerConsumer) {
        entityManagerConsumer.accept(this.entityManager);
    }

    public <T> T doInJpa(Function<EntityManager, T> entityManagerConsumer) {
        return entityManagerConsumer.apply(this.entityManager);
    }
}
