# Establishing a connection to a server via it's own protocol SATP

## SATP (Simple-Arithmetic-Task-Protocol)

This Protocol describes the communication between a client and a server which provides calculation-exercises. The client establishes the connection and authenticates itself to the server. After the successful authorization the server provides small calculation exercises to the client to solve. Should the client solve 100 exercises in a row correctly, the server sends congratulations and closes the connection. If the client transmits a wrong result, the server will report the failure and close the connection.

### Description of the SATP-Protocol

#### Communication (telnet)
- the server listens on port 7893
- each message from server or client consists of one line of text and has to end with a carriage return and line feed (in Java: '\r\n')
- as encoding US-ASCII is used

#### Authentification

- after the client has established a connection, the server sends an INFO-message and prompts for authentification

- the client has to anwer with LOGIN username password
- in case of success: the server sends an INFO-message
- in case of failure: the server sends an ENDE-message and closes the connection

#### Messages

##### AUFGABE

- begins with the literal "AUFGABE" followed by a space character and a calculation-exercise consisting of a unsigned integer, an operator (+-\*/) and another unsigned integer (e.g. AUFGABE 5 \* 14)

- the client calculates the result, transmits it to the server and waits for the next exercise

##### INFO

- begins with the literal "INFO" followed by a space character and a text

- the client prints the text to the console, then it waits for further messages

##### ENDE
- consists of the literal "ENDE" only
- after having sent this message, the server closes the connection


-------------------
the code was successfully tested by establishing a SATP-connection to a server of fernuniversität hagen