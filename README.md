### Instructions for running the application:

1. Clone the [repository](https://github.com/dandimog/Geniusee/tree/main) to your local machine.
2. Create a Postrgres database.
3. Add your own DB_URL, DB_USERNAME and DB_PASSWORD values to the application.properties file.
4. Run the program with `mvn spring-boot:run`

### Self evaluation checklist and room for improvements:

1. #### Movie Controller
- [x] Basic CRUD operations for `MovieController` 
- [x] Find All by any parameter of an entity using `javax.persistence.criteria` and
  `springframework.data.jpa.domain.Specification` with pagination
- [x] Unit tests for `MovieController` and `MovieService`
- [ ] Integration tests for `MovieRepository`
2. #### Order Controller
- [x] Basic CRUD operations for `OrderController`
- [ ] Find All by any parameter of an entity using `javax.persistence.criteria` and
  `springframework.data.jpa.domain.Specification` with pagination
- [ ] Unit tests for `OrderController` and `OrderService`
- [ ] Integration tests for `OrderRepository` 
3. Other requirements
- [x] DB creating should be implemented with Liquibase
- [x] Host the code on GitHub and provide instructions for running it in readme file
