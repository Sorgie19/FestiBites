package com.festibites.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses = {UserRepository.class, ItemRepository.class, CategoryRepository.class, LocationRepository.class, OperatingHoursRepository.class, ShopRepository.class})
public class FbMerchentRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(FbMerchentRestApplication.class, args);
	}

}
