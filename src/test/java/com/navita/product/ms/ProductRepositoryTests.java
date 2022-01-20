package com.navita.product.ms;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import com.navita.product.ms.models.ProductModel;
import com.navita.product.ms.repositories.ProductRepository;

@DataJpaTest
@TestPropertySource("classpath:server-config-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository productRepository;

	@Test
	@Order(1)
	@Rollback(value = false)
	public void saveProduct() {

		ProductModel product = ProductModel.builder().name("XOLLA").description("CAMISETA").price(17.5).build();

		productRepository.save(product);

		Assertions.assertThat(product.getId()).isNotNull();
	}

	@Test
	@Order(2)
	public void getAListOfProducts() {

		List<ProductModel> products = productRepository.findAll();

		Assertions.assertThat(products.size()).isGreaterThan(0);

	}

	@Test
	@Order(3)
	@Rollback(value = false)
	public void updateEmployeeTest() {

		ProductModel product = productRepository.findAll().get(0);

		product.setName("JOSIAS");

		ProductModel productUpdated = productRepository.save(product);

		Assertions.assertThat(productUpdated.getName()).isEqualTo("JOSIAS");

	}

	@Test
	@Order(4)
	@Rollback(value = false)
	public void deleteEmployeeTest() {

		ProductModel product = productRepository.findByName("JOSIAS");

		productRepository.delete(product);

		ProductModel nullProduct = productRepository.findByName("JOSIAS");

		Assertions.assertThat(nullProduct).isNull();
	}

	@Test
	@Order(5)
	@Rollback(value = false)
	public void searchReturnEmptyList() {

		ProductModel product = ProductModel.builder().name("XOLLA").description("TOP").price(17.5).build();

		productRepository.save(product);

		List<ProductModel> products = productRepository.search("KFIKLF");

		Assertions.assertThat(products.size()).isEqualTo(0);
	}
}
