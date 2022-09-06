package io.swen90007sm2.app;

import io.swen90007sm2.alpheccaboot.AlpheccaBootApplication;
import io.swen90007sm2.alpheccaboot.annotation.AlpheccaBoot;

/**
 * main entrance of the app
 *
 * configuration file: resources/application.properties
 *
 * @author xiaotian
 */
@AlpheccaBoot
public class HotelBookingApplication {
    public static void main(String[] args) {
        AlpheccaBootApplication.run(args);
    }
}
