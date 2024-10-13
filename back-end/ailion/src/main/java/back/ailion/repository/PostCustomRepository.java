package back.ailion.repository;

import back.ailion.model.dto.PostDto;

import java.util.List;

public interface PostCustomRepository {


    List<PostDto> getBestList();
}
