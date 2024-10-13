package back.ailion.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private String writer;

    private boolean delCheck;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<Reply> replies = new ArrayList<>();

    @Builder
    public Comment(String content, String writer, User user, Post post, boolean delCheck) {
        this.content = content;
        this.writer = writer;
        this.user = user;
        // this.post = post;
        if (post != null) {
            changePost(post);
        }
        this.delCheck = delCheck;
    }

    public void modifyContent(String content) {
        this.content = content;
    }

    public void delete() {
        this.delCheck = true;
    }

    // 연관관계 편의 메소드
    public void changePost(Post post) {
        // Comment에 이미 post가 설정되어 있을 경우
        if(this.post != null) {

            // post에서 해당 Entity를 제거
            this.post.getComments().remove(this);
        }

        // 해당 Comment Entity에 파라미터로 들어온 post 연관 관계 설정
        this.post = post;

        // 파라미터로 들어온 post Entity에 Comment 연관 관계 설정
        post.getComments().add(this);
    }

    // 연관관계 편의 메소드
//    public void addCommentReply(CommentReply commentReplies) {
//        this.commentReplies.add(commentReplies);
//        if (commentReplies.getComment() != this) {
//            commentReplies.changeComment(this);
//        }
//    }
}