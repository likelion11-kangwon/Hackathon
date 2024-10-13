package back.ailion.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

//
@Configuration
public class SwaggerConfiguration {
    private Docket testDocket(String groupName, Predicate<String> selector) {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(this.apiInfo(groupName)) // ApiInfo 설정
                .useDefaultResponseMessages(false)
                .groupName("testApi")
                .select()
                .apis(RequestHandlerSelectors.
                        basePackage("back.ailion"))
                .paths(PathSelectors.ant("")).build();
    }

    private ApiInfo apiInfo(String groupName) {
        return new ApiInfoBuilder()
                .title("제목")
                .description("설명")
                .version("1.0")
                .contact(new Contact("이름", "홈페이지 URL", "e-mail"))
                .build();
    }
}
