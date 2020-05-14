package io.pivotal.management.user.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collection;
import java.util.Collections;

@EnableSwagger2
@Configuration
public class SwaggerConfig {


    public Docket userApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
        return docket;
    }

    private ApiInfo apiInfo() {

        Contact contact = new Contact("Administrator", "https://tanzu.vmware.com", "");
        Collection<VendorExtension> vendorExtensions = Collections.EMPTY_LIST;
        ApiInfo apiInfo = new ApiInfo("title", "description", "version", "termsOfServiceUrl", contact, "license", "licenseUrl", vendorExtensions);
        return apiInfo;
    }
}
