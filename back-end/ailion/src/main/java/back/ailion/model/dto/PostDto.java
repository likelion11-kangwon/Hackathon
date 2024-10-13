package back.ailion.model.dto;

import back.ailion.model.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long userId;
    private Long postId;

    private String title;
    private String content;
    private String writer;
    private String category;

    private Integer likeCount;
    private Integer viewCount;
    private Integer commentCount;

    private boolean delCheck;

    private LocalDateTime createdDate;

    private List<CommentDto> comments;

    public PostDto(Post post) {
        this.postId = post.getId();
        this.createdDate = post.getCreatedDate();
        this.userId = post.getUser().getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter();
        this.likeCount = post.getLikeCount();
        this.viewCount = post.getViewCount();
        this.commentCount = post.getCommentCount();
        this.delCheck = post.isDelCheck();
        this.category = post.getCategory();
    }
}