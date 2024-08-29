# PhoneNumResolver

## Getting start

#### Running the Application
To start the application, run the following commands from the `PhoneNumResolver` directory:
1. `mvn clean install`
2. `java -jar target/PhoneNumResolver-0.0.1-SNAPSHOT.jar`  


#### Running tests
For testing and get result, run the following commands from the `PhoneNumResolver` directory:
1.  `mvn clean test` - Cleans the previous test outputs, runs the unit tests, and generates test reports.
2.  `mvn allure:report` - Generates an Allure report from the test results.
3.  `mvn allure:serve` - Opens a local server to display the Allure report in a web browser.