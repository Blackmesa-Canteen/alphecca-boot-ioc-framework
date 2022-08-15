package io.swen90007sm2.app.common.util;

import io.swen90007sm2.alpheccaboot.common.util.ClassLoadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 996Worker
 * @description uti for handling resources in the resources dir
 * @create 2022-08-10 16:27
 */
public class ResourceUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtil.class);

    /**
     * read the file from resources dir.
     * @param fileName file path/name
     * @return inputString please free it after use!
     */
    public static InputStream getFileStringFromResources(String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = ClassLoadUtil.getContextClassLoader().getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new FileNotFoundException(fileName + "property file is not found");
            }
        } catch (IOException e) {
            LOGGER.error("Read file [{}] from resources failed! {}", fileName, e.toString());
            throw new RuntimeException(e);
        }

        return inputStream;

    }

    /**
     * parse text file line by line
     * @param path file path
     * @return List of string
     */
    public static List<String> readLine(String path){
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {

            String line = "";
            while ((line = reader.readLine()) != null){
                if (line.trim().length()>0){
                    list.add(line);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }


    //读取文件内容并返回
    public static String readToString(String fileName){
        String encoding = "UTF-8";
        //new File对象
        File file = new File(fileName);
        //获取文件长度
        Long filelength = file.length();
        //获取同长度的字节数组
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(filecontent);
            return new String(filecontent,encoding);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            try {
                in.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    //向文件里写入内容
    public static void saveAsFileWriter(String path, String content){
        File file = new File(path);
        //如果目录不存在 创建目录
        try {
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        FileWriter fileWriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fileWriter = new FileWriter(path, false);
            fileWriter.write(content);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}