package io.swen90007sm2.alpheccaboot.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * class manager, handler reflection logic.
 * Scan and load class file!
 *
 * @author xiaotian
 */
public class ClassLoadUtil {

    /**
     *
     * Using context class loader to break Parent delegation mechanism of JVM,
     * to avoid class not found error, because by default some class will be loaded
     * within the root package of the JDK.
     *
     * @return class loader
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassLoadUtil.class);

    /**
     * load class, user can decide whether initialize the class or not (load->link->initialize).
     * initialize: init default value + run static code blocks in class file.
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className, isInitialized, getContextClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure, exception:", e);
            throw new RuntimeException(e);
        }
        return clazz;
    }

    /**
     * load class, and initialize the class too
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    public static Set<Class<?>> getClassSetUnderPackageName(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {

            // class file url
            Enumeration<URL> urls = getContextClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();

                // handles chinese/special path name
                String decodedString = URLDecoder.decode(url.toString(), StandardCharsets.UTF_8);
                url = new URL(decodedString);

                // protocol name of the url, file:// or jar://
                String protocol = url.getProtocol();
                if (protocol.equals("file")) {

                    // replace %20 space from url to string " "
                    String packagePath = url.getPath().replaceAll("%20", " ");
                    addClassToSetFromFile(classSet, packagePath, packageName);
                } else if (protocol.equals("jar")) {
                    // jar:// can establish jar connection directly
                    JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                    if (jarURLConnection != null) {
                        JarFile jarFile = jarURLConnection.getJarFile();
                        if (jarFile != null) {
                            Enumeration<JarEntry> jarEntries = jarFile.entries();
                            while (jarEntries.hasMoreElements()) {
                                JarEntry jarEntry = jarEntries.nextElement();
                                String jarEntryName = jarEntry.getName();
                                if (jarEntryName.endsWith(".class")) {
                                    // change io/swen90007sm2/core/Some.class to io.swen90007sm2.framework.core.Some
                                    String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                    addClassToSetWithClassName(classSet, className);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("get class set failure with exception: ", e);
            throw new RuntimeException(e);
        }

        return classSet;
    }



    private static void addClassToSetFromFile(Set<Class<?>> classSet, String packagePath, String packageName) {

        // find all .class files or directory in that packPath
        File[] classFiles = new File(packagePath).listFiles(
                file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory()
        );

        if (classFiles == null) return;

        for (File file : classFiles) {
            String fileName = file.getName();
            if (file.isFile()) {

                // change Some.class to Some
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNotEmpty(packageName)) {

                    // build up io.swen90007sm2.framework.core.Some
                    className = packageName + "." + className;
                }
                addClassToSetWithClassName(classSet, className);
            } else {
                // if is not a file but a directory
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }

                // recursively traverse directory
                addClassToSetFromFile(classSet, subPackagePath, subPackageName);
            }
        }
    }

    /**
     * add the class object with some name to the class set
     */
    private static void addClassToSetWithClassName(Set<Class<?>> classSet, String className) {
        Class<?> clazz = loadClass(className, false);
        classSet.add(clazz);
    }
}
