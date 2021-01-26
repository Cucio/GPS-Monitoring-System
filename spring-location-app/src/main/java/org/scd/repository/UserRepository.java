package org.scd.repository;

import org.scd.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * User Repository
 */
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Find user by email
     *
     * @param email - unique email address
     * @return
     */

    @Modifying
    @Query(value = "INSERT INTO parole (email,password,user_id) VALUES (:mail,:parola,:id)",nativeQuery = true)
    @Transactional
    void insertValue(@Param("mail") String email , @Param("parola") String parola, @Param("id") long id);

    @Modifying
    @Query(value = "SELECT * FROM users",nativeQuery = true)
    @Transactional
    List<Integer> getId();

    @Modifying
    @Query(value = "Delete from users u where u.id =?1",nativeQuery = true)
    @Transactional
    void deleteUser(long id);

    @Modifying
    @Query(value = "INSERT INTO users (email,first_name,last_name,password) VALUES (1,1,1,1)",nativeQuery = true)
    @Transactional
    void insertRandomValue();

    @Modifying
    @Query(value = "Delete from user_role u where u.user_id =?1",nativeQuery = true)
    @Transactional
    void deleteUserRole(long id);

    @Modifying
    @Query(value = "Update Users u set email =:email, first_name = :firstName, last_name =:lastName, password = :password where u.id = :id", nativeQuery = true)
    @Transactional
    void updateUser(@Param("id") long id, @Param("email") String email, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("password") String password);

    @Modifying
    @Query(value = "DELETE from user_location u where u.user_id = ?1", nativeQuery = true)
    @Transactional
    void deleteUserLocation(long id);

    User findByEmail(final String email);

    User save(User user);

    User findById(long id);


}