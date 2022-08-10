package io.swen90007sm2.alpheccaboot.core.aop.interceptor.validation;

import io.swen90007sm2.alpheccaboot.annotation.validation.Validated;
import io.swen90007sm2.alpheccaboot.bean.MethodCalling;
import io.swen90007sm2.alpheccaboot.core.aop.interceptor.AbstractInterceptor;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.alpheccaboot.exception.ValidationException;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.*;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

/**
 * interceptor to perform JSR303 validation
 *
 * @author xiaotian
 * @author shuang.kou:https://github.com/Snailclimb/jsoncat
 */
public class JSR303ValidationInterceptor extends AbstractInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractInterceptor.class);
    private final Validator VALIDATOR;

    public JSR303ValidationInterceptor() {

        // close the factory if finished generate a Validator
        // attach hibernateValidator to JSR303 API
        try (ValidatorFactory factory = Validation.byProvider(
                HibernateValidator.class
                )
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()) {

            this.VALIDATOR = factory.getValidator();
        }
    }

    /**
     * check whther perform the validator on this bean
     */
    @Override
    public boolean supports(Object bean) {
        // if this bean (usually the controller obj) marked with Validated, we can enhance it's method calling
        return (bean != null && bean.getClass().isAnnotationPresent(Validated.class));
    }

    @Override
    public Object intercept(MethodCalling methodCalling) {

        LOGGER.info("JSR303 Validation Interceptor Calling for [{}] 's [{}] controller",
                methodCalling.getTargetObject().getClass().getName(),
                methodCalling.getTargetMethod().getName());
        // when call methods from this bean, interception will be triggered
        // get annotations from param
        Annotation[][] paramAnnos = methodCalling.getTargetMethod().getParameterAnnotations();
        Object[] methodArgs = methodCalling.getArgs();

        // get validated param, validate it with Hibernate validator
        // travel all params
        for (int i = 0; i < methodArgs.length; i++) {
            // one param may have multiple param
            Annotation[] annotationArray = paramAnnos[i];
            boolean needValid = Arrays.stream(annotationArray).anyMatch(
                    // Valid.class Marks a property, method parameter or method return type for validation cascading.
                    annotation -> annotation.annotationType() == Valid.class
            );

            // if this param has @Valid anno, validate with JSR303
            if (needValid) {
                // use validator created by factory to validate the arg, then create result.
                if (ArrayUtils.isEmpty(methodArgs) || methodArgs[i] == null) {
                    throw new RequestException("missing input param");
                }
                Set<ConstraintViolation<Object>> results = VALIDATOR.validate(methodArgs[i]);
                if (!results.isEmpty()) {
                    throw new ValidationException(results);
                }
            }
        }

        // passed validation, continue calling the method
        return methodCalling.proceed();
    }
}
