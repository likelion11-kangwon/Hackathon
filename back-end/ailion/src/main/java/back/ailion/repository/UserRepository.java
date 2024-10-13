package back.ailion.repository;

import back.ailion.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findOneWithAuthoritiesByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u.posts FROM User u where u.id = :userId")
    List<Post> findPostsById(@Param("userId") Long userId);

    @Query("SELECT u.hearts FROM User u where u.id = :userId")
    List<Heart> findHeartsById(@Param("userId") Long userId);

    @Query("SELECT u.favorites FROM User u where u.id = :userId")
    List<Favorite> findFavoritesById(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User u SET u.nickname = :nickname WHERE u.username = :username")
    User setNickname(@Param("nickname") String nickname, @Param("username") String username);

    @Modifying
    @Query("UPDATE User u SET u.email = :email WHERE u.username = :username")
    User setEmail(@Param("email") String email, @Param("username") String username);

    @Modifying
    @Query("UPDATE User u SET u.date = :date WHERE u.username = :username")
    User setDate(@Param("date") Date date, @Param("username") String username);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.username = :username")
    User setPassword(@Param("password") String password, @Param("username") String username);

    @Query(value = "select count(h.post_id) as userLike from Post p inner join Heart h on p.post_id = h.post_id where p.user_id = :userId and p.post_id = h.post_id", nativeQuery=true)
    int findHeartCountById(@Param("userId") Long userId);
}
