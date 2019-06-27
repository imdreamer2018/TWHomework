package hello.repository;

import hello.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface  UserRepository extends CrudRepository<User, Integer> {

    @Query(value = " select * from user where username= ?1 ",nativeQuery = true)
    List<User> findUser(String username);

}
