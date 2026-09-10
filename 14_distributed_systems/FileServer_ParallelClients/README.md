# Simulation of serveral clientside requests to a fileserver via sockets

- a simple fileserver to serve .txt-files to several clients simultaneously

- several clients requesting simultaneously a .txt-file from the fileserver


## Requirements

### Server
- listens on port 7891 for requests
- can process several requests simultaneously
- transmits the requested .txt-file, which is located in the same directory 
- reports the start and the end of the data-transmission to the console (including client-id)


### Clients
- a random number of n clients is started by the program (I limited n to a max of 10)
- all clients request the same .txt-file from the server
- every client established a socket-connection to the server
- server-address and filename are passed as parameters to the main-method (e.g. java ParallelClients localhost textfile.txt)
- each client transmits it's id and the name of the requested .txt-file to the server
- the id of the launched client is printed to the console
- the client prints a message to the console to confirm the successful transaction
