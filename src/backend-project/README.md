# Backend System

## Alphecca Hotel Booking System
 
# Intro

We implemented a mini IoC supporter called *Alphecca-Boot* in `src/main/java/io/swen90007sm2/alpheccaboot`.

The hotel booking website backend application is in `src/main/java/io/swen90007sm2/app`.

# Features

We implemented a mini IoC supporter called *Alphecca Boot*:
- Tomcat server embedded as an object. Don't need to configure web container while development.
- NIO Dispatcher Servlet.
- IoC singleton bean container, class scan. Dependency injection with @AutoInjected annotation.
- Injection qualifier to inject specific bean you have defined.
- RESTful API support, can also support basic `HttpServletRequest` or `HttpServletResponse`.
- Annotation support for MVC: @Handler, @Blo, @Dao.
- JSR303 Validation for Json Body parameter.
- Filter for request, helpful in authorization.
- Demo Configuration file support.

As for application:
- RESTful style API backend;
- JWT authentication;
- Role-based authorization, using request filter to reuse authorization logic;
- Simple Memory Cache with expiration time.

# Run
Java source code entrance: `io.swen90007sm2.app.HotelBookingApplication`

# Open Source Projects

Alphecca-Boot is inspired by the other project:

- [spring-boot](https://github.com/spring-projects/spring-boot)
- [jsoncat](https://github.com/Snailclimb/jsoncat)
- [handwritten-mvc](https://github.com/tyshawnlee/handwritten-mvc)
- [spring-imitator](https://github.com/Blackmesa-Canteen/spring-imitator)
- [halo](https://github.com/halo-dev/halo)