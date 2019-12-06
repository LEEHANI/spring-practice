package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;


//@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
//	private Map<String, User> users = new HashMap<>();
//	private Random random = new Random();
	
//	public User save(User user)
//	{
//		user.setSeq(random.nextLong());
//		users.put(user.getEmail(), user);
//		
//		return user;
//	}
//	
//	public User findByEmail(String username)
//	{
//		return users.get(username);
//	}
	
	public Optional<User> findByEmail(String email);
}
