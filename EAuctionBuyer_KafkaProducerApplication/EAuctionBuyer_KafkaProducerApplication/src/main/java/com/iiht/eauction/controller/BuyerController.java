package com.iiht.eauction.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.eauction.exception.AccessDeniedException;
import com.iiht.eauction.kafka.config.KafkaProducer;
import com.iiht.eauction.model.BidModel;
import com.iiht.eauction.model.CustomResponse;
import com.iiht.eauction.repository.ProductRepository;
import com.iiht.eauction.service.ProductService;
import com.iiht.eauction.service.UserDetailsImpl;
import com.iiht.eauction.service.UserService;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/buyer")
public class BuyerController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	ProductRepository productRepo;

	@Autowired
	ProductService productService;

	

	@PostMapping("/addBid")
	@PreAuthorize("hasRole('ROLE_BUYER')")
	public ResponseEntity<?> addBidToProduct(@Valid @RequestBody BidModel bidRequest, Authentication authentication) {
		bidRequest.setId(null);
		UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
		bidRequest.setUserId(principal.getId());
		boolean isBidValid = productService.bidValidationForProduct(bidRequest);
		CustomResponse response = null;
		if (isBidValid) {

			response = new CustomResponse(HttpStatus.OK.value(), "Bid added successfully!!", "Success");
		} else {
			response = new CustomResponse(HttpStatus.OK.value(), "Error in adding bid to product!!", "Error");

		}

		return ResponseEntity.ok(response);
	}

	@GetMapping("/getBidDetails")
	public ResponseEntity<?> getBidDetails(@RequestParam("productId") String productId) {
		List<BidModel> allBidOfProduct = productService.getAllBidOfProduct(productId);
		return ResponseEntity.ok(allBidOfProduct);
	}

	@GetMapping("deleteBid")
	@PreAuthorize("hasRole('ROLE_BUYER')")
	public ResponseEntity<?> deleteBidFromProduct(@RequestParam("bidId") String bidId,
			@RequestParam("productId") String productId) {

		boolean deleteBidFromProduct = productService.deleteBidFromProduct(productId, bidId);
		CustomResponse response = null;
		if (deleteBidFromProduct) {

			response = new CustomResponse(HttpStatus.OK.value(), "Bid deleted successfully!!", "Success");
		} else {
			response = new CustomResponse(HttpStatus.OK.value(), "Error in deleting bid from product!!", "Error");

		}

		return ResponseEntity.ok(response);

	}

	@PostMapping("/updateBid")
	@PreAuthorize("hasRole('ROLE_BUYER')")
	public ResponseEntity<?> updateBid(@Valid @RequestBody BidModel bidRequest) {
		boolean updateBidToProduct = productService.updateBidToProduct(bidRequest);
		CustomResponse response = null;
		if (updateBidToProduct) {

			response = new CustomResponse(HttpStatus.OK.value(), "Bid updated successfully!!", "Success");
		} else {
			response = new CustomResponse(HttpStatus.OK.value(), "Error in updating bid to product!!", "Error");

		}

		return ResponseEntity.ok(response);
	}

	@PostMapping("/403")
	public ResponseEntity<?> getBidDetails() {
		throw new AccessDeniedException();
	}

//	@GetMapping("/publish")
//	public ResponseEntity<String> publish(@RequestParam("message") String message) {
//		kafkaProducer.sendMessage(message);
//		return ResponseEntity.ok("Message sent to kafka topic");
//	}
}
