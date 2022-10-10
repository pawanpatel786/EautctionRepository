package com.iiht.eauction.seller.controller;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.eauction.seller.exception.AccessDeniedException;
import com.iiht.eauction.seller.exception.UserNotFoundException;
import com.iiht.eauction.seller.model.CustomResponse;
import com.iiht.eauction.seller.model.Product;
import com.iiht.eauction.seller.model.User;
import com.iiht.eauction.seller.repository.ProductRepository;
import com.iiht.eauction.seller.service.ProductService;
import com.iiht.eauction.seller.service.UserDetailsImpl;
import com.iiht.eauction.seller.service.UserService;
import com.iiht.eauction.seller.util.Utility;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/seller")
public class SellerController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	ProductRepository productRepo;

	@Autowired
	ProductService productService;

	@PostMapping("/addProduct")
	@PreAuthorize("hasRole('ROLE_SELLER')")
	public ResponseEntity<?> addProduct(@Valid @RequestBody Product productRequest, Authentication authentication)
			throws ParseException {
		UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

		User user = userService.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException());
		productRequest.setUser(user);
		productRequest.setId(null);
		productRequest.setBidEndDate(Utility.convertingTimestampToDate(productRequest.getBidEndDate()));
		productRepo.save(productRequest);
		CustomResponse response = new CustomResponse(HttpStatus.OK.value(), "Product addedd successfully!", "Success");

		return ResponseEntity.ok(response);
	}

	@GetMapping("/getProducts")
	public ResponseEntity<?> getProducts(Authentication authentication) {
		UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
		List<Product> productList = productRepo.findAll();
		productService.updatedProductList(productList, principal.getId());
		return ResponseEntity.ok(productList);
	}

//	@PreAuthorize("hasRole('ROLE_SELLER')")
//	@RequestMapping(method = RequestMethod.GET, value = "/hello")
	@DeleteMapping("/deleteProduct")
	public ResponseEntity<?> deleteProduct(@RequestParam("productId") String productId) {

		boolean deleteBidFromProduct = productService.deleteProduct(productId);
		CustomResponse response = null;
		if (deleteBidFromProduct) {

			response = new CustomResponse(HttpStatus.OK.value(), "Product deleted successfully!!", "Success");
		} else {
			response = new CustomResponse(HttpStatus.OK.value(), "Error in deleting bid from Product!!", "Error");

		}

		return ResponseEntity.ok(response);

	}

	@PostMapping("/403")
	public ResponseEntity<?> getBidDetails() {
		throw new AccessDeniedException();
	}
}
