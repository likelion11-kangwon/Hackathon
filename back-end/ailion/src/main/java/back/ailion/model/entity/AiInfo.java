package back.ailion.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ai_info_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "url")
    private String url;

    @Column(name = "category")
    private String category;

    private String imageUrl;

    @ColumnDefault("0")
    @Column(name = "favorite_count",nullable = false)
    private Integer favoriteCount;

    @Builder
    public AiInfo(String name, String content, String url, String category) {
        this.name = name;
        this.content = content;
        this.url = url;
        this.category = category;
    }

    public void update(String name, String content, String url, String category) {
        this.name = name;
        this.content = content;
        this.url = url;
        this.category = category;
    }
}
