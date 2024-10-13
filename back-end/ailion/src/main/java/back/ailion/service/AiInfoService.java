package back.ailion.service;

import back.ailion.model.entity.AiInfo;
import back.ailion.repository.AiInfoRepository;
import back.ailion.model.dto.request.AddAiInfoRequest;
import back.ailion.model.dto.request.UpdateAiInfoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AiInfoService {

    private final AiInfoRepository aiInfoRepository;


    //전체 조회
    public List<AiInfo> findAll() {
        return aiInfoRepository.findAll();
    }

    //카테고리별 조회
    public List<AiInfo> findByCategory(String category){
        return aiInfoRepository.findByCategory(category);
    }




    //단건 조회
    public AiInfo findById(long id){
        return aiInfoRepository.findById(id).orElseThrow(()->new IllegalArgumentException("AiInfo not exist ! : "+id));
    }

    //수정
    @Transactional
    // step 1. 2.가 모두 실행 되어야 작업이 끝남.
    public AiInfo update(Long id, UpdateAiInfoRequestDto requestDto) {
        // step 1. 기존 등록된 글을 가져옴
        AiInfo aiInfo = aiInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("aiInfo not exist! : " + id));

        // step 2. 원하는 제목과 내용을 수정함
        aiInfo.update(requestDto.getName(), requestDto.getContent(), requestDto.getUrl(), requestDto.getCategory());
        return  aiInfo;
    }

    //저장
    public AiInfo save(AddAiInfoRequest requestDto){
        return aiInfoRepository.save(requestDto.toEntity());
    }
    //삭제
    public void delete(Long id) {
        aiInfoRepository.deleteById(id);
    }
}
