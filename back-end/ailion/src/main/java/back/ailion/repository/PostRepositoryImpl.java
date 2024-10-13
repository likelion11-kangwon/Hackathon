package back.ailion.repository;

import back.ailion.model.dto.PostDto;
import back.ailion.model.entity.Post;

import java.util.List;

public class PostRepositoryImpl implements PostCustomRepository {
    @Override
    public List<PostDto> getBestList() {
        return null;
    }
//    private final JPAQueryFactory queryFactory;
//
//    @Override
//    public List<PostDto> getBestList() {
//        List<Post> boardList = queryFactory
//                .selectFrom(board)
//                .leftJoin(board.category, category).fetchJoin()
//                .leftJoin(board.writer, member).fetchJoin()
//                .where(
//                        board.boardType.eq(boardType))
//                .orderBy(board.likeCount.desc())
//                .limit(20)
//                .fetch();
//
//        return BoardResponseMapper.INSTANCE.toDtoList(boardList);
//    }
}