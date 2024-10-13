package back.ailion.service;


import back.ailion.config.auth.SecurityUtil;
import back.ailion.exception.BaseException;
import back.ailion.exception.BaseExceptionCode;
import back.ailion.exception.custom.NotFoundException;
import back.ailion.model.dto.AiInfoResponseDto;
import back.ailion.model.dto.PostDto;
import back.ailion.model.dto.ProfileDto;
import back.ailion.model.dto.UserDto;
import back.ailion.model.entity.*;
import back.ailion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto UserToUserDto(User user) {
        return new UserDto(user);
    }

    @Transactional
    public User signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new BaseException(BaseExceptionCode.USER_ID_CONFLICT);
        }

        // 가입되어 있지 않은 회원이면,
        // 권한 정보 만들고
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();


        Map<String, Integer> recommends = userDto.getRecommends();
        List<Recommend> recommendList = new ArrayList<>();

        for(String recommend : recommends.keySet()){
            if(recommends.get(recommend) > 3){
                recommendList.add(Recommend.valueOf(recommend.toUpperCase()));
            }
        }

        // 유저 정보를 만들어서 save
        User user = User.builder()
                .username(userDto.getUsername())
                .password("{bcrypt}" + passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .email(userDto.getEmail())
                .name(userDto.getName())
                .phone(userDto.getPhone())
                .recommends(recommendList)
                .build();

        return userRepository.save(user);
    }

    // 유저,권한 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }

    public Boolean isValidId(String id) {
        if (userRepository.findByUsername(id).isPresent()) {
            return true;
        }
        else{
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<PostDto> myPosts(String username) {

        List<Post> posts = userRepository.findPostsById(userRepository.findByUsername(username).get().getId());
        List<PostDto> collect = posts.stream()
                .map(post -> new PostDto(post))
                .collect(Collectors.toList());
        return collect;
    }

    public List<PostDto> myLikePosts(String username) {

        List<Heart> hearts = userRepository.findHeartsById(userRepository.findByUsername(username).get().getId());
        List<PostDto> collect = hearts.stream()
                .map(heart -> new PostDto(heart.getPost()))
                .collect(Collectors.toList());

        return collect;
    }

    public List<AiInfoResponseDto> myFavoriteAi(String username) {

        List<Favorite> favorites = userRepository.findFavoritesById(userRepository.findByUsername(username).get().getId());
        List<AiInfoResponseDto> collect = favorites.stream()
                .map(favorite -> new AiInfoResponseDto(favorite.getAiInfo()))
                .collect(Collectors.toList());

        return collect;
    }

    @Transactional
    public UserDto setNickname(String nickname, String username){
        User user = userRepository.setNickname(nickname, username);

        return UserDto.builder()
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .email(user.getEmail())
                .name(user.getName())
                .date(user.getDate())
                .build();
    }

    @Transactional
    public UserDto setEmail(String email, String username){
        User user = userRepository.setEmail(email, username);

        return UserDto.builder()
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .email(user.getEmail())
                .name(user.getName())
                .date(user.getDate())
                .build();
    }

    @Transactional
    public UserDto setDate(Date date, String username){
        User user = userRepository.setDate(date, username);

        return UserDto.builder()
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .email(user.getEmail())
                .name(user.getName())
                .date(user.getDate())
                .build();
    }

    @Transactional
    public UserDto setPassword(String password, String username) {
        String encodePassword = "{bcrypt}" + passwordEncoder.encode(password);
        User user = userRepository.setPassword(encodePassword, username);

        return UserDto.builder()
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .email(user.getEmail())
                .name(user.getName())
                .date(user.getDate())
                .build();
    }

    public ProfileDto getMyProfile(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(BaseExceptionCode.USER_NOT_FOUND));

        return new ProfileDto(userRepository.findHeartCountById(user.getId()), UserToUserDto(user));
    }
}
