package com.retocuatro.repository.crud;

import com.retocuatro.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author Alberto
 */
public interface OrderCrudRepository extends MongoRepository<Order, Integer> {
    
    //Lista las ordenes de pedido que coincida con la zona recibida como parametro
    @Query("{'salesMan.zone': ?0}")
    List<Order> findByZone(final String zone);
    
    //Lista las ordenes x estado
    @Query("{status: ?0}")
    List<Order> findByStatus(final String status);
    
    //Encuentra la orden con el id maximo
    Optional<Order> findTopByOrderByIdDesc();
}
