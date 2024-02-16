## About NRFAWebService

NRFAWebService is the web service that provides electronic tolling services to the Toll gates in Zambia. Point of Sale devices connect to the web service to manage the lifecycle of 
cards purchased by customers. Customers purchase cards from merchants. These merchants also handle the funding of these cards as they serve as agents in an Agency banking model. 
Cards funded are used by customers when they approach the toll gates by tapping the cards to enable the toll gates open. Cards can also be used to purchase tickets from Agents at 
the toll gates who provide point of sale devices for this purchase.
##


## Technical Details

The NRFAWebService mobile application is developed using Java, database is Microsoft SQL

## Install the Java
Before proceeding, make sure your computer has Java installed. Minimum version is Java 8. See Oracle website for documentation on Java installation

## Install the Microsoft SQL
Before proceeding, make sure your computer has Microsoft SQL installed. See guidance online for installtion of Microsoft SQL and its tools.

## Dependency
Generate WAR file using your favorite IDE such as Eclipse or your command prompt/bash. 

Using Eclipse:
Right Click on Project and click on "Export"
Proceed with the steps to generate the war file.
Go to your project Directory and inside Dist Folder you will get war file that you copy on your tomcat webApp Folder.
Start the tomcat.
It automatically extracts the folder from the war file.
