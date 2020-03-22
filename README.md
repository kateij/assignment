## General info
This project is a Restful web service for Products.

## Technologies
Project is created with:
* Java 8
* Spring Boot 2.2.5.RELEASE
* Spring Data Rest

## Setup
The project can be cloned and imported into intellJ or eclipse as a Maven project.

## Data Persistence
The application uses an in-memory database The h2 database is used and configured in application.properties.

## Running the application
The application can be run using Maven to run it as a Spring Boot application. Use the following command:

$ mvn spring-boot:run

The application can also be run from the application JAR. It can be run from the target after the application
has been built using Maven. Using the command

mvn clean install

The application JAR can be run with the command:

java -jar target/assignment-0.0.1-SNAPSHOTjar

## API
The API consists of 4 end-points
#### Get all products
The following end-point given as a GET request will return all the end-points held in the database:

/products

#### Create a product
The following end-point given as a POST request will create a new product which
will be stored in the database:

/products

The product data is given in the body of the requests in JSON. For example

The following end-point given as a GET request will return all the end-points held in the database:

{
  "active": true,
  "name": "milk",
  "price": 1.00
}

Only the active, name and price fields should be given in the JSON request. The
product sku is generated internally when the product is created. The createdDate
is generated in the application using Spring Auditing. See the AuditorAwareImpl
class and the AuditorEntity which has the createdDate field. The Auditor is configured in the AssignmentApplication
class. The Product domain class extends the AuditorEntity class.

The created product is given in the response:

{
  "createdDate": "2020-03-22T15:47:36.725+0000",
  "sku": 1,
  "name": "milk",
  "price": "1.00",
  "active": true
}

#### Update a product
The sku of the product to update is given in the PUT request URL:

/products/{sku}

For example,
/products/1

The update data is given in JSON. For example, changing the product's price:

{
  "price": 12.00
}

The updated product is given in the response.

#### Delete a product
This is a soft delete. A field on the Product entity is used to hold the active
status of a product. This is the _active_ field.
The sku of the product to be deleted is given in the DELETE request URL:

/products/{sku}

For example,
/products/1

The deleted product is given in the response.

#### General
When the product identified in the the update and delete request is not found
a ProductNotFoundException is given. This is handled by the RestExceptionHandler
class and an HTTP status 404 (not found) is given. An error message is also given.

When an invalid price is given an HTTP status 400 (bad request) is given. An error
message is also given. An invalid price is a non-numeric price value.

The error messages are hard-coded. They should be handled using a Spring message
source and internalialised message.properities.

## Swagger
Swagger can be used to access the REST end-points. Use the following URL:

http:http://localhost:8080//swagger-ui.html#

The latest version of SpringFox Swagger 2 does not work with the current version
Spring Boot. The Swagger version is 2.9.2. Rather than using an earlier version
Spring Boot which was found to be compatible a snapshot version of SpringFox
Swagger is being used. This is version, 3.0.0-SNAPSHOT. This version has not
been uploaded to Maven Central. The repository for accessing this version is
given in the application POM.

## HAL Explorer
The application has also been setup to use the HAL explorer. Use the following
URL to access it:

http://localhost:8080

The HAL browser is not being used because this has been replaced by the HAL explorer.
