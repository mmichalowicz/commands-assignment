# Getting Started

### Developer considerations
* Json output is not perfect according to specs: Array of top commands per state wrapped in a topStateCommands JSon parent
* Skipped Javadocs. Wanted to focus on the code. I adapt verbosity of Javadocs per coding standards (per company)
* Used Spring Initialzr to get me started with a comprehensive Maven pom.xml file
* Skipped slf4j and used log4j2 (I understand slf4j may be more appropriate).. Wanted to use lazy logging lambdas
* commands.json at the root is the file I used calling from PostMan with application/json as content-type
* commands.json was edited from the document I was sent since quotes were wrong, commands had spaces in them
* results.json at the root is the result from the body of the response in POSTMAN
* I used Lombok annotations to generate builders, and get'ers/set'ers which requires lombok plugin in IntelliJ/Eclipse
* Lombok will compile using mvn clean install however if the plugin isn't installed, 
    the IDE won't see the generated get'ers and set'ers and the code won't compile in the IDE
* mvn clean install works
* mvn spring-boot:run works
* logging is verbose (at info level) and I would crank down verbosity/levels before QA
* There are minimal JUnits however I am comfortable with many many flavors of JUnits, mocking, Spring testing
* When working for Comcast, a new module I created I brought to 100% JUnit coverage (above the call of duty)
* A forced delay was added after adding each command to show that processing times are being updated
* I wasn't quite sure what processing time means, but I inferred it as when a command for a state is encountered
* I used AssertJ fluent assertions for JUnits; I learned AssertJ from working at Comcast
* CommandServiceImpl methods are too big, I need to refactor to componentize methods better
* Granularity of the timestamps is seconds since epoch (default TimeZone). Just picked a convenient precision.

## NOTE: Misunderstood requirements, 
* The code produces the necessary output, however I misunderstood the requirements
* I thought the top commands nationally was to be pulled from the top commands per state (which is incorrect)
* It needs to be the aggregate of the commands across all states
* This would allow a non-top command for a state to be a higher national aggregate overall. 
* I have to timebox this coding effort, otherwise I'd refactor to track aggregate nationally better 
* I would then potentially share code through inheritance or generics
* _Tracking by state should have the same pattern as tracking nationally_
