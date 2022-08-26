package io.swen90007sm2.alpheccaboot.common.util;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

/**
 * util for object convertion.
 *
 * @author xiaotian,
 */
public class ObjectUtil {

    /**
     * convert string to object with specific type，
     * used to convert incoming param to the corresponding type declared in controller method param.
     *
     * This technique is used in the Spring Boot framework src code.
     *
     */
    public static Object convertString2Object(String srcText, Class<?> targetClass) {
        PropertyEditor editor = PropertyEditorManager.findEditor(targetClass);
        if (targetClass.equals(String.class)) {
            editor.setAsText(srcText);
        } else {
            if (StringUtils.isNotEmpty(srcText)) {
                editor.setAsText(srcText);
            } else {
                editor.setValue(null);
            }
        }
        return editor.getValue();
    }
}
