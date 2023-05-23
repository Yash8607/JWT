package com.example.demo.serviceImpl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UsersDto;
import com.example.demo.entities.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenHelper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {
	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users obj = userRepository.findByEmail(email);
		
		if(obj==null) {
			throw new UsernameNotFoundException("User Not Found " + email);
		}else {
			return new User(obj.getEmail(), obj.getPassword(), obj.getAuthorities()); 
		}
		
		
	}
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Users createUser(Users users) {
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		Users obj  = userRepository.save(users);
		return obj;
	}
	
	private void mapDto2ToEntity(UsersDto userDto2, Users user) {
		user.setEmail(userDto2.getEmail());
		user.setPassword(userDto2.getPassword());

	}

	private UsersDto mapEntityToDto2(Users user) {

		UsersDto responseDto = new UsersDto();

		responseDto.setEmail(user.getEmail());
		responseDto.setPassword(user.getPassword());

		return responseDto;

	}
@Autowired
private JwtTokenHelper jwtToken;
	public String validateUser(String email, String password) {
		// TODO Auto-generated method stub
		
		Users user = userRepository.findByEmail(email);
		
		UsersDto obj2 = mapEntityToDto2(user);
		if(obj2 != null && passwordEncoder.matches(password, obj2.getPassword())) {
			String newToken = jwtToken.generateToken(loadUserByUsername(obj2.getEmail()));  
			return newToken;
		}
		
		else {
			return "Invalid Credentials";
		}
	}
	

}
