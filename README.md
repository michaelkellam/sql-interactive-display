# sql-interactive-display
 Java program that allows for simple MySQL commands.

Author: Michael Kellam

To run this program, open the SQLGUI.jar file. You must have a MySQL server with appropriate admin rights to effectively use this program.

This project was created to test my basic knowledge of SQL and its commands. There is plenty of room for improvement, mainly the fact that whenever I perform an action (add,update,delete), I need to perform a query, in which I select the entire table rather than what I exactly need. But the point of this wasn't speed, rather creating a basic program that works as intended.

Due to my limited knowledges of GUI implementation, I did not quite understand how to allow scrolling through the table, so it fits the entire table into the frame. Again, this is not ideal for dealing with real large-scale databases, but for conveying its function, it works perfectly.

One problem that I need to fix is that the input when adding a new row is that even if the primary key is auto incremented, it will still ask for an input for it, even though it doesn't effect the final output at all. It's not a program-breaking bug, but I still do plan to fix it.

You may use/modify this code in any way you want. Credit to me is not required, but it is appreciated.

I would love to hear your criticism and what you think of this project! You can email me at: michaelkellammc@gmail.com
