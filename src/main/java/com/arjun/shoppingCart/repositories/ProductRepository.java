package com.arjun.shoppingCart.repositories;

import java.util.List;

import com.arjun.shoppingCart.models.Product;

public interface ProductRepository {

	public Product findOne(long id);

	public void delete(Product entity);

	public List<Product> findAll();

	public <S extends Product> S save(S entity);
	
	public void delete(long id);
}
