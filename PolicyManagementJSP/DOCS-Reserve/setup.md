# Setup Instructions

## Prerequisites
1. Java 17 JDK
2. Maven 3.8+
3. PostgreSQL 14+
4. Jakarta EE compatible server (Tomcat 10+)

## Database Setup
1. Create the database:
```sql
CREATE DATABASE policydb;
```

2. Create the policies table:
```sql
CREATE TABLE policies (
    id SERIAL PRIMARY KEY,
    policy_number VARCHAR(50) NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);
```

## Application Setup
1. Build the application:
```bash
cd PolicyManagementJSP
mvn clean package
```

2. Deploy to Tomcat:
- Copy the generated WAR file to Tomcat's webapps directory
- Start Tomcat server

## Accessing the Application
- Main application: http://localhost:8080/PolicyManagementJSP
- Legacy interface: http://localhost:8080/PolicyManagementJSP/legacy

## Troubleshooting

### Common Issues
1. Database Connection
   - Check database URL in legacy-config.properties
   - Verify database credentials
   - Ensure PostgreSQL is running

2. Application Deployment
   - Check Tomcat logs
   - Verify WAR file is properly deployed
   - Check for missing dependencies

3. Configuration
   - Verify all properties are set
   - Check for proper file permissions
   - Ensure all required directories exist

## Known Issues
1. No proper error handling
2. No input validation
3. No security measures
4. No logging framework
5. No proper configuration management

## Security Warning
This application is intentionally vulnerable and should NEVER be deployed in production. It is designed for educational purposes only to demonstrate legacy code issues and security vulnerabilities.
