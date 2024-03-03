package back.ailion.repository;

import back.ailion.model.entity.AiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AiInfoRepository extends JpaRepository<AiInfo, Long> {

    @Modifying
    @Query("UPDATE AiInfo a SET a.favoriteCount = a.favoriteCount + 1 WHERE a = :selectedAiInfo")
    void addFavoriteCount(@Param("selectedAiInfo") AiInfo aiInfo);

    @Modifying
    @Query("UPDATE AiInfo a SET a.favoriteCount = a.favoriteCount - 1 WHERE a = :selectedAiInfo")
    void subFavoriteCount(@Param("selectedAiInfo") AiInfo aiInfo);

    @Query(value = "SELECT ai FROM AiInfo ai order by ai.favorite_count desc limit 5", nativeQuery = true)
    List<AiInfo> top5AI();

    AiInfo findByName(String name);

    List<AiInfo> findByCategory(String category);
    // 페이지는 나중에 구현
    // Page<AiInfo> findByCategory(String category, Pageable pageable);

    AiInfo findAiInfoById(Long id);

    @Query(value = "SELECT ai FROM AiInfo ai WHERE ai.category = :category order by ai.name desc, rand() limit 3", nativeQuery = true)
    List<AiInfo> findRecommendList(String category);

}
