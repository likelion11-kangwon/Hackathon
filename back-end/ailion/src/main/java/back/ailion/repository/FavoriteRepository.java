package back.ailion.repository;

import back.ailion.model.entity.*;
import back.ailion.model.global.Star;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByUserAndAiInfo(User user, AiInfo aiInfo);

//    @Query(value = "SELECT f.ai_info_id as id, count(f.ai_info_id) as stars from Favorite f group by f.ai_info_id order by stars desc limit 5", nativeQuery = true)
//    public List<Star> top5AI();

    Optional<Favorite> findByUserIdAndAiInfoId(Long userId, Long aiInfoId);
}
