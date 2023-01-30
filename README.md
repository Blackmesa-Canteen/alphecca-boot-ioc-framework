
# Alphecca Hotel Booking System

## Intro
This project introduced an IoC backend framework Alphecca Boot, which is inspired by Spring Boot Framework.
The backend framework supports:
- Tomcat server embedded as an object. Don't need to configure web container while development.
- NIO Dispatcher Servlet.
- IoC singleton bean container, class scan. Dependency injection with @AutoInjected annotation.
- Injection qualifier to inject specific bean you have defined.
- RESTful API support, can also support basic HttpServletRequest or HttpServletResponse.
- Annotation support for MVC: @Controller, @Blo, @Dao.
- JSR303 Validation for Json Body parameter.
- Filter for request, helpful in authorization.
- Demo Configuration file support.

Click [here](https://github.com/SWEN900072022/SWEN90007-2022-Alphecca/tree/main/src/backend-project) to jump to the backend part of the project.

## Run
Please use Java 11 to run `io.swen90007sm2.app.HotelBookingApplication`, which is located in `SWEN90007-2022-Alphecca/src/backend-project/`.

**WARNING**: Since the database is now invalid, please configure the correct database connection in the configuration file of the backend project.
 

## Project Overview

An implementation of an online hotel booking application that allows Customers to book stays at hotels.
The application would aggregate properties from different Hoteliers and display them to Customers. Similar to
Expedia, the business operates as a search engine and booking system for hotel shopping.

## Important Information to Use Our Product
* The user manual has been written in the document located [here](https://github.com/SWEN900072022/SWEN90007-2022-Alphecca/blob/main/docs/part2) with the name **architecture-document-part2-submission.pdf**. You will be able to find the User Manual in section 9 under the content page.
* To run the back-end server locally, the filre is located at ~/src/backend-project/src/main/java/io/swen90007sm2/app/HotelBookingApplication.java.
* To run the front-end locally, run `npm start` in the ~/src/frontend-project
* Images used the part 2 architecture document can be found [here](https://github.com/SWEN900072022/SWEN90007-2022-Alphecca/blob/main/docs/part2/images)

## Sprint Releases
:one: [Part 1 Release](https://github.com/SWEN900072022/SWEN90007-2022-Alphecca/releases/tag/SWEN90007_2022_Part1_Alphecca_v.2)

:two: [Part 2 Release](https://github.com/SWEN900072022/SWEN90007-2022-Alphecca/releases/tag/SWEN90007_2022_Part2_Alphecca)

:three: [Part 3 Release](https://github.com/SWEN900072022/SWEN90007-2022-Alphecca/releases/tag/SWEN90007_2022_Part3_Alphecca)

## Heroku Deployment Repositories
#### Front-end Deployment
[Link to Front-end in Heroku](https://swen90007-alphecca-frontend.herokuapp.com/)
#### Back-end Deployment & API Documentation
[Link to Back-end in Heroku](https://swen90007-alphecca-backend-app.herokuapp.com/)
#### Admin Login Repository
[Link to Admin Login](https://swen90007-alphecca-frontend.herokuapp.com/adminLogin/)

## Data Samples
For the purpose testing the system, simple and basic data samples for the Cutsomer, Hotelier and Admin have been created under [here](https://github.com/SWEN900072022/SWEN90007-2022-Alphecca/tree/main/docs/data-samples). You can find the credential details of each of the role in the three documents listed below:

* [customer.doc](https://github.com/SWEN900072022/SWEN90007-2022-Alphecca/blob/main/docs/data-samples/customer.doc)
	* User Id: edisontest1@gmail.com
	* Password: yls123456
* [hotelier.doc](https://github.com/SWEN900072022/SWEN90007-2022-Alphecca/blob/main/docs/data-samples/hotelier.doc)
	* User Id: edisonhotelier@gmail.com
	* Password: yls12345
* [admin.doc](https://github.com/SWEN900072022/SWEN90007-2022-Alphecca/blob/main/docs/data-samples/admin.doc)
	* User Id: admin@demo.com
	* Password: 123456


**As for backend API Documentation, visit the backend server with a browser: [Link to Backend in Heroku](https://swen90007-alphecca-backend-app.herokuapp.com/)**

# License
```
MIT License

Copyright (c) 2022 SWEN90007-2022-Alphecca

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
