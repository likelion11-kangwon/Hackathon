package back.ailion.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String content;

    private String writer;

    private boolean delCheck;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Builder
    public Reply(String content, String writer, User user, Comment comment, boolean delCheck) {
        this.content = content;
        this.writer = writer;
        this.user = user;
        this.delCheck = delCheck;
        // this.comment = comment;
        if (comment != null) {
            changeComment(comment);
        }
    }

    public void modifyContent(String content) {
        this.content = content;
    }

    // 연관관계 편의 메소드
    public void changeComment(Comment comment) {

        if(this.comment != null) {

            this.comment.getReplies().remove(this);
        }

        this.comment = comment;
        comment.getReplies().add(this);
    }
}