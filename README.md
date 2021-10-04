# gorest-api-tests

Gorest is a sample API to exercise API automation tests. It creates fake data and working with real responses.
for more information: https://gorest.co.in/

# Running Tests

## Running Tests Locally
Test runner classes are located in `testRunners` package. In this package `gorestAPITestsInParallel` xml file is located. For local run, select xml file and run it in the code editor.

# Running Tests Parallel
In `gorestAPITestsInParallel.xml` file, change `thread-count` value.

# Changing Retry Count
In `RetryAnalyzer` class, change `maxRetryCount` value. Default value is 5 right now.

# Changing Environment Values
In `BaseSettings` class, change desired parameter. Here is the list of the values:

- "baseURI" => "https://gorest.co.in/"
    - This value is main API URL.
- "usersPath" => "v1/users"
    - This value is used to call on user create, update and delete operations on `UserUtils` class
- "postsPath" => "v1/posts" 
    - This value is used to call post creation operations on `PostUtils` class
- "commentsPath" => "v1/comments"
    - This value is used to call comment creation operations on `CommentUtils` class
- "responseTimeLimit" => "5000"
    - This value is used in every request to validate `Endpoint responds in less than 200 ms.` requirement. Note: 200 ms is very low value, default value is `5000`
- "accessToken" => "YOUR_ACCESS_TOKEN"
    - This value is used in every request as Authentication. Please create your own access token before execution from https://gorest.co.in/
