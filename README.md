
# Multi-threaded WebCrawler API
A **WebCrawler** application that parses webpages and searches for keywords, the application also gives out statistics on amount of keywords found in a webpage.


To run this app, install the requirements below, then clone app this using command:
`git clone https://github.com/alialiusefi/WebCrawler`  then run the script `run.sh`

Installation Requirements:
    * Git
    * Java 11
    * Gradle
    * Docker

To change the following default configuration parameters (`thread-count`,`default-link-depth`,`default-max-visited-pages`),
please edit the parameters in `application.yml` file that is located in `application/src/main/resources/application.yml` 
    
To send requests to the api, visit the swagger documentation endpoint `localhost:8080/swagger-ui.html`
