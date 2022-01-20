package com.navita.product.ms.consumers;

import com.navita.product.ms.dtos.ProductDto;
import com.navita.product.ms.models.ProductModel;
import com.navita.product.ms.services.ProductService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {

	@Autowired
	ProductService productService;

	@RabbitListener(queues = "${spring.rabbitmq.queue}")
	public void listen(@Payload ProductDto productDto) {
		ProductModel productModel = ProductModel.builder().build();
		BeanUtils.copyProperties(productDto, productModel);
		productService.create(productModel);
	}
}