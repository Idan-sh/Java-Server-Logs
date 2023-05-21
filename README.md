# TODO HTTP Server Application
#### Written in Java

## 1. About
This TODO app allows users to maintain a list of tasks they need to do. The app allows users to create, updae and delete TODOs, with more functions listed below.

Each todo has the below properties
* `Id:` a unique ID assigned for each To-Do, Starting at 1
* `Title:` short title describing the essence of this TODO
* `Content:` the actual content/description describing what this TODO stands for
* `Due date:` a timestamp (in millisecs) denoting the target time for this TODO to be fulfilled
* `Status:` the status of the TODO as follows,
    * `PENDING` when it is created and before the due date
    * `LATE`    when it is not performed yet, and we are past the due date
    * `DONE`    when the TODO item processing is over

## 2. Frameworks Used
* `Spring-Boot` web framework
* `Logback` logging framework
* `SLF4J` Simple Logging Facade for Java

## 3. HTTP endpoints
### 3.0 Return
endpoints will return a result in the Json format:
```yaml
{
  result: <result of operation>             (Template, depends on the context)
  errorMessage: <message in case of error>  (string)
}
```
### 3.1 Get
