package io.swen90007sm2.alpheccaboot.common.util;

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
     * used to convert incoming param to the corresponding type declared in handler method param.
     *
     * This technique is used in the Spring Boot framework src code.
     *
     */
    public static Object convertString2Object(String srcText, Class<?> targetClass) {
        PropertyEditor editor = PropertyEditorManager.findEditor(targetClass);
        editor.setAsText(srcText);
        return editor.getValue();
    }
}
