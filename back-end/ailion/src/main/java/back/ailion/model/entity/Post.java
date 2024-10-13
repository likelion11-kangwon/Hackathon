package back.ailion.model.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private String writer;

    private boolean delCheck;

    private String category;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @ColumnDefault("0")
    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    @ColumnDefault("0")
    @Column(name = "view_count",nullable = false)
    private Integer viewCount;

    @ColumnDefault("0")
    @Column(name = "comment_count",nullable = false)
    private Integer commentCount;

    @Builder
    public Post(String title, String content, String writer, User user, Integer likeCount,
                Integer viewCount, Integer commentCount, boolean delCheck, String category) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.user = user;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.delCheck = delCheck;
        this.category = category;
    }

    public void delete() {
        this.delCheck = true;
    }

    public void viewCountUp(Post post) {
        post.viewCount++;
    }

    // 연관관계 편의 메소드
//    public void addComment(Comment comment) {
//        this.comments.add(comment);
//        if (comment.getPost() != this) {
//            comment.changePost(this);
//        }
//    }
}