# SeleniumExample

This project was developed using Selenium with Java using Intellij.

## Setup:
1. Download the Selenium Chromedriver and extract the binary (https://sites.google.com/a/chromium.org/chromedriver/downloads)
2. Install and launch Intellij
3. With Intellij open the SeleniumExample project
3. Open the Maven view and select "Reimport all Maven projects"
4. Open MainTest.java and update line 22 to contain the locaiton of the chromedriver binary

## Running:
- Navigate to the "MainTest.java" file in intellij and select run 'MainTest'
or
- Navigate to the "MainTest.java" file in intellij and press CMD + Shift + R

## Notes:
### Predefined acceptance criteria
- testSuccessfulSearchForProducts
- testUnSuccessfulSearchForProducts
Cover the 2 predefined acceptance criteria

## Additional acceptance criteria
- testSignInWithValidCredentials:
        - When a customers logs in with valid credentials on the web site, then the your account screen is displayed
- testCreateAnAccount:
        - When a customers is on the sign page, then they can choose to create a account and enter details
These are the 2 additional acceptance criteria