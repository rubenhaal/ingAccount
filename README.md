# IngAccount API

Launch like a normal springboot application.

Url for the openapi:
http://localhost:8080/swagger-ui.html

This project will be a CRUD to create an Account

Account entity

    - UUID : 36 digits
    - Type: Savings or Current. Enum
    - Opening date:  Date . Now or 30 days in the past
    - Temporary Account : boolean
    - Clousure date : mandatory if temporary 
                    -   Cant be erliar than opening + 2 months
                    -  When it is change to  temporary can not be 
                    earlier than the modification  + 1 mont
    - Initial deposit : Cant be negative -> >0
    - Holder: Person entity
 
Person entity:
    
    - First Name: minimum 3 max 35
    - Last name : minimum 3 max 35
    - Date of birth : must be >=18
    - Email: must be a valid format

**Endpoints Account:**

    - Create
    - Read: Receive an UUID if not foudn return the expected status code
    - Update: if not valid date error
    - Delete

