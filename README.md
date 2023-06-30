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

CRUD Operation	Endpoint	Description
POST	/auth/login	login into account
POST	/ contact/add	Add Contact information
GET	/contact/get/all	Fetch the Contact details all
GET	/dashboard	Fetch the Dashboard information like Total donations etc..
POST	/feedback/add	Add the Feedback
GET	/user/{userId}/feedback	Fetch the feedback based on user ID
GET	/file/{type}/{file}	Get the File based on file type
GET	/file/download/{type}/{fileName}	Download the file based on the type and filename
POST	/file/upload	Upload the file
GET	/donation/list	fetches the Donation List
PUT	/food/accept	Update the Food upon accept the food donation
POST	/food/add	Add the food donation
GET	/food/list	Get the Food donation list all 
PUT	/food/reject	Reject the Food Donation
POST	/food/update	Update the Food donation before accepting the food 
GET	/get/food/{id}	Fetch the food details based on ID
GET	/getAll/food/list	Get the Food list all information 
POST	/rating/add	Add the Rating
GET	/user/{userId}/ratings	Fetch the Rating based on the User ID
GET	/user/reporting	Get the Reporting information
POST	/changePassword	Change the password
GET	 /user/{id}	Get the User details based on ID 
DELETE	/user/{id}/delete	Delete the user account based on user id
POST	/user/add	Add the user account
GET	 /user/list	Fetch the all the user’s information
POST	/user/pagination/filter	Fetch the user information up to 5 based on filter number
POST	/user/password/forgot	Forgot the password
POST	 /user/refer	Refer a friend
POST	/user/update	Update the user account
GET	/registrationConfirm	Registration confirms create an account
GET	/resendRegistrationToken	Fetch the registration token

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

