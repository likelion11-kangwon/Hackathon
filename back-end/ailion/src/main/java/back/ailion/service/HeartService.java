package back.ailion.service;

import back.ailion.exception.BaseExceptionCode;
import back.ailion.exception.custom.AlreadyExecutedException;
import back.ailion.exception.custom.NotFoundException;
import back.ailion.model.dto.HeartDto;
import back.ailion.model.dto.request.HeartRequestDto;
import back.ailion.model.entity.Heart;
import back.ailion.model.entity.Post;
import back.ailion.model.entity.User;
import back.ailion.repository.HeartRepository;
import back.ailion.repository.PostRepository;
import back.ailion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private HeartDto HeartToHeartDto(Heart heart) {
        return new HeartDto(heart);
    }

    @Transactional
    public HeartDto like(HeartRequestDto heartRequestDto) {

        User user = userRepository.findById(heartRequestDto.getUserId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.USER_NOT_FOUND));

        Post post = postRepository.findById(heartRequestDto.getPostId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.POST_NOT_FOUND));

        // 이미 좋아요 되어있으면 오류 반환
        if (heartRepository.findByUserAndPost(user, post).isPresent()){

            throw new AlreadyExecutedException(BaseExceptionCode.ALREADY_LIKED);
        }

        Heart heart = Heart.builder()
                .user(user)
                .post(post)
                .build();

        postRepository.addLikeCount(post);
        return HeartToHeartDto(heartRepository.save(heart));
    }

    @Transactional
    public boolean cancelLike(HeartRequestDto heartRequestDto) {

        User user = userRepository.findById(heartRequestDto.getUserId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.USER_NOT_FOUND));

        Post post = postRepository.findById(heartRequestDto.getPostId())
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.POST_NOT_FOUND));

        Heart heart = heartRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.HEART_NOT_FOUND));

        heartRepository.delete(heart);
        postRepository.subLikeCount(post);

        return true;
    }


}