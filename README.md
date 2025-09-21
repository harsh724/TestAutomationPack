🧪 Test Automation Pack

Test Automation Pack is a hybrid automation framework built using the Page Object Model (POM) and Data-Driven testing approaches. It supports both UI and API test automation using Selenium, Rest Assured, and TestNG, and is managed via Maven for easy dependency management and build execution.

🚀 Features

✅ Hybrid model combining Page Object Model and Data-Driven Testing

🌐 UI automation using Selenium WebDriver

🔗 API testing with Rest Assured

🧪 Test orchestration using TestNG

📦 Maven-based build and dependency management

🔄 Easy integration with CI/CD tools like Jenkins or GitHub Actions

📊 Support for test reporting (via TestNG / Extent Reports)

🛠️ Tech Stack
Tool/Library	Purpose
Java	Programming language
Selenium WebDriver	UI Test Automation
Rest Assured	API Test Automation
TestNG	Test Execution and Reporting
Maven	Build and Dependency Management

```TestAutomationPack/
├── src/
│   └── test/
│       └── java/
│           ├── PageControllers/         # Page Object classes
│           ├── pages/                   # Page Object classes
│           ├── payloads/                # Contains JsonPayloads for API testing
│           ├── resources/               # Configuration files (e.g., config.properties)
│           ├── services/                # API Service classes (using Rest Assured)
│           ├── testbase/                # TestBase class (for setup and teardown)
│           ├── testcase/                # Test classes (TestNG tests)
│           └── utilities/               # Utility classes (Excel reader, general utilities, etc.)
├── testng.xml                  # TestNG Suite configuration
├── .gitignore                  # List of files to be ignored by Git
├── pom.xml                     # Maven configuration file
└── README.md                   # Project Documentation```


⚙️ Getting Started
Prerequisites

Java 17 or above

Maven

IDE (IntelliJ IDEA / Eclipse)

ChromeDriver (or other browser drivers)

Internet connection for dependency resolution

Installation

Clone the repository:

git clone https://github.com/harsh724/TestAutomationPack.git
cd TestAutomationPack


Install dependencies:

mvn clean install


Run the tests:

mvn test

📄 Configuration

testng.xml: Configure which test suites or classes to run.

config.properties: Define environment-specific configurations (URL, browser, etc.).

Excel/JSON: For data-driven input (if applicable).
