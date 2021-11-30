# SamePage

SamePage is a collaborative book writing web app.
by Taylor Barnes, Thalia La Pommeray, Dominique Ortega, Isaac Tuckey
Group 11 Project #3 Interactive Book Writing
COP 4331, Fall 2021

Materials Required
- Download and install IntelliJ IDEA
  https://www.jetbrains.com/idea/download/#section=windows
- Download and install MySQL Workbench
  https://dev.mysql.com/downloads/installer/ https://dev.mysql.com/downloads/workbench/ 

Build Instructions:

MySQL set up:
- Download and install MySQL Workbench
- When it prompts you to make a local account, sign in with the root account, and set your password as “toor”
- In Local instance MySQL80, type in your password (“toor”) and create a schema named “samepage.”
- In the menu bar, go to Server > Data Import, and click “Import from Self-Contained File” and select the downloaded “samepageexport.sql” file. Choose “samepage” in the “Default Target Schema” area.
- Click “Start Import”

IntelliJ IDEA set up:
- Open the “SamePage” folder through IntelliJ.
- In the file directory at the left, go to SamePage > src > main > java > com.Group11.SamePage > SamePageApplication
- Run “SamePageApplication”

Browser:
- Open your browser (Mozilla Firefox or Google Chrome preferred) and go to http://localhost:8080/
- You can start using SamePage!

Bugs or other notes:
- If you have a different username and  password than “toor,” in IntelliJ, go to SamePage > src > main > resources > application.properties. You can change lines 3 and 4 to contain your username and password other than “root” and “toor.”
- When running IntelliJ, your schema in MySQL Workbench should have “hibernate_sequence” added.
- If IntelliJ won’t run due to an error that mentions “hibernate,” go to MySQL Workbench and type in the query: “INSERT INTO samepage.hibernate_sequence (next_val) VALUES (0);” and execute this. Try rerunning SamePageApplication again.
- Other hibernate_sequence bugs may occur, such as the console in IntelliJ saying that a current ID already exists. There is no known fix for this other than repeating the action until the counter increments to an ID that doesn’t exist yet.
- You can theoretically connect another device to the main device that’s running the server, as long as you connect to the same network, and the main device does not have a firewall or anything that could prevent others from connecting. This can be done by having the second device type in http://<insert host’s IP address here>:8080/ on their browser, once the main device has started to run the server.
- Make sure your IntelliJ IDEA Maven dependencies will roughly contain: Spring framework, JQuery, Bootstrap, JSON, MySQL. If you don’t see them, try Reloading All Maven Projects.
