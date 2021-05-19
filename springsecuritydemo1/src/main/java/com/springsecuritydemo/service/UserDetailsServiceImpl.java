package com.springsecuritydemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springsecuritydemo.entity.AuthUser;
import com.springsecuritydemo.entity.User;
import com.springsecuritydemo.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
//		System.out.println(user.toString());
		//return user.map(AuthUser::new).orElseThrow(()-> new UsernameNotFoundException("User not found...!"));
		if(user != null)
			return new AuthUser(user);
		else
			throw new UsernameNotFoundException("User not found..!");
	}

}
