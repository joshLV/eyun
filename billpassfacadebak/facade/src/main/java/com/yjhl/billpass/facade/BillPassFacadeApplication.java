package com.yjhl.billpass.facade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by zhouyu on 2017/3/20.
 */

@EnableFeignClients(basePackageClasses ={ com.yjhl.yqb.api.IBillpassService.class})
@EnableEurekaClient
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@ComponentScan(basePackages = {"com.yjhl.yqb.api","com.yjhl.billpass.facade.controller"})
public class BillPassFacadeApplication {
    @Bean
    public Docket addDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                                    .title("一帐通接口服务")    //接口文档大标题
                                    .description("一帐通接口服务")    //接口文档描述
                                    .version("v1")   //版本号
                                    .termsOfServiceUrl("http://www.baidu.com")    //服务条款
                                    .contact(new Contact("zhooyu","www.yinpiao.com","zhouyu@yinpiao.com"))  //联系人姓名， 提交问题url，邮箱
                                    .license("lisence")    //lisence
                                    .licenseUrl("lisenceurl").build())   //LISENCE文档地址
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yjhl.yqb.api"))  //要扫描的包()
                .paths(PathSelectors.any())      //此处可以添加过滤器
                .build()
                .host("http://127.0.0.1:8080");   //配置swagger-api服务调用地址。
    }

    public static void main(String[] args) {
        SpringApplication.run(BillPassFacadeApplication.class, args);
    }
}

