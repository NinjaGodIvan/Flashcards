# Flashcards

**NOTE**: *This project is currently in development. I do not recommend you cloning this repo (downloading it) until it's complete. This will be finalized around this April. So hang tight!*

Are you tired of having to write down your own flashcards? Are you cutting your budget even though that it costs a penny to buy a set of index cards?

Flashcards is a Java application where you can customize your own flashcards and their sets. You can use this app without having to write down any flashcards if your hands are too tired of writing words with a pencil. Save money and download this app for **FREE**!

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

1. Make sure that the user and your password is correct when connecting to MySQL server. Otherwise, it will throw an error and you can't access your flashcard sets.

2. On the console log from any IDE or terminal, if it displays this following error: **The server time zone value 'time_zone_value' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the 'serverTimezone' configuration property) to use a more specifc time zone value if you want to utilize time zone support**, run this MySQL command => **SET GLOBAL time_zone = 'time_zone_value'**.

3. You can download MySQL Workbench to also keep track of flashcard sets (tables) and their flashcards (rows). It is much more convenient that running in the terminal or command prompt. 

4. The application is not designed for users to change the flashcard set's name. You have to run a command to change the name of the table and ensure that its name is wrapped around with brackets.