package back.ailion.service;

import back.ailion.config.jwt.GetIdFromToken;
import back.ailion.exception.BaseExceptionCode;
import back.ailion.exception.custom.NotFoundException;
import back.ailion.model.dto.AiInfoResponseDto;
import back.ailion.model.dto.UserRcmAiDto;
import back.ailion.model.entity.AiInfo;
import back.ailion.model.entity.Recommend;
import back.ailion.model.entity.User;
import back.ailion.model.global.Star;
import back.ailion.model.global.StarDto;
import back.ailion.repository.AiInfoRepository;
import back.ailion.repository.FavoriteRepository;
import back.ailion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RcmService {
    private final AiInfoRepository aiInfoRepository;
    private final UserRepository userRepository;

    public List<AiInfoResponseDto> top5AI() {
        List<AiInfo> aiInfoIds = aiInfoRepository.top5AI();

        List<AiInfoResponseDto> aiInfos = new ArrayList<>();

        for(AiInfo aiInfo : aiInfoIds){
            aiInfos.add(new AiInfoResponseDto(aiInfo));
        }

        return aiInfos;
    }

    public List<UserRcmAiDto> recommendAi(String uid){
        User user = userRepository.findByUsername(uid).orElseThrow(
                () -> new NotFoundException(BaseExceptionCode.USER_NOT_FOUND));

        List<Recommend> recommendList = user.getRecommends();

        if(recommendList.size() == 0){
            return recommendAi();
        }
        else {
            List<UserRcmAiDto> userRcmAiDtos = new ArrayList<>();

            List<AiInfoResponseDto> aiInfoList;

            for (Recommend recommend : recommendList) {
                aiInfoList = new ArrayList<>();
                List<AiInfo> aiInfos = aiInfoRepository.findRecommendList(recommend.toString());

                for (AiInfo aiInfo : aiInfos) {
                    aiInfoList.add(new AiInfoResponseDto(aiInfo));
                }

                userRcmAiDtos.add(new UserRcmAiDto(recommend.toString(), aiInfoList));
            }

            return userRcmAiDtos;
        }
    }

    public List<UserRcmAiDto> recommendAi(){
        List<UserRcmAiDto> userRcmAiDtos = new ArrayList<>();

        List<AiInfoResponseDto> aiInfoList;

        for(Recommend recommend : Recommend.values()){
            aiInfoList = new ArrayList<>();
            List<AiInfo> aiInfos = aiInfoRepository.findRecommendList(recommend.toString());

            for (AiInfo aiInfo : aiInfos) {
                aiInfoList.add(new AiInfoResponseDto(aiInfo));
            }

            userRcmAiDtos.add(new UserRcmAiDto(recommend.toString(), aiInfoList));
        }

        return userRcmAiDtos;
    }
}
