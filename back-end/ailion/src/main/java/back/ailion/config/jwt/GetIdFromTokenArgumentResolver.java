package back.ailion.config.jwt;

import back.ailion.exception.BaseException;
import back.ailion.exception.BaseExceptionCode;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
public class GetIdFromTokenArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenProvider tokenProvider;

    public GetIdFromTokenArgumentResolver(TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(GetIdFromToken.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if(request == null){
            throw new IllegalStateException("No HttpServletRequest found");
        }

        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null){
            throw new BaseException(BaseExceptionCode.AUTHORIZATION_HEADER_NULL);
        }

        return tokenProvider.getUserPk(authorizationHeader.substring(6));
    }

}
