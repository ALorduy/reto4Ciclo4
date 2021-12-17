package com.retocuatro.service;

import com.retocuatro.model.HairProducts;
import com.retocuatro.repository.HairProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 *
 * @author Alberto
 */
@Service
public class HairProductsService {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private HairProductsRepository hairProductsRepository;

    public List<HairProducts> getAll() {
        return hairProductsRepository.getAll();
    }

    public Optional<HairProducts> getHairProducts(String reference) {
        return hairProductsRepository.getHairProducts(reference);
    }

    public HairProducts create(HairProducts hairProducts) {
        if (hairProducts.getReference() == null) {
            return hairProducts;
        } else {
            return hairProductsRepository.create(hairProducts);
        }
    }

    public HairProducts update(HairProducts hairProducts) {

        if (hairProducts.getReference() != null) {
            Optional<HairProducts> hairProductsDb = hairProductsRepository.getHairProducts(hairProducts.getReference());
            if (!hairProductsDb.isEmpty()) {
                if (hairProducts.getBrand() != null) {
                    hairProductsDb.get().setBrand(hairProducts.getBrand());
                }

                if (hairProducts.getCategory() != null) {
                    hairProductsDb.get().setCategory(hairProducts.getCategory());
                }

                if (hairProducts.getName() != null) {
                    hairProductsDb.get().setName(hairProducts.getName());
                }

                if (hairProducts.getDescription() != null) {
                    hairProductsDb.get().setDescription(hairProducts.getDescription());
                }
                if (hairProducts.getPrice() != 0.0) {
                    hairProductsDb.get().setPrice(hairProducts.getPrice());
                }
                if (hairProducts.getQuantity() != 0) {
                    hairProductsDb.get().setQuantity(hairProducts.getQuantity());
                }
                if (hairProducts.getPhotography() != null) {
                    hairProductsDb.get().setPhotography(hairProducts.getPhotography());
                }
                hairProductsDb.get().setAvailability(hairProducts.isAvailability());
                hairProductsRepository.update(hairProductsDb.get());
                return hairProductsDb.get();
            } else {
                return hairProducts;
            }
        } else {
            return hairProducts;
        }
    }

    public boolean delete(String reference) {
        Boolean aBoolean = getHairProducts(reference).map(hairProducts -> {
            hairProductsRepository.delete(hairProducts);
            return true;
        }).orElse(false);
        return aBoolean;
    }
}
