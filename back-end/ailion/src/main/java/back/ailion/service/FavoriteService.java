package back.ailion.service;

import back.ailion.exception.BaseExceptionCode;
import back.ailion.exception.custom.AlreadyExecutedException;
import back.ailion.exception.custom.NotFoundException;
import back.ailion.model.dto.FavoriteDto;
import back.ailion.model.dto.HeartDto;
import back.ailion.model.dto.request.FavoriteDeleteDto;
import back.ailion.model.dto.request.FavoriteRequestDto;
import back.ailion.model.dto.request.HeartRequestDto;
import back.ailion.model.entity.*;
import back.ailion.repository.AiInfoRepository;
import back.ailion.repository.FavoriteRepository;
import back.ailion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final AiInfoRepository aiInfoRepository;

    private FavoriteDto FavoriteToFavoriteDto(Favorite favorite) {
        return new FavoriteDto(favorite);
    }

    @Transactional
    public FavoriteDto favorite(FavoriteRequestDto favoriteRequestDto) {

        User user = userRepository.findById(favoriteRequestDto.getUserId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.USER_NOT_FOUND));

        AiInfo aiInfo = aiInfoRepository.findById(favoriteRequestDto.getAiInfoId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.AI_INFO_NOT_FOUND));

        // 이미 즐겨찾기 되어있으면 오류 반환
        if (favoriteRepository.findByUserAndAiInfo(user, aiInfo).isPresent()){

            throw new AlreadyExecutedException(BaseExceptionCode.ALREADY_FAVORITE);
        }

        Favorite favorite = Favorite.builder()
                .user(user)
                .aiInfo(aiInfo)
                .build();

        aiInfoRepository.addFavoriteCount(aiInfo);
        return FavoriteToFavoriteDto(favoriteRepository.save(favorite));
    }

    @Transactional
    public boolean cancelFavorite(FavoriteDeleteDto favoriteDeleteDto) {

        Favorite favorite = favoriteRepository.findById(favoriteDeleteDto.getFavoriteId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.FAVORITE_NOT_FOUND));

        AiInfo aiInfo = aiInfoRepository.findById(favoriteDeleteDto.getAiInfoId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.AI_INFO_NOT_FOUND));

        favoriteRepository.delete(favorite);
        aiInfoRepository.subFavoriteCount(aiInfo);
        return true;
    }

    public boolean existFavorite(Long userId, Long aiInfoId){
        return favoriteRepository.findByUserIdAndAiInfoId(userId, aiInfoId).isPresent();
    }
}