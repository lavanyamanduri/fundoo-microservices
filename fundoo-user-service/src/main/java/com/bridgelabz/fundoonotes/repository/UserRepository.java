package com.bridgelabz.fundoonotes.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.dto.ResetPassword;
import com.bridgelabz.fundoonotes.entity.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value = "insert into users ( name,password,email,mobileNumber,creationTime,isverified) values (?,?,?,?,?,?)", nativeQuery = true)
	void insertData(String name, String password, String email, String mobileNumber, LocalDateTime creationTime,
			boolean isverified);
 
	@Query(value = "select * from users where email=?", nativeQuery = true)
	User FindByEmail(String email);
	
	@Query(value = "select * from users where email=?", nativeQuery = true)
	Optional<User> FindUserByEmail(String email);

	@Query(value = "select * from users where email=?", nativeQuery = true)
	User checkByEmail(ResetPassword email);
	
	@Modifying
	@Transactional
	@Query(value = "update users set password=? where user_id=?", nativeQuery = true)
	void updateUserPassword(String password, Long id);

	@Query(value = "select * from users where user_id=?", nativeQuery = true)
	Optional<User> findbyId(Long userId);

	@Query(value = "update users set isverified = true where user_id = ?", nativeQuery = true)
	void updateIsVerified(Long id);

	@Query(value = "select * from users where user_id=?", nativeQuery = true)
	User findUserById(long userId);
	
}
