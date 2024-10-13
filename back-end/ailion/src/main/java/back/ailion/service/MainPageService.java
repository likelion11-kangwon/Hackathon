package back.ailion.service;

import back.ailion.model.dto.AiInfoResponseDto;
import back.ailion.model.dto.MainPageDto;
import back.ailion.model.dto.PostDto;
import back.ailion.model.entity.AiInfo;
import back.ailion.model.entity.Post;
import back.ailion.repository.AiInfoRepository;
import back.ailion.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final AiInfoRepository aiInfoRepository;
    private final PostRepository postRepository;

    public MainPageDto getMainPage(int page) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<Post> paging = postRepository.findByDelCheckFalse(pageable);
        Page<PostDto> posts = paging.map(post -> new PostDto(post));

        List<AiInfo> aiInfoIds = aiInfoRepository.top5AI();
        List<AiInfoResponseDto> aiInfos = new ArrayList<>();
        for(AiInfo aiInfo : aiInfoIds){
            aiInfos.add(new AiInfoResponseDto(aiInfo));
        }

        pageable = PageRequest.of(0, 5);
        List<Post> postsByLike = postRepository.findBestPostsByLike(pageable);
        List<PostDto> bestPosts = postsByLike.stream()
                .map(post -> new PostDto(post))
                .collect(Collectors.toList());

        return new MainPageDto(posts, aiInfos, bestPosts);
    }

}
