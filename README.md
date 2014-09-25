TcpChat
=======

A simple Java chat server where clients can connect through TCP and send messages to eachother

Server commands
---------------

* “/quit” - disconnect this client, send information to all
* “/who” - send a string containing the name of all connected clients to this client
* “/nick <nickname>” - add a nick name to this client. A nickname is only valid during the session, duplicates are not allowed, old ones are removed.
* “/help”, listing the available commands
