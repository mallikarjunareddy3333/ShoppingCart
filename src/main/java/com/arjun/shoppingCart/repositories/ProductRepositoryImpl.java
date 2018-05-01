package com.arjun.shoppingCart.repositories;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.arjun.shoppingCart.SpringMongoConfig;
import com.arjun.shoppingCart.models.Product;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

	// For Annotation
	ApplicationContext ctx = new AnnotationConfigApplicationContext(
			SpringMongoConfig.class);
	MongoOperations mongoOperations = (MongoOperations) ctx
			.getBean("mongoTemplate");

	private static final String PRODUCT_COLLECTION = "products";

	public void setMongoOperations(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	@Override
	public <S extends Product> S save(S entity) {

		this.mongoOperations.save(entity, PRODUCT_COLLECTION);

		return entity;
	}

	@Override
	public void delete(Product entity) {
		this.mongoOperations.remove(entity, PRODUCT_COLLECTION);
	}

	@Override
	public Product findOne(long id) {
		Product p = this.mongoOperations.findById(id, Product.class,
				PRODUCT_COLLECTION);
		return p;
	}

	@Override
	public void delete(long id) {
		Product p = this.mongoOperations.findById(id, Product.class,
				PRODUCT_COLLECTION);
		this.mongoOperations.remove(p, PRODUCT_COLLECTION);
	}

	@Override
	public List<Product> findAll() {
		List<Product> products = this.mongoOperations.findAll(Product.class,
				PRODUCT_COLLECTION);
		return products;
	}
	
}
