# Surplus food waste management system backend application
 
Surplus food waste management system is a web application. This project is source code for backend software that providing all required web services to communicate with frontend applications. 
functions of the application is as follow:

- User Management, User Registration and secure login
- User Roles - Doner: [university, restaurant, individual, NGO] Recipient: [Recipient individual, Recipient organization]
- donation listing
- Collection scheduling 
- Recipient matching for food distribution and status tracking
- donation Distribution algorithm
- Food Inventory 
- Reminders & Email notification
- recipient feedback and Rating


# Getting Started
This project folder contains the source code of backend project
- Backend folder contains src folder and pom.xml file
- pom.xml contains all the required dependencies. 
- src contains main and test folders.
# Tools and IDE Used
•	Eclipse
•	MySQL workbench
•	Visual studio code
•	Git
•	Postman
Built With
# This project has been built using the following technologies:
•	Java
•	Spring Boot
•	Angular
•	MySQL
# MVC Architecture
Surplus sharing System follows to the MVC (Model-View-Controller) architecture and pattern, which is a popular design model for developing scalable and maintainable software systems. The application is organized into three primary components in this design (MODEL, VIEW, and CONTROLLER).
# Model:
Represents the business logic and data of the application. In this project, the model includes classes such as Food, Contact, Feedback, Rating, User, etc.
# View:
The view displays the data from the model to the user. The view understands how to get the model's data, but it has no idea what that data represents or what the user may do with it. View just represents the application's data on the screen.
# Controller:
This is an intermediary layer between the model and the view, it is handling user requests and updating the model accordingly. In this code, the controller includes classes those are UserController, FoodController, FeedbackController DashboardController, ReportController etc.
By separating the application into these three components, we can easily make changes to one component without affecting the others. This makes the application more modular and easier to maintain over time.
# APIs Used
The following APIs are used in this project:

<table>
  <tr>
    <th>CRUD Operation</th>
    <th>Endpoint</th>
    <th>Description</th>
  </tr>
  <tr>
    <td>POST</td>
    <td>/auth/login</td>
    <td>Login into account</td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/contact/add</td>
    <td>Add Contact information</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/contact/get/all</td>
    <td>Fetch the Contact details all</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/dashboard</td>
    <td>Fetch the Dashboard information like Total donations etc.</td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/feedback/add</td>
    <td>Add the Feedback</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/user/{userId}/feedback</td>
    <td>Fetch the feedback based on user ID</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/file/{type}/{file}</td>
    <td>Get the File based on file type</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/file/download/{type}/{fileName}</td>
    <td>Download the file based on the type and filename</td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/file/upload</td>
    <td>Upload the file</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/donation/list</td>
    <td>Fetches the Donation List</td>
  </tr>
  <tr>
    <td>PUT</td>
    <td>/food/accept</td>
    <td>Update the Food upon accepting the food donation</td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/food/add</td>
    <td>Add the food donation</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/food/list</td>
    <td>Get the Food donation list all</td>
  </tr>
  <tr>
    <td>PUT</td>
    <td>/food/reject</td>
    <td>Reject the Food Donation</td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/food/update</td>
    <td>Update the Food donation before accepting the food</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/get/food/{id}</td>
    <td>Fetch the food details based on ID</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/getAll/food/list</td>
    <td>Get the Food list all information</td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/rating/add</td>
    <td>Add the Rating</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/user/{userId}/ratings</td>
    <td>Fetch the Rating based on the User ID</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/user/reporting</td>
    <td>Get the Reporting information</td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/changePassword</td>
    <td>Change the password</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/user/{id}</td>
    <td>Get the User details based on ID</td>
  </tr>
  <tr>
    <td>DELETE</td>
    <td>/user/{id}/delete</td>
    <td>Delete the user account based on user ID</td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/user/add</td>
    <td>Add the user account</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/user/list</td>
    <td>Fetch all the user's information</td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/user/pagination/filter</td>
    <td>Fetch the user information up to 5 based on filter number</td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/user/password/forgot</td>
    <td>Forgot the password</td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/user/refer</td>
    <td>Refer a friend</td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/user/update</td>
    <td>Update the user account</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/registrationConfirm</td>
    <td>Registration confirms create an account</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/resendRegistrationToken</td>
    <td>Fetch the registration token</td>
  </tr>
</table>

# Installation
1.	Install Eclipse IDEA and MySQL .
2.	Clone the repository.
3.	Import the project into Eclipse as a Maven Existing Project.
4.	Maven Clean 
5.	Maven Install
6.	Run as a java application.
7.	Make sure you have properly configured the host and port details in the application. Properties file related to mysql properties
8.	Change the SMTP mail properties
Conclusion and Discussion
we faced many challenges with the roles and Requirement Creep and Integrating other services.
Acknowledgments
We would like to thank the following individuals and organizations for their contributions to this project:
•	Open-Source Initiative (Stack over flow etc.)
•	Spring Boot community
•	Angular Community



## Running project and test cases
-Build maven project and run unit test cases 
mvn clean
mvn install

run as a spring boot java application


