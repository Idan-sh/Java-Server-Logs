# TODO HTTP Server Application

## 1. About
This TODO app allows users to maintain a list of tasks to do. The app allows users to create, update and delete TODOs, with more functions listed below.   

The server will create two log files, in a dedicated logs folder:
* `requests.log`:
      * In charge of logging each incoming request of any type to the server
* `todos.log`:
      * In charge of logging information regarding the todo management
#### Each todo has the below properties:
* `Id:` a unique ID assigned for each To-Do, Starting at 1
* `Title:` short title describing the essence of this TODO
* `Content:` the actual content/description describing what this TODO stands for
* `Due date:` a timestamp (in millisecs) denoting the target time for this TODO to be fulfilled
* `Status:` the status of the TODO as follows,
    * `PENDING` when it is created and before the due date
    * `LATE`    when it is not performed yet, and we are past the due date
    * `DONE`    when the TODO item processing is over
<br />

## 2. Frameworks Used
* `Java` language
* `Spring-Boot` web framework
* `Logback` logging framework
* `SLF4J` Simple Logging Facade for Java
<br />

## 3. Server Behavior
### 3.0 Return
endpoints will return a result in the Json format:
```yaml
{
  result: <result of operation>             (Template, depends on the context)
  errorMessage: <message in case of error>  (String)
}
```
<br />

### 3.1 Server Properties
The server is set to listen on port `9583`.  
This can be changed in the `application.properties` file, under the `server.port` property.
<br />   
<br />

### 3.2 Server Endpoints
#### 3.2.1 Get Health
This is a sanity endpoint used to check that the server is up and running.  
`Endpoint:` /todo/health  
`Method:` GET  
> The response code will be **200**, and the result will be the string **"OK"**.   
<br />

#### 3.2.2 Create New TODO:
Creates a new TODO item in the system.
`Endpoint:` /todo   
`Method:` POST   
`Body:` json object-   
```yaml
{
   title: <TODO title>                 (String)
   content: <TODO description>         (String)
   dueDate: <timestamp in millisecs>   (long number)
}
```
The TODO is created with the status PENDING.   
When a new TODO is created, it is assigned by the server to the next id in turn.    

#### Upon processing the creation, the following will be checked:   
1. Is there already a TODO with this title (TODOs titles are unique)
2. Is the dueDate in the future.   

#### If the operation can be invoked (all verification went OK): 
* The response code will be 200
* The result will hold the newly assigned TODO number

#### If there is an error:   
*  The response will end with 409 (conflict)
*  The errorMessage will be set according to the error:
      * `TODO already exists:` "Error: TODO with the title [<TODO title>] already exists in the system".  
      * `due date is in the past:` “Error: Can’t create new TODO that its due date is in the past”
<br />  
 
#### 3.2.3 Get TODOs Count
Returns the total number of TODOs in the app, according to the given filter.   
`Endpoint:` /todo/size   
`Method:` GET   
`Query Parameter:` status, possible values- `ALL`, `PENDING`, `LATE`, `DONE`.   
   
* The response code will be **200**.
* The result will hold the **number of TODOs** that have the given status.   

If that status is not precisely the above four options (case sensitive) the result will be **400** (bad request).   
<br />   

#### 3.2.4 Get TODOs Data
Returns the content of the todos according to the given status.    
`Endpoint:` /todo/content   
`Method:` GET   
`Query Parameter:` status, possible values- `ALL`, `PENDING`, `LATE`, `DONE`   
`Query Parameter:` sortBy, optional, possible values- `ID`, `DUE_DATE`, `TITLE` (default value: `ID`).   
   
* The response will be a json array.   
* The array will hold json objects that describe a single TODO.  
* The array will be sorted according to the sortBy parameter, in an acending order.
* If no TODOs are available the result is an empty array.
   
Each Json object in the array holds:
```yaml
{
   id: Integer
   title: String
   content: String
   status: String
   dueDate: long (Timestamp in millisecs)
}
```

* The response code will be 200
* The result will hold the json array as described above

In case status or sortBy are not **precisely** as the options mentioned above, case sensitive, the result is **400** (bad request).   
<br />

#### 3.2.5 Update TODO status
Updates a TODO's status property.   
`Endpoint:` /todo  
`Method:` PUT  
`Query Parameter:` id, The TODO ID  
`Query Parameter:` status, The status to update. possible values- `PENDING`, `LATE`, `DONE`  
   
#### If the TODO exists (according to the ID):
* The response code will be **200**   
* Its status will be updated
* The result is the name of the **OLD** state that this TODO was at (any option of PENDING, LATE, or DONE, case sensitive)

#### If no such TODO with that ID can be found
* The response code will be **404** (not found).
* The errorMessage will be: "Error: no such TODO with id <todo number>"
   
#### If the status is not exactly the above-mentioned options (case sensitive)
* The result is **400** (bad request)
<br />
  
#### 3.2.6 Delete TODO
Deletes a TODO object.   
`Endpoint:` /todo  
`Method:` DELETE  
`Query Parameter:` id, The TODO ID   
   
> Once deleted, its deleted id remains **empty**, so that the next TODO that will be created **will not** take this id  
   
#### If the operation can be invoked (the TODO exists):
* The response will end with **200**
* The result will hold the number of TODOs left in the app.

#### If the operation cannot be invoked (TODO does not exist):
* The response will end with **404** (not found)
* The errorMessage will be: "Error: no such TODO with id <todo number>"
