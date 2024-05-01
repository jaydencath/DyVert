## Title
>DyVert

## Team Members
>Sergio Guerra, Samuel George, Jayden Cathcart

## Description 
>The motivation for DyVert derives from a lack of media search instruments that accurately cater to the interest of the consumer. With this app, our goals are to broaden the client's media/ entertainment palette and showcase the content of our creators to new consumers who share a common interest.

>Our service will take place as a web app, giving users easy access to our platform. The user will be able to swipe (DyVert) items left or right based on if they are interested in the item and want to save them, or if they are not interested. The selling point of our app is to essentially give users a social platform that lets them find and save items that they may want to look into based on a preview shown on a content card.

>Other users will include admins who can monitor posts. They will be able to flag and delete content cards. They will also be able to delete posts. Another user will be the creator/curator who can create content cards and edit those cards for the regular user.


![Use Case Diagram](./Use%20Case%20Diagram.png)

## Directions
- Clone the project and open in Netbeans
- Open XAMPP Control Panel Dashboard.
- Start Apache.
- Start MySQL.
- Click on MySQL Admin, to open the database dashboard on your browser.
- Create a database with the name 'dyvert-db'.
- Import 'dyvert-db.sql'.
- Clean and Build the project.
- Run->Set Project Configuration->Customize->Run->Main Class->Browse->Select com.DyVert.DyVert.DyVertApplication.java.
- Run the main method.
- On web browser: http://localhost:8080
    - Login with three user types with username below:
        - Username: user
        - Username: creatorUser
        - Username: adminUser
        - All passwords are password
    - User can only access Home and Bucket.
        - Trying to access Creator/Admin will throw error.
    - Creator can access what user can AND Creator tab.
        - Trying to access Admin will throw error.
        - If create page throws error after publish, try smaller image file size.
    - Admin can access all tabs.
    - Click on Profile Icon in top right at any time to log out.
    - Create your own users with sign up link on login page!