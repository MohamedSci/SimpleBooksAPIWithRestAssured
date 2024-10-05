# API Automation Project - Simple Books API

## Project Overview

This project automates API testing for the [Simple Books API](https://simple-books-api.glitch.me), utilizing **Rest Assured**, **Java**, **Selenium**, and **Behavior Driven Development (BDD)** with **Cucumber**. The automation framework supports various API testing scenarios including retrieving book information, submitting and managing orders, and testing API client authentication.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Technologies Used](#technologies-used)
- [API Endpoints Covered](#api-endpoints-covered)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [Execution](#execution)
- [Cucumber Reports](#cucumber-reports)
- [Contributing](#contributing)
- [License](#license)

---

## Technologies Used

- **Java**: Programming language used for writing automation scripts.
- **Rest Assured**: Library for automating REST API requests.
- **Selenium**: For simulating browser actions if required during authentication.
- **Cucumber**: For Behavior Driven Testing (BDD) approach.
- **Test NG**: For executing test cases and assertions.
- **Maven**: Build tool used to manage project dependencies and plugins.

---

## API Endpoints Covered

### 1. Status API
- **Endpoint**: `GET /status`
- **Description**: Returns the status of the API.

### 2. List of Books
- **Endpoint**: `GET /books`
- **Query Parameters**:
  - `type`: fiction or non-fiction (optional)
  - `limit`: number between 1 and 20 (optional)
- **Description**: Retrieves a list of available books.

### 3. Get Single Book
- **Endpoint**: `GET /books/:bookId`
- **Description**: Fetches detailed information about a specific book.

### 4. Submit Order
- **Endpoint**: `POST /orders`
- **Description**: Submits a new order.
- **Authentication**: Requires an API token.
- **Request Body**:
  ```json
  {
    "bookId": 1,
    "customerName": "John"
  }
  ```

### 5. Get All Orders
- **Endpoint**: `GET /orders`
- **Description**: Retrieves a list of all submitted orders.
- **Authentication**: Requires an API token.

### 6. Get Single Order
- **Endpoint**: `GET /orders/:orderId`
- **Description**: Fetches details about a specific order.
- **Authentication**: Requires an API token.

### 7. Update Order
- **Endpoint**: `PATCH /orders/:orderId`
- **Description**: Updates an existing order.
- **Authentication**: Requires an API token.
- **Request Body**:
  ```json
  {
    "customerName": "John"
  }
  ```

### 8. Delete Order
- **Endpoint**: `DELETE /orders/:orderId`
- **Description**: Deletes an existing order.
- **Authentication**: Requires an API token.

### 9. Register API Client
- **Endpoint**: `POST /api-clients`
- **Description**: Registers a new API client.
- **Request Body**:
  ```json
  {
    "clientName": "Postman",
    "clientEmail": "example@example.com"
  }
  ```

---

## Project Structure

```plaintext
src/      
├── test/
    ├── resources/
    │   └── features/
    │       └── features.feature
│   └── java/
│       ├── helper/                   # BDD feature files
│                └── BookClo.java
                  └── OrderClo.java  
│       ├── runner/
                  └── RunCucumberTest.java                      # Cucumber test runner
│       └── steps/                       # Step definitions for feature files
│           └── Steps.java              # Pre/post execution hooks
```

- **`features/`**: Holds the Gherkin (.feature) files that describe API test cases in BDD format.
- **`steps/`**: Contains the step definition classes where feature file steps are implemented.
- **`runner/`**: Cucumber runners to trigger BDD tests.
- **`helper/`**: Utility classes for token management, hooks, and other reusable logic.

---

## Setup Instructions

### Prerequisites

1. **Java JDK 8+**: Ensure Java is installed on your machine. Check by running `java -version`.
2. **Maven**: Required for managing dependencies and building the project. Install and verify by running `mvn -version`.
3. **IDE (e.g., IntelliJ IDEA or Eclipse)**: Recommended for developing and running the project.
4. **Internet Connection**: Required for fetching Maven dependencies.

### Steps to Setup

1. Clone the repository:
    ```bash
    git clone https://github.com/your-username/simple-books-api-automation.git
    cd simple-books-api-automation
    ```

2. Install project dependencies:
    ```bash
    mvn clean install
    ```

3. Configure environment variables for API token management if necessary (optional).

---

## Execution

### Run All Tests

To run all tests using Cucumber, execute the following Maven command:

```bash
mvn test
```

### Running Specific Features

You can run specific feature files by specifying the Cucumber tag:

```bash
mvn test -Dcucumber.options="--tags @orders"
```

---

## Cucumber Reports

Cucumber generates HTML reports by default. After running the tests, you can find the generated reports in the `target/cucumber-reports/` directory.

- **HTML Report**: Open `target/cucumber-reports/cucumber-html-reports/overview-features.html` to view test results in the browser.

---

## Contributing

Feel free to submit issues or pull requests if you'd like to contribute to the project. Make sure to follow the coding standards and write tests for any new features or changes.

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## [Buy me a Coffee☕](<https://ko-fi.com/mohamedsaidibrahim>)

If you enjoy this content and want to support me, feel free to [buy me a coffee](<https://ko-fi.com/mohamedsaidibrahim>)


Happy testing! 🎉
