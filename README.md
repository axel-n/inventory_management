# inventory management system

## Links
[postman collerction](https://www.getpostman.com/collections/c53a6ea89b5ff710d90c)
[online_documentation](https://documenter.getpostman.com/view/6588996/SVfNv9At?version=latest)

## Use docker
1. build docker image `mvn package`
2. run docker container `docker run inventory_management:1.0-SNAPSHOT`

## TODO
##$ Write a simple inventory management system. User should be able to:
- Enter new product (product details are listed below)
- Find product by name/brand
- Update/remove product
- See all leftovers on a separate page (leftovers means product which quantity is less than 5)
- It should be two users - admin (can create, add, remove) and user with read-only access.
### Product details:
- Name
- Brand
- Price
- Quantity

### Non-functional requirements
- All application layers should be covered with tests
- This system should be written using spring boot
- All data should be accessed using REST API.
- application should be compliant with 12factor apps approach (https://12factor.net)
### Bonus Tasks
#### Bonus tasks are optional, but shows more advanced expertise. Feel free to implement any number of those or add and describe something by
yourself.
- Export search result as an xls file
- Application should run in docker container.
- Consider more complex security mechanism like JWT.
- provide virtual configs for 2 envs (staging and prod)
- add instrumentation to your code where you think it's reasonable (a way to check knowledge about metrics, logging etc.)
