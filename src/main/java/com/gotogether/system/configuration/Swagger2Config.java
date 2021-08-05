package com.gotogether.system.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.gotogether.controller"))
                // Api Annotation 으로 선언된 부분만 swagger 로 노출되도록 설정
                // .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)) //
                // HelloController.java
                // .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) //
                // HelloRestController.java
                .paths(PathSelectors.any())
                // .paths(PathSelectors.ant("api/v1/**"))
                .build().useDefaultResponseMessages(false).enableUrlTemplating(false)
                .apiInfo(new ApiInfoBuilder().title("spring-work").version("v1").description("gotogether swagger api")
                        .license("Apache License 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0").build());
    }

    // https://github.com/swagger-api/swagger-core/wiki/annotations
    // @Api Marks a class as a Swagger resource.
    // @ApiImplicitParam Represents a single parameter in an API Operation.
    // @ApiImplicitParams A wrapper to allow a list of multiple ApiImplicitParam
    // objects.
    // @ApiModel Provides additional information about Swagger models.
    // @ApiModelProperty Adds and manipulates data of a model property.
    // @ApiOperation Describes an operation or typically a HTTP method against a
    // specific path.
    // @ApiParam Adds additional meta-data for operation parameters.
    // @ApiResponse Describes a possible response of an operation.
    // @ApiResponses A wrapper to allow a list of multiple ApiResponse objects.
    // @Authorization Declares an authorization scheme to be used on a resource or
    // an operation.
    // @AuthorizationScope Describes an OAuth2 authorization scope.

}