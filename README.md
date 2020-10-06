# Word Guessing Game Multi-Client
### Team Project for Sofware Design - CS 342 @UIC
    
### Description:
- Create a server(local ip and port provided in code)
- Create as many clients as needed 
    - Each client can pick a different category. ( 3 categories available )
    - Client guesses a letter, the server shall respond with either 'Correct Guess'  
      else will show the number of chances left.
- The game is only won by a client (player) only when they have guessed at least 
  one word from each of the categories available.
 
### Implementation
-   GUI created in JavaFx
-   2 seperate projects 
    1. Server  
        - Server runs its own thread and handles each client on a seperate thread. 
    2. Client
        - Each client connects and communicate the with server on a seperate thread.  
-   Networks are created using Java Sockets
-   Only used Java 8 libraries
   
##### Import both P4Client and P4Server as *Maven* projects. 
- Refer to *pom.xml* for further details on configuration.


#### Authors:
* [Aryan Thaman](https://github.com/aryanthaman)
* [Siddarth Menon](https://github.com/s1ddarth)
* [Wajahat Khan](https://github.com/wajahatkhan97)
* [Varun Subramanian](https://github.com/Varun221999)
