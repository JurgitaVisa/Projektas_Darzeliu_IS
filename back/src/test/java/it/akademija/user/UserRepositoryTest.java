/*
 * package it.akademija.user;
 * 
 * import static org.assertj.core.api.Assertions.assertThat;
 * 
 * import org.junit.Test; import org.junit.runner.RunWith; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager; import
 * org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 * 
 * import it.akademija.App; import it.akademija.role.Role;
 * 
 * @RunWith(SpringJUnit4ClassRunner.class)
 * 
 * @SpringBootTest(classes = { App.class, UserController.class }, webEnvironment
 * = SpringBootTest.WebEnvironment.RANDOM_PORT)
 * 
 * public class UserRepositoryTest {
 * 
 * @Autowired private TestEntityManager entityManager;
 * 
 * @Autowired private UserDAO userDAO;
 * 
 * @Test public void saveUser() { User user = new User(Role.ADMIN, "admin",
 * "admin", "admin@admin.lt", "admin@admin.lt", "admin@admin.lt");
 * entityManager.persistAndFlush(user);
 * assertThat(user.getUserId()).isNotNull(); }
 * 
 * }
 */