package store.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<OrderModel, String> {
    public Iterable<OrderModel> findByIdAccount(String idAccount);
}
