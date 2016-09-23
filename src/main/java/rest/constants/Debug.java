package rest.constants;

import java.lang.annotation.*;

/**
 * @author jtduan
 * @date 2016/9/22
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Debug {
    String value() default "";
}
