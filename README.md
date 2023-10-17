# JMulticastChat
This project is a Java implementation of a multicast chat system that allows multiple users to communicate with each other using UDP multicast sockets.

The project also uses jSwing to create a graphical user interface for the chat system, as shown in the images . Additionally, the project separates the communication responsible for adding and removing new users from the chat conversation, which significantly improves the performance. The system uses a different port for managing the user list than for sending and receiving chat messages. This is a brief and concise description of the project.

The description presents the images of the application.

* The first image shows the main window of the chat system, where the user can enter their username.

![](https://github.com/Mati-Wojtacha/JMulticastChat/blob/main/IMG/login.png)

* The second image shows an error message that appears when the user tries to join the chat with a username that is already taken by another user. The user has to enter a different username and try again.

![](https://github.com/Mati-Wojtacha/JMulticastChat/blob/main/IMG/badUsername.png)

* The third image shows the chat window, where the user can see the messages from other users, as well as their own messages. The user can also send a message by typing it in the text field and pressing enter.

![](https://github.com/Mati-Wojtacha/JMulticastChat/blob/main/IMG/Chat.png)

* The fourth image shows a screenshot of multiple users participating in the chat system. Each user has a different username and color for their messages. The users can communicate with each other using multicast chat.

![](https://github.com/Mati-Wojtacha/JMulticastChat/blob/main/IMG/Multiuser.png)
