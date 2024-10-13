package back.ailion.controller;

import back.ailion.model.dto.CommentDto;
import back.ailion.model.dto.request.CommentDeleteDto;
import back.ailion.model.dto.request.CommentRequestDto;
import back.ailion.model.dto.request.CommentUpdateDto;
import back.ailion.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ailion/comments")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping
    public CommentDto saveComment(@Valid @RequestBody CommentRequestDto commentRequestDto) {

        return commentService.saveComment(commentRequestDto);
    }

    @PatchMapping
    public CommentDto updateComment(@Valid @RequestBody CommentUpdateDto commentUpdateDto) {

        return commentService.updateComment(commentUpdateDto);
    }

    @DeleteMapping
    public boolean deleteComment(@Valid @RequestBody CommentDeleteDto commentDeleteDto) {

        return commentService.deleteComment(commentDeleteDto);
    }

}