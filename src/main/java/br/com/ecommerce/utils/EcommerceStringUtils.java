package br.com.ecommerce.utils;

public class EcommerceStringUtils {

	private EcommerceStringUtils() {
		
	}
	
	public static boolean isBlank(String str) {

		return (str == null || str.isBlank());
	}
}
