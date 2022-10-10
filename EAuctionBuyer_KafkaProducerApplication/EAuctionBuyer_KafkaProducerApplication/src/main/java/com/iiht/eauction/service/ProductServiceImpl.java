package com.iiht.eauction.service;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.eauction.exception.BidDateExpiredException;
import com.iiht.eauction.exception.BidNotFoundException;
import com.iiht.eauction.exception.BidsExistInProductException;
import com.iiht.eauction.exception.MoreThanOneBidByUserException;
import com.iiht.eauction.exception.ProductNotFoundException;
import com.iiht.eauction.exception.UserNotFoundException;
import com.iiht.eauction.kafka.config.KafkaProducer;
import com.iiht.eauction.model.BidModel;
import com.iiht.eauction.model.Product;
import com.iiht.eauction.model.User;
import com.iiht.eauction.repository.BidsRepository;
import com.iiht.eauction.repository.ProductRepository;
import com.iiht.eauction.repository.UserRepository;
import com.iiht.eauction.util.Utility;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	BidsRepository bidRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	private KafkaProducer kafkaProducer;

	@Override
	public List<Product> updatedProductList(List<Product> list, String loggedUserId) {

		for (Product product : list) {

			if (product.getUser().getId().equals(loggedUserId)) {
				product.setIsDeleted("Y");
			}

		}
		return list;
	}

	@Override
	public boolean bidValidationForProduct(BidModel bidRequestModel) {
		Optional<Product> productOptional = productRepository.findById(bidRequestModel.getProductId());
		if (productOptional.isEmpty()) {
			throw new ProductNotFoundException();
		} else {
			Product product = productOptional.get();
			try {
				if (Utility.isBidEndDateIsExpired(product.getBidEndDate())) {
					throw new BidDateExpiredException();
				} else {
					List<BidModel> bids = product.getBids();
					for (BidModel bidModel : bids) {
						if (bidModel.getUserId().equals(bidRequestModel.getUserId())) {
							throw new MoreThanOneBidByUserException();
						}
					}
					return kafkaProducer.sendMessage(bidRequestModel);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;

	}

	@Override
	public List<BidModel> getAllBidOfProduct(String productId) {
		// TODO Auto-generated method stub

		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (optionalProduct.isEmpty()) {
			throw new ProductNotFoundException();
		} else {
			Product product = optionalProduct.get();
			List<BidModel> bids = product.getBids();
			for (BidModel bidModel : bids) {
				Optional<User> findByIdUser = userRepository.findById(bidModel.getUserId());
				if (findByIdUser.isEmpty()) {
					throw new UserNotFoundException();
				} else {
					bidModel.setEmail(findByIdUser.get().getEmail());
					bidModel.setUserName(findByIdUser.get().getFirstName());
				}
			}
			Collections.sort(bids);
			return bids;
		}

	}

	@Override
	public boolean deleteBidFromProduct(String productId, String bidId) {
		// TODO Auto-generated method stub
		Optional<BidModel> optionalBid = bidRepository.findById(bidId);
		Optional<Product> optionalProduct = productRepository.findById(productId);

		if (optionalBid.isEmpty()) {
			throw new BidNotFoundException();

		} else {
			Product product = optionalProduct.get();
			List<BidModel> bids = product.getBids();
			CopyOnWriteArrayList<BidModel> concurrentList = new CopyOnWriteArrayList<>(bids);
			for (BidModel bidModel : concurrentList) {
				if (bidModel.getId().equals(bidId)) {
					concurrentList.remove(bidModel);
				}
			}
			product.setBids(concurrentList);
			BidModel bidModel = optionalBid.get();

			if (bidModel.getProductId().equals(productId)) {
				bidRepository.deleteById(bidId);
				productRepository.save(product);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteProduct(String productId) {
		// TODO Auto-generated method stub
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (optionalProduct.isEmpty()) {
			throw new ProductNotFoundException();
		} else {

			Product product = optionalProduct.get();
			List<BidModel> bids = product.getBids();
			if (!bids.isEmpty()) {

				throw new BidsExistInProductException();
			} else {

				try {
					if (Utility.isBidEndDateIsExpired(product.getBidEndDate())) {
						throw new BidDateExpiredException();
					} else {
						productRepository.deleteById(productId);
						return true;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		return false;
	}

	@Override
	public boolean updateBidToProduct(BidModel bidRequestModel) {
		// TODO Auto-generated method stub

		Optional<BidModel> optionalBidModel = bidRepository.findById(bidRequestModel.getId());
		Optional<Product> optionProduct = productRepository.findById(bidRequestModel.getProductId());
		if (optionProduct.isEmpty()) {
			throw new ProductNotFoundException();

		} else if (optionalBidModel.isEmpty()) {
			throw new BidNotFoundException();
		} else {

			try {
				if (Utility.isBidEndDateIsExpired(optionProduct.get().getBidEndDate())) {
					throw new BidDateExpiredException();
				}
				BidModel bidModel = optionalBidModel.get();
				bidModel.setBidPrice(bidRequestModel.getBidPrice());
				bidRepository.save(bidModel);
				return true;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

}
