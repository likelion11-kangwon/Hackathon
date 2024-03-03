package back.ailion.controller;

import back.ailion.config.jwt.GetIdFromToken;
import back.ailion.model.dto.AiInfoResponseDto;
import back.ailion.model.dto.SingleResponseDto;
import back.ailion.model.dto.UserRcmAiDto;
import back.ailion.model.entity.AiInfo;
import back.ailion.service.RcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ailion")
public class RcmController {
    private final RcmService rcmService;

    @GetMapping("/api/top5")
    public SingleResponseDto top5AI(){
        List<AiInfoResponseDto> response = rcmService.top5AI();
        return new SingleResponseDto<>(response);
    }


    @GetMapping("/api/userRecommend")
    public SingleResponseDto userRecommend(@GetIdFromToken String username){
        List<UserRcmAiDto> response = rcmService.recommendAi(username);
        return new SingleResponseDto<>(response);
    }

    @GetMapping("/api/userRecommend")
    public SingleResponseDto userRecommend(){
        List<UserRcmAiDto> response = rcmService.recommendAi();
        return new SingleResponseDto<>(response);
    }
}
