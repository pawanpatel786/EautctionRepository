package com.iiht.eauction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iiht.eauction.exception.UserNotFoundException;
import com.iiht.eauction.model.User;
import com.iiht.eauction.repository.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException());

		return UserDetailsImpl.build(user);
	}

}
