# Overview

## Purpose

- A React-based web UI implementation for a Twitter Clone. This project replicates the essential features and design of Twitter, serving as a platform for learning and demonstrating proficiency in React development, state management, and interaction with APIs.

## Scope

- The React UI encapsulates features such as sign up, sign in, profile management, following, explore, notification, and direct message.
- It interacts with a separate user service to perform token-based authentication and provides APIs for communication with other services in microservices achitecture.

## Service Dependencies

### User Service

- The primary objective of User Service is to oversee functionalities related to user management, including but not limited to, user profiles, registration, following, and login.

### Tweet Service

### Explore Service

### Message Service

### Notification Service

# Technologies

## Development Environment

- Build Tool: NPM 10.2.3, vite 5.0.0, eslint 8.53.0
- Code Repository: GitHub
- IDE: Visual Studio Code

## Frameworks and Libraries (Dependencies)

- react 18.2.0
- react-dom 18.2.0
- react-router-dom 6.20.1
- axios 1.6.2

## API

- RESTful APIs for integrating with backend services

# Installation and Setup (Windows 11)

```
> git clone https://github.com/jamongx/twitter-clone.git
> cd twitter-clone\twitter-clone-react
> \twitter-clone\twitter-clone-react> npm install
> \twitter-clone\twitter-clone-react> npm install react-router-dom --sav
> \twitter-clone\twitter-clone-react> npm install axios --save
> \twitter-clone\twitter-clone-react> npm install apollo-upload-client --save
```

# Developed Features

## Create a account

- [x] Create a registration form
- [x] Use an unique username and email to register
- [x] Display error message when invalid input
- [x] Close create account modal when success submit
- [ ] Sign Up with Google or Apple ID
- [ ] Email confirmation

## Sign in

- [x] Sign in with a registered username/email address and password
- [x] Sign in with JWT (JSON Web Token)
- [ ] Forget password
- [ ] Sign in with Google ID or Apple ID
- [ ] Reset password if forget password

## Log out

- [x] Redirect Sign in after Log out
- [x] Delete User's data of Session and LocalStorage

## Profile management

- [x] Change display name, bio, active/deactive and birth day
- [x] Upload and change user's avatar image

## Follow

- [x] Display Followers list
- [x] Display Following list
- [ ] Follow Followers from followers list
- [ ] UnFollow Following from followring list

## Tweet

- [x] Display Following Tweets with Infinite Loading using dummy tweets
- [x] Display Recommended Tweets with Infinite Loading using dummy tweets
- [ ] Interact with tweet service using RESTful API

# History

## version 1.0

- Established the main UI of twitter clone, includes Tweet, Explore, Notification, Messages, Follows and Profile management
- Implemented the features which based on user service such as create account, sign-in, profile edit, profile image upload
