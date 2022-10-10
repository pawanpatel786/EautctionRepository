package com.iiht.eauction.seller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.iiht.eauction.seller.model.CustomResponse;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<CustomResponse> userNotFoundException(UserNotFoundException exception) {

		CustomResponse expModel = new CustomResponse(HttpStatus.NOT_FOUND.value(), "User not found in the system!!",
				"Error");

		return new ResponseEntity<>(expModel, HttpStatus.OK);

	}

	@ExceptionHandler(value = ProductNotFoundException.class)
	public ResponseEntity<CustomResponse> productNotFoundException(ProductNotFoundException exception) {

		CustomResponse expModel = new CustomResponse(HttpStatus.NOT_FOUND.value(), "Product not found in the system!!",
				"Error");

		return new ResponseEntity<>(expModel, HttpStatus.OK);

	}

	@ExceptionHandler(value = BidDateExpiredException.class)
	public ResponseEntity<CustomResponse> bidDateExpiredException(BidDateExpiredException exception) {

		CustomResponse expModel = new CustomResponse(HttpStatus.EXPECTATION_FAILED.value(),
				"This action can not be performed, As Bid end date is already expired!!", "Error");

		return new ResponseEntity<>(expModel, HttpStatus.OK);

	}

	@ExceptionHandler(value = BidNotFoundException.class)
	public ResponseEntity<CustomResponse> bidNotFoundException(BidNotFoundException exception) {

		CustomResponse expModel = new CustomResponse(HttpStatus.NOT_FOUND.value(), "Bid not found in the system!!",
				"Error");

		return new ResponseEntity<>(expModel, HttpStatus.OK);

	}

	@ExceptionHandler(value = BidsExistInProductException.class)
	public ResponseEntity<CustomResponse> bidsExistInProductException(BidsExistInProductException exception) {

		CustomResponse expModel = new CustomResponse(HttpStatus.EXPECTATION_FAILED.value(),
				"Can not delete this Product, As Bids are still associated with this product!!", "Error");

		return new ResponseEntity<>(expModel, HttpStatus.OK);

	}

	@ExceptionHandler(value = MoreThanOneBidByUserException.class)
	public ResponseEntity<CustomResponse> userNotFoundException(MoreThanOneBidByUserException exception) {

		CustomResponse expModel = new CustomResponse(HttpStatus.EXPECTATION_FAILED.value(),
				"User is not allowed to add more than one bid for the same product!!", "Error");

		return new ResponseEntity<>(expModel, HttpStatus.OK);

	}

	@ExceptionHandler(value = AccessDeniedException.class)
	public ResponseEntity<CustomResponse> accessDeniedException(AccessDeniedException exception) {

		CustomResponse expModel = new CustomResponse(HttpStatus.FORBIDDEN.value(),
				"User is not authorized to access this resource!!", "Error");

		return new ResponseEntity<>(expModel, HttpStatus.OK);

	}
}
