package io.swen90007sm2.alpheccaboot.bean;

/**
 * a bean holds validation error message, used to send back to front end
 *
 * @author xiaotian
 */
public class ParamValidationErrorBean {

    private String paramName;
    private String errorMessage;

    public ParamValidationErrorBean() {
    }

    public ParamValidationErrorBean(String paramName, String errorMessage) {
        this.paramName = paramName;
        this.errorMessage = errorMessage;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
