## Changes that needs to be added.
1. Add tests: We need to create tests that use EmbededKafka or Testcontainer.
These tests will be used to verify that our listeners can receive the messages.
Also in our function that listens on the online queue, we need to mock the call
to 3d party service. We want to test only our code.
2. I need better error handling. We need to log the error in a good way.
I can also create some custom error with which it will be easier to have better
error handling.
3. There is a problem with the test when there are too many request. Sending the 
data for verification to the 3rd party with rest template starts to return
error 504.
4. Extract some more hardcoded values to the application properties
so that we can configure them when needed.
4. Add the service to the docker compose. We should be able to start
all the services including this one with docker-compose up. This will
include adding a Dockerfile which will describe how to build our
service in an image.