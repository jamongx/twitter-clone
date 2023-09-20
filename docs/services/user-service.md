- This service handles user-related concerns such as authentication and user information.
- This table will contain a user's information such as name, email, dob, and other details
- This table maps the followers and followees as users can follow each other (N:M relationship).
- This table maps tweets with users for the favorite tweets functionality in our application.


---


### User Stories

#### User Registration and Authentication
1. As a new user, I want to register for a Twitter account so that I can engage with other users.
    - Acceptance Criteria:
        - I should be able to provide an email or phone number.
        - I should be able to create a password.
        - I should receive a confirmation email or SMS to verify my identity.
2. As an existing user, I want to log in to my Twitter account so that I can engage in social activities.
    - Acceptance Criteria:
        - I should be able to log in using my email/phone and password.
        - I should have the option to reset my password if forgotten.


#### Profile Management
1. As a user, I want to edit my profile so that I can share relevant information about myself.
    - Acceptance Criteria:
        - I should be able to change my profile picture.
        - I should be able to edit my bio, location, and website information.
2. As a user, I want to view other users' profiles so that I can know more about them.
    - Acceptance Criteria:
        - I should be able to see their tweets, following, and follower counts.
        - I should be able to see their bio, location, and website information if available.

#### Follow and Unfollow
1. As a user, I want to follow other users so that I can see their tweets in my timeline.
    - Acceptance Criteria:
        - I should be able to search for users.
        - I should be able to click a "Follow" button on their profile.
2. As a user, I want to unfollow users so that I no longer see their tweets in my timeline.
    - Acceptance Criteria:
        - I should be able to click an "Unfollow" button on their profile.
        - Their tweets should be immediately removed from my timeline.


---


### Database

- [User service](data-model.md#user-service)


---


### API Documentation

- [User service](api-documentation.md#user-service)