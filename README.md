# CSC 480 OpenLiberty Hello REST

This sandbox can be launched with `mvn liberty:dev`

## Main Files

### [RestApplication.java](src/main/java/piesarentsquare/rest/RestApplication.java)

This class extends the `Application` class, which groups all other RESTful classes in its WAR file under the path specified in `@ApplicationPath`

In this application `@ApplicationPath` is set to `"\api"`, meaning all REST resources will be accessed under the parent path http://localhost:9081/api

### [Data.java](src/main/java/piesarentsquare/rest/Data.java)

The Data class, annotated with `@Path` represents a route, in this case, http://localhost:9081/api/data, because it is under RestApplication's umbrella

Routes can have

* Resources which have to be `@Inject`ed into the server in order for api calls to affect it.
* HTTP GET, PUT, POST, and DELETE methods.
```java
@GET
@Produces(MediaType.TEXT_PLAIN)
public int getStuff() {
    return resource.getValue();
}
```
These methods can have their own sub-path. e.g. http://localhost:9081/api/data/get-this-thing, however, this is not required if there is only one method of this HTTP type in a given route (only one GET, only one POST).

HTTP methods can `@Consume` and/or `@Produce` values of a given mime type
```java
@PUT
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public Response update(@QueryParam("value") Integer v) {
    if (v == null)
        return fail("invalid value");
    resource.setValue(v);
    return success("value was updated");
}
```
`@Consumes` most commonly takes either url encoded for queries like `curl -X PUT http://localhost:9081/data/api?value=9`,

or json `curl -d '{"value":9}' -H "Content-Type: application/json" -X PUT http://localhost:9081/data/api`

When using url encoding, parameters should be annotated with `@QueryParam("name-of-the-variable")`

When using json, (as far as i can tell) there can only be one parameter (except a path parameter discussed below), which jsonb will try to convert the json object to
```java
@GET
@Consumes(MediaType.APPLICATION_JSON)
public int doThing(SomePOJOClass pojo /* automatically converted from json*/) {
...
```

`@PathParam` can be used in both and is used for the case you want to do a query like
`curl -X POST http://localhost:9081/api/data/someuserid?name=john` and is used in conjuction with a `@Path("/{pathvariablename}")`
```java
@POST
@Path("/{userid}")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public Response addUser(@PathParam("userid") String uid, @QueryParam("name") String name) {
    db.addUser(uid, name);
}

```
    
### [Resource.java](src/main/java/piesarentsquare/rest/Resource.java)

`@Inject`ed state needs to be POJO classes marked by the `@ApplicationScoped` annotation. They should also be thread safe.

## Config Files

### [pom.xml](pom.xml)

There are three points of interest in the project file.

* The packaging type must be set to war to build a web app servable by openliberty
    ```xml
    <packaging>war</packaging>
    ```

* You must add the dependencies for the jarkartaEE api and microprofile
    ```xml
    <dependency>
        <groupId>jakarta.platform</groupId>
        <artifactId>jakarta.jakartaee-api</artifactId>
        <version>10.0.0</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.eclipse.microprofile</groupId>
        <artifactId>microprofile</artifactId>
        <version>6.1</version>
        <type>pom</type>
        <scope>provided</scope>
    </dependency>
    ```
    note: the `provided` scope makes these dependencies available to the container at runtime

* Build should include both the OpenLiberty and maven war plugins
    ```xml
    <plugin>
        <groupId>io.openliberty.tools</groupId>
        <artifactId>liberty-maven-plugin</artifactId>
        <version>3.10.3</version>
    </plugin>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <version>3.3.2</version>
    </plugin>
    ```
### [server.xml](src/main/liberty/config/server.xml)
server.xml is the OpenLiberty server config file. 

* It enables server features (the pom `<scope>provided</scope>` dependencies),
* sets the http(s) port(s)
* and sets the webapp(s) to serve
    ```xml
    <webApplication contextRoot="/" location="testapp.war" />
    ```
    note: the `location` should match the `<finalName>` in pom.xml's build tag
* as well as various other things

### [microprofile-config.properties](src/main/resources/META-INF/microprofile-config.properties)
This is the config for microprofile. It can be left blank, but ommiting it will generate  error notifications