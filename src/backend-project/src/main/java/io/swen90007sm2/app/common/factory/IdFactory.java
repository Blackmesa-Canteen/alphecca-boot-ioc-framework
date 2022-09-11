package io.swen90007sm2.app.common.factory;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * a factory for generating business id
 */
public class IdFactory {
    
    public static Long genSnowFlakeId() {
        return cn.hutool.core.util.IdUtil.getSnowflake().nextId();
    }

    public static String genSnowFlaskIdString() {
        return IdUtil.getSnowflake().nextIdStr();
    }



    public static void main(String[] args) {
        System.out.println(genSnowFlaskIdString());
    }
}
