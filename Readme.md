# Average Calculator Microservice Documentation

## Problem Statement

Develop an Average Calculator microservice that exposes a REST API `numbers/{numberId}`. The API should accept qualified number IDs ('p' for prime, 'f' for Fibonacci, 'e' for even, and 'r' for random numbers). The service should fetch numbers from a test server and store them with a window size of 10, ensuring uniqueness. The response should include the fetched numbers, previous and current window states, and the average of the current window.

## Postman Screenshots

### Test Case 1: Prime Numbers

**Request**: `GET http://localhost:8080/numbers/p`

**Response**:
![Prime Numbers Request](Screenshot%202024-06-08%20112537.png)

### Test Case 2: Fibonacci Numbers

**Request**: `GET http://localhost:8080/numbers/f`

**Response**:
![Fibonacci Numbers Request](Screenshot%202024-06-08%20112615.png)

### Test Case 3: Even Numbers

**Request**: `GET http://localhost:8080/numbers/e`

**Response**:
![Even Numbers Request](Screenshot%202024-06-08%20112706.png)


## Conclusion

The microservice successfully fetches numbers from the test server based on the provided qualifiers and calculates the average of the current window. It ensures uniqueness and maintains a sliding window with a size of 10, providing accurate responses for various test cases.
