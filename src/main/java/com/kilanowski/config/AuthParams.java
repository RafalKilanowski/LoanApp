package com.kilanowski.config;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams(
        @ApiImplicitParam(name = "Authorization", paramType = "header", type = "string"))
@interface AuthParams {

}
