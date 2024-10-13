package back.ailion.controller;

import back.ailion.model.dto.AiInfoResponseDto;
import back.ailion.model.dto.MainPageDto;
import back.ailion.model.entity.AiInfo;
import back.ailion.repository.AiInfoRepository;
import back.ailion.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ailion")
@RequiredArgsConstructor
public class MainPageApiController {

    private final MainPageService mainPageService;
    private final AiInfoRepository aiInfoRepository;

    @GetMapping("/api/main/page")
    public MainPageDto getMainPage(@RequestParam(value="page", defaultValue="0") int page) {

        return mainPageService.getMainPage(page);
    }
}