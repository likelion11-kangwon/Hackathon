package back.ailion.config.auth.oauth.provider;

import java.util.Map;

public interface OAuth2UserInfo {
    public String getProviderId();

    public String getProvider();

    public String getEmail();

    public String getName();

    public Map<String, Object> getAttributes();
}
