package store.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<ItemModel, String> {
    public Iterable<ItemModel> findByIdOrder(String OrderId);
}
