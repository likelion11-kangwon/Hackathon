package back.ailion.controller;

import back.ailion.model.dto.ReplyDto;
import back.ailion.model.dto.request.ReplyDeleteDto;
import back.ailion.model.dto.request.ReplyRequestDto;
import back.ailion.model.dto.request.ReplyUpdateDto;
import back.ailion.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ailion/reply")
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;

    @PostMapping
    public ReplyDto saveReply(@Valid @RequestBody ReplyRequestDto replyRequestDto) {

        return replyService.saveReply(replyRequestDto);
    }

    @PatchMapping
    public ReplyDto updateReply(@Valid @RequestBody ReplyUpdateDto replyUpdateDto) {

        return replyService.updateReply(replyUpdateDto);
    }

    @DeleteMapping
    public boolean deleteReply(@Valid @RequestBody ReplyDeleteDto replyDeleteDto) {

        return replyService.deleteReply(replyDeleteDto);
    }

}