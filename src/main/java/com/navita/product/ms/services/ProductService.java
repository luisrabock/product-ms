package com.navita.product.ms.services;

import com.navita.product.ms.models.ProductModel;
import com.navita.product.ms.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;

	public ProductModel create(ProductModel productModel) {
		return productRepository.save(productModel);
	}

	public Page<ProductModel> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	public Optional<ProductModel> findById(UUID id) {
		return productRepository.findById(id);
	}

	public void deleteById(UUID id) {
		productRepository.deleteById(id);
	}

	public ProductModel save(ProductModel productModel) {
		return productRepository.save(productModel);
	}

	public List<ProductModel> search(String keyword) {
		return productRepository.search(keyword);
	}

}
