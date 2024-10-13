package back.ailion.service;

import back.ailion.exception.BaseExceptionCode;
import back.ailion.exception.custom.NotFoundException;
import back.ailion.model.dto.CommentDto;
import back.ailion.model.dto.request.CommentDeleteDto;
import back.ailion.model.dto.request.CommentRequestDto;
import back.ailion.model.dto.request.CommentUpdateDto;
import back.ailion.model.entity.Comment;
import back.ailion.model.entity.User;
import back.ailion.model.entity.Post;
import back.ailion.repository.CommentRepository;
import back.ailion.repository.UserRepository;
import back.ailion.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private CommentDto CommentToCommentDto(Comment comment) {
        return new CommentDto(comment);
    }

    @Transactional
    public CommentDto saveComment(CommentRequestDto commentRequestDto) {

        User user = userRepository.findById(commentRequestDto.getUserId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.USER_NOT_FOUND));

        Post post = postRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.POST_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .writer(user.getNickname())
                .user(user)
                .post(post)
                .delCheck(false)
                .build();

        post.setCommentCount(post.getCommentCount() + 1);
        return CommentToCommentDto(commentRepository.save(comment));
    }

    @Transactional
    public CommentDto updateComment(CommentUpdateDto commentUpdateDto) {

        Comment comment = commentRepository.findById(commentUpdateDto.getCommentId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.COMMENT_NOT_FOUND));

        comment.modifyContent(commentUpdateDto.getContent());

        return CommentToCommentDto(commentRepository.findById(comment.getId()).get());
    }

    @Transactional
    public boolean deleteComment(CommentDeleteDto commentDeleteDto) {

        Comment comment = commentRepository.findById(commentDeleteDto.getCommentId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.COMMENT_NOT_FOUND));

        Post post = postRepository.findById(commentDeleteDto.getPostId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.POST_NOT_FOUND));
        post.setCommentCount(post.getCommentCount() - 1);

        if (commentRepository.countRepliesByCommentId(commentDeleteDto.getCommentId()) > 0) {

            comment.delete();
            return true;
        }

        commentRepository.delete(comment);
        return true;
    }

}