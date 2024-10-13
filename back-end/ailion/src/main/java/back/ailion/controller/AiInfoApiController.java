package back.ailion.controller;

import back.ailion.model.entity.AiInfo;
import back.ailion.service.AiInfoService;
import back.ailion.model.dto.request.AddAiInfoRequest;
import back.ailion.model.dto.AiInfoResponseDto;
import back.ailion.model.dto.request.UpdateAiInfoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ailion")
public class AiInfoApiController {

    private final AiInfoService aiInfoService;

    // 전체 조회
    @GetMapping("/api/aiInfo")
    public ResponseEntity<List<AiInfoResponseDto>> findAllAiInfo() {
        List<AiInfoResponseDto>  aiInfo = aiInfoService.findAll()
                .stream()
                .map(AiInfoResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(aiInfo);

    }
    //카테고리별 조회
    @GetMapping("/api/aiInfo/category/{category}")
    public ResponseEntity<List<AiInfoResponseDto>> findAllByCategory(@PathVariable String category){
        List<AiInfoResponseDto> aiInfo = aiInfoService.findByCategory(category)
                .stream()
                .map(AiInfoResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(aiInfo);
    }

    //---관리자 계정으로 들어오면 ---이건 나중에 사용.
    //단건 조회
    @GetMapping("/api/aiInfo/{id}")
    public ResponseEntity<AiInfoResponseDto> findAiInfo(@PathVariable Long id){
        return ResponseEntity.ok().body(new AiInfoResponseDto(aiInfoService.findById(id)));
    }

    //새로운 aiInfo 등록
    @PostMapping("/api/latest/aiInfo")
    public ResponseEntity<AiInfo> addAiInfo(@RequestBody AddAiInfoRequest requestDto){
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(aiInfoService.save(requestDto));
    }
    //aiInfo 수정
    @PutMapping("/api/latest/aiInfo/{id}")
    public ResponseEntity<AiInfo> updateAiInfo(@PathVariable Long id, @RequestBody UpdateAiInfoRequestDto requestDto) {
        AiInfo updatedAiInfo = aiInfoService.update(id,requestDto);

        return ResponseEntity.ok().body(updatedAiInfo);
    }
    //aiInfo 삭제
    @DeleteMapping("/api/latest/aiInfo/{id}")
    public ResponseEntity<Void> deleteAiInfo(@PathVariable Long id){
        aiInfoService.delete(id);
        return ResponseEntity.ok().build();

    }

}

