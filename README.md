this Project is the Sessions microservice project and it's used to create a session for each chatting connection and it's contain the following API's
saveSession, and this is for creating a new Session for the chatting connection and in this session , it's create a sessionId, sessionName by userName and session creating date.
after creating the session, the api send a request to the message microservice to create a message with the sessionId for each request.
renameSession, to change the sessionName.
deleteSession, to delete the session and delete the messages which associated to this SessionId.
get all the messages
