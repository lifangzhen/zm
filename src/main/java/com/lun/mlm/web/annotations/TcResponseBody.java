package com.lun.mlm.web.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来处理tcm约定的返回对�?
 *
 * @author meng
 * @since 0.0.1
 * @see TcAnnotationMethodHandlerAdapter
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TcResponseBody {

}
