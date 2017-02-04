### FirebaseRepoTest
This is a test project to provide abstractions/facades around Firebase operations.

**Disclaimer: This is a demo**
This is provided as an example, and as such it does not implement all of the firebase features that are available.  It is simply intended to be an example project.  
And yes, the UI is terrible.  Again, demo project.

##Authentication
Firebase authentication is implemented through a facade that wraps around the firebase authentication library.  The facade provides automatic setup and teardown of all associated authentication event handlers, so that all that is required for a developer is to simply declare the authentication facade and call the appropriate method.

#Examples
- Authentication using email address via regular firebase code
- Authentication using email address via abstraction

##Database access
Database access utilizes firebase's the built-in mechanism that automatically serializes/deserializes the relevant entities.
The database abstraction implements a repository interface, which offers and consistent and greatly simplified syntax for firebase data access.

#Examples
- Adding a record
- Removing a record
- Finding a record
- Finding a record and then doing something else with it
