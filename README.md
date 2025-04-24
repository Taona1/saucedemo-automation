# SauceDemo Automation Assessment (Java with Extent Reports and Lombok)

## Overview
This project contains automated tests for the SauceDemo e-commerce website using Selenium WebDriver, TestNG, and the Page Object Model (POM) design pattern. Tests are enhanced with Extent Reports for detailed HTML reporting, including screenshots for failed tests. Lombok is used to reduce boilerplate code. The tests cover:
- Successful login
- Failed login
- Dashboard navigation
- Adding items to cart
- Verifying cart contents
- Checkout process
- Completing checkout
- End-to-end flow (login to logout)

## Setup Instructions
1. **Prerequisites**:
   - Java 11+
   - Maven 3.6+
   - Chrome browser
   - ChromeDriver (compatible with your Chrome version)

2. **Installation**:
   - Clone the repository or unzip the project files
   - Install dependencies:
     ```bash
     mvn clean install
     ```

3. **Running Tests**:
   - Navigate to the project directory
   - Run all tests:
     ```bash
     mvn test
     ```
   - Run specific tests:
     ```bash
     mvn test -Dtest=SauceLabs.FirstTest#testEndToEndFlow
     ```

4. **Viewing Reports**:
   - After running tests, find the Extent Reports in the `reports/` directory
   - Open the latest `ExtentReport_YYYYMMDD_HHMMSS.html` file in a browser to view the test results
   - Screenshots for failed tests are saved in `reports/screenshots/`

5. **Project Structure**:
   - `src/main/java/SauceLabs/pom/`: Page Object Model classes (`LoginPage`, `DashboardPage`, etc.)
   - `src/test/java/SauceLabs/`: Test classes (`FirstTest`)
   - `src/main/java/SauceLabs/`: Base test class (`BaseTest`)
   - `pom.xml`: Maven configuration
   - `testng.xml`: TestNG configuration
   - `reports/`: Extent Reports and screenshots

## Approach
- **Page Object Model**: Each page has a dedicated class with locators (using `@FindBy`) and methods, initialized with PageFactory.
- **Lombok**: Used to generate getters for page classes and `BaseTest`, reducing boilerplate code.
- **Exception Handling**: WebDriverWait (10-second timeout) ensures elements are visible/clickable. Implicit waits provide stability.
- **Assertions**: TestNG assertions verify each step (e.g., URLs, element text, item counts, prices).
- **Extent Reports**: Generates HTML reports with step-by-step logs, pass/fail statuses, and screenshots for failures.
- **Clean Code**: Modular design, meaningful method names, and parameterized test data.
- **Test Independence**: Self-contained tests with setup/teardown in `BaseTest`.
- **Headless Mode**: Tests run in headless Chrome for CI/CD compatibility.

## Handling Challenges
- **Dynamic Elements**: WebDriverWait handles dynamic content.
- **Slow Page Loads**: Implicit and explicit waits manage load times.
- **Test Data**: Credentials and checkout info are parameterized.
- **Lombok Compatibility**: Configured with `--add-exports` to resolve Java 11 module access issues.
- **Cross-browser Testing**: Chrome-focused; extensible to Firefox with WebDriverManager.
- **Parallel Execution**: Enabled via `testng.xml` (`parallel="methods"`).

## Bonus Features
- **Extent Reports**: Detailed HTML reports with logs and screenshots.
- **Lombok**: Reduces boilerplate code with annotations like `@Getter`.
- **Parallel Execution**: Configured in `testng.xml` with `thread-count="2"`.
- **CI/CD Integration**: Compatible with GitHub Actions or Jenkins.
- **Headless Mode**: Enabled for CI environments.

## Running in CI/CD
Add a `.github/workflows/ci.yml` file for GitHub Actions:
```yaml
name: CI Pipeline
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
      - name: Run Tests
        run: mvn test
      - name: Archive Reports
        uses: actions/upload-artifact@v3
        with:
          name: extent-reports
          path: reports/
```
This runs tests on every push and archives Extent Reports for download.

## Troubleshooting
- **Lombok Error**: If you encounter `cannot access com.sun.tools.javac.processing`, ensure the `maven-surefire-plugin` includes:
  ```xml
  <argLine>--add-exports jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED</argLine>
  ```
- **IDE Support**: For Lombok to work in your IDE (e.g., IntelliJ, Eclipse), install the Lombok plugin and enable annotation processing.
```