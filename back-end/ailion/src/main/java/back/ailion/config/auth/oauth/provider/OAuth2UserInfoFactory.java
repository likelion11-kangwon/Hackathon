package back.ailion.config.auth.oauth.provider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(AuthProvider authProvider, Map<String, Object> attributes) {
        switch (authProvider) {
            case GOOGLE: return new GoogleUserInfo(attributes);
            case NAVER: return new NaverUserInfo(attributes);
            case FACEBOOK: return new FacebookUserInfo(attributes);

            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}