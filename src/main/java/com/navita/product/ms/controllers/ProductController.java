package com.navita.product.ms.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import com.navita.product.ms.ResourceBase;
import com.navita.product.ms.dtos.ProductDto;
import com.navita.product.ms.models.ProductModel;
import com.navita.product.ms.services.ProductService;

@RestController
@RequestMapping({ "/products" })
public class ProductController extends ResourceBase {

	@Autowired
	ProductService productService;

	@PostMapping
	public ResponseEntity<ProductModel> create(@RequestBody @Valid ProductDto productDto) {
		ProductModel productModel = ProductModel.builder().build();
		BeanUtils.copyProperties(productDto, productModel);
		productService.create(productModel);
		return new ResponseEntity<>(productModel, HttpStatus.CREATED);
	}

	@PutMapping(path = { "/{id}" })
	public ResponseEntity<ProductModel> update(@RequestBody @Valid ProductDto productDto,
			@PathVariable(value = "id") UUID id) {
		Optional<ProductModel> productModel = productService.findById(id);
		if (productModel.isPresent()) {
			ProductModel productModelUpdated = ProductModel.builder().build();
			productDto.setId(id);
			BeanUtils.copyProperties(productDto, productModelUpdated);
			return new ResponseEntity<>(productService.save(productModelUpdated), HttpStatus.CREATED);

		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping
	public ResponseEntity<Page<ProductModel>> findAll(
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		return new ResponseEntity<>(productService.findAll(pageable), HttpStatus.OK);
	}

	@GetMapping(path = { "/{id}" })
	public ResponseEntity<ProductModel> findById(@PathVariable(value = "id") UUID id) {
		Optional<ProductModel> productModel = productService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(productModel.get());
	}

	@GetMapping(path = { "/search" })
	public ResponseEntity<List<ProductModel>> search(@Param("keyword") String keyword) {
		List<ProductModel> listProducts = productService.search(keyword);
		return ResponseEntity.status(HttpStatus.OK).body(listProducts);
	}

	@DeleteMapping(path = { "/{id}" })
	public void deleteById(@PathVariable(value = "id") UUID id) {
		productService.deleteById(id);
	}

}
