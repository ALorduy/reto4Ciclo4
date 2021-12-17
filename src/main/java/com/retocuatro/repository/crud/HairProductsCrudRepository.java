package com.retocuatro.repository.crud;


import com.retocuatro.model.HairProducts;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Alberto
 */
public interface HairProductsCrudRepository extends MongoRepository<HairProducts, String> {
    
}
