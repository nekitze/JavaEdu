# Chat

In this project I use the key mechanisms to work with the PostgreSQL DBMS via JDBC.

Key domain models which both SQL tables and Java classes are implemented for are:

- User
  - User ID
  - Login
  - Password
  - List of created rooms
  - List of chatrooms where a user socializes
- Chatroom
  - Chatroom id
  - Chatroom name
  - Chatroom owner
  - List of messages in a chatroom
- Message
  - Message id
  - Message author
  - Message room
  - Message text
  - Message date/time
 
In schema.sql file described CREATE TABLE operations to create tables for the project. There is also data.sql file with text data INSERTS.

To work with the repository, the Read/Find, Create/Save, Update and FindAll methods are implemented.
