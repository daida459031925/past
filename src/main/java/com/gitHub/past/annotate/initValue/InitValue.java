package com.gitHub.past.annotate.initValue;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface InitValue {
    String value() default "";
}
