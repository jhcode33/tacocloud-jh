package tacos.jwt.repository;

import org.springframework.data.repository.Repository;

import java.util.Optional;

import tacos.jwt.User;

public interface UserRepository extends Repository<User, Long> {

	Optional<User> findById(Long id);
	User save(User user);	
	Optional<User> findOneWithAuthoritiesByUsername(String username);
	void delete(User user);
}
