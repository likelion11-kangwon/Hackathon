//package back.ailion;
//
//import back.ailion.model.entity.Authority;
//import back.ailion.model.entity.User;
//import back.ailion.model.entity.Post;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//import java.util.HashSet;
//import java.util.Set;
//
//@Component
//@RequiredArgsConstructor
//public class InitDb {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.dbInit1();
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final EntityManager em;
//
//        public void dbInit1() {
//
//            // 권한 객체 생성
//            Authority authority1 = new Authority("ROLE_USER");
//            Authority authority2 = new Authority("ROLE_ADMIN");
//
//            // 권한 객체를 담을 Set 생성
//            Set<Authority> authorities = new HashSet<>();
//            authorities.add(authority1);
//
//            User user1 = createMember("비번1", "이메일1", "닉넴1", true, "이름1", "0101234", "카카오", "카카오1");
//            em.persist(user1);
//            User user2 = createMember("비번2", "이메일2", "닉넴2", true, "이름2", "0101235", "카카오", "카카오1");
//            em.persist(user2);
//            User user3 = createMember("비번3", "이메일3", "닉넴3", true, "이름3", "0101236", "카카오", "카카오1");
//            em.persist(user3);
//            User user4 = createMember("비번4", "이메일4", "닉넴4", true, "이름4", "0101237", "카카오", "카카오1");
//            em.persist(user4);
//
//            int count = 1;
//            for (int i = 0; i < 50; i++) {
//                String writer = "write" + count;
//                String title = "title" + count;
//                String content = "content" + count;
//                String category = "category" + count;
//                Post post = new Post(title, content, writer, user1, 0, 0, 0, false, category);
//                em.persist(post);
//                count += 1;
//            }
//        }
//
//        private User createMember(String password, String username, String nickname, boolean activated,
//                                  String name, String phone, String provider, String providerId) {
//            Set<Authority> authorities = new HashSet<>();
//            User user = User.builder()
//                    .password(password)
//                    .username(username)
//                    .nickname(nickname)
//                    .activated(activated)
//                    .name(name)
//                    .phone(phone)
//                    .provider(provider)
//                    .providerId(providerId)
//                    .authorities(authorities)
//                    .build();
//            return user;
//        }
//    }
//}
