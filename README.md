<div align="center">
  <br>
  <img src="logo-task-tracker.svg" alt="">
  <h1>task tracker</h1>
</div>

## ❓ *About project*
This is an advanced task tracker on Spring Boot and Kafka that says it all!

## 🛠 *Tech stack of project*
In its inception: 
* Java 21 Corretto
* Spring Boot 3+
* PostgreSQL 16
* Kafka *(sorry I forgot a normal version of Kafka)* 😅

## 🤓 *How to run from localhost?*
At now you may do it's very simple:
Clone repository in consoile:
```
gti clone https://github.com/MrNikaMilon/task-tracker-app
```

At firstly start a docker compose in project directory to start DB and another service:
```
docker compose up
```

Okay, you are wonderful! At now start our application:
```
./gradlew clean compile bootRun
```

And check our application on any endpoint which you can find out at: `http://localhost:*your ports*/swagger-ui/docs/index.html`

## ⚙️ *Logic of work this project*
### 🔐 Auth service(in core service)
* Registration - this is a check on existense user in app, if user already exists we call out method authorization in service. In request we wait RegistrationUserRequest:
   * String Name, String Email, String Password
   * After we return a message: "Succesful registration in task tracker app!" and return RegisrtationUserReponse:
   * String email, String access token   
* Authorization - we call out a authorization method and we check exist user in our DB, if not exists we return an exception, else we check avalibale token by user if exisit and avalible we return token and message about succesful authorization in system. We wait:
   * String email, String password
   * After we return a message: "Succesful authentication in task tracker app!" and return AutorizationUserResponse:
   * String email, String access token  
* Logout - we just use a logout endpoint and mark all user's tokens as invalidate and not actual. When users make a request and get an error. 

  
### ⏱️ Schedule service
We have a three case when our Scheduler start a work, in the last of every day we generate a statistic by users:
* Users wich have not completed tasks on 23:30 by Moscow have a notification - *You have don't complete task, in quantity: `n`*
* Users wich have completed tasks on 23:30 by Moscow have a notification - *You completed a tasks, in quantity: `n`*
* If both upper conditions are met, the user recivers the notification - *You have completed tasks in quantity: `n` and uncompleted tasks in quantity: `n`.*

The scheduler by **cron** expression goes to the database or method and pulls out statistic on users for a day and form them forms users, to whom it will send mailings the scheduller itself knows nothing about letters and their structures, after the performed analystics the sheduler puts the information for letters in the **EMAIL_SENDING_TASK** topic and this topic is read by the postman. 


### 📫 Postman service
Stores different types of mailings and when new messages are received in the listened topics:
* **NEW_REGISTRATION_NOTIFY**
*	**EMAIL_SENDING_TASK**

There are four types of mailings:
* Registration for the first time
* Uncompleted tasks
* Completed tasks
* General statistics
