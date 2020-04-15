# Flashcards

Are you tired of having to write down your own flashcards? Are you cutting your budget even though that it costs a penny to buy a set of index cards?

Flashcards is a Java application where you can customize your own flashcards and their sets. You can use this app without having to write down any flashcards if your hands are too tired of writing words with a pencil. Save money and download this app for **FREE**!

####DOWNLOAD:

**NOTE:** *After downloading MySQL, go through all the steps and run the server.*

MySQL download link => **https://dev.mysql.com/downloads/mysql/**.
mysql-connector-java-8.0.19.jar download link => **https://dev.mysql.com/downloads/connector/j/**

####FOR ALL USERS:

On the console log from any IDE or terminal, if it displays this following error: 

**The server time zone value 'time_zone_value' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the 'serverTimezone' configuration property) to use a more specifc time zone value if you want to utilize time zone support**

Run this MySQL command => **SET GLOBAL time_zone = 'time_zone_value'**

**FOR ECLIPSE USERS:**

1. Open your terminal or Git, and branch to your workspace of Eclipse.

2. Clone the Flashcards repository to that workspace (Use git clone https://github.com/NinjaGodIvan/Flashcards)

3. Open Eclipse => File => Open Projects from File System

4. On Import Source, select the Flashcards project folder and open it.

5. After opening the Flashcards project, select Reference Libraries and make sure that mysql-connector-java-8.0.19.jar is included. If not, then you will need to add it to the library.

6. Run the project and have fun!
	
**FOR NETBEANS USERS:**

1. Open NetBeans => Team => Git => Clone

2. Copy the Flashcards repository URL and paste it on the URL textfield.

3. Clone the Flashcards repository to the destination folder.

4. When a pop-up window appears, select "Open Projects"

5. Select Java => Java Project with Existing Sources

6. Name your Flashcards Project

7. On "Source Package Folders", open Flashcards/images and Flashcards/src

8. Select "Finish" to create a Java Application for Flashcards

9. On the Projects tab, right click "Libraries" and select "Add JAR/Folder"

10. Select "mysql-connector-java-8.0.19.jar" and add it to Libraries.

11. Run the project and have fun!
	
#### FEATURES:

1. **Create a flashcard set**: You can start by adding a flashcard set to add any flashcards.

2. **Delete a flashcard set**: You can delete a flashcard set.

3. **Create a flashcard**: You can create a flashcard by selecting a flashcard set and filling out a question and an answer.

4. **Edit a flashcard**: You can edit a flashcard by selecting a flashcard set. After selecting a flashcard set, the screen will prompt the question and answer on textboxes for you to edit. If you decided to edit a different flashcard, you have to go back to the flashcard set selection.

5. **Delete a flashcard**: After selecting a flashcard set to proceed to deleting a flashcard, you will be given an option to select multiple flashcards to delete from a set. 

6. **View flashcards**: This is where you can view your own flashcards. After selecting a flashcard set to view your flashcards, the flashcard maker will be constructing every flashcards from the set. You can press "Previous" or "Next" to go through the flashcards. It also features a glossary where you can go to any flashcard within an instant!

#### FLASHCARDS APP INFO:

1. All questions from the flashcards are **UNIQUE**. Any question that already exists in the data is invalid. This prevents multiple duplications of deleting flashcards. If you enter a question that already exists, then you will be prompt to reenter the question. Making the same question but with different amount of non-leading or non-trailing spaces is also invalid.

2. Every flashcard **MUST** include a question and an answer, or it's considered invalid. Both question and the answer have at least one character.

3. Deleting the flashcard set deletes all flashcards within that set.

#### MySQL INFO:

1. Make sure that the user and your password is correct on DatabaseHandler.java when connecting to MySQL server. Otherwise, it will throw an error and you can't access your flashcard sets.

2. You can download MySQL Workbench to also keep track of flashcard sets (tables) and their flashcards (rows). It is much more convenient that running in the terminal or command prompt. 

3. The application is not designed for users to change the flashcard set's name. You have to run a command to change the name of the table and ensure that its name is wrapped around with brackets.