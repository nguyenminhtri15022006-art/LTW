package com.example.webapp;
import com.example.webapp.dto.*;
import com.example.webapp.entity.*;
import com.example.webapp.repository.*;
import com.example.webapp.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, properties = {
 "spring.datasource.url=jdbc:h2:mem:repositories;DB_CLOSE_DELAY=-1",
 "spring.datasource.driver-class-name=org.h2.Driver",
 "spring.datasource.username=sa", "spring.datasource.password=",
 "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
class BootRepositoryTest {
 @Autowired ProductService products;
 @Autowired CategoryRepository categories;
 @Autowired ProductRepository repository;
 @Autowired JpaUserStore users;
 @Autowired UserRepository userRepository;
 @BeforeEach void clear() {
  repository.deleteAll(); categories.deleteAll(); userRepository.deleteAll();
 }
 @Test void springDataCrudPaginationAndHomePreserveRelationshipAndImages() {
  Category c = categories.saveAndFlush(new Category(0, "Category"));
  for (int i=1; i<=13; i++) {
   Product p = new Product(); p.setName("Product "+i); p.setPrice(BigDecimal.TEN);
   p.setStock(i); p.setCategory(c); p.setCreatedAt(LocalDateTime.of(2026,1,1,0,0).plusDays(i/3));
   repository.saveAndFlush(p);
  }
  assertEquals(6, products.page(1).size()); assertEquals(6, products.page(2).size());
  assertEquals(1, products.page(3).size()); assertEquals(10, products.newest().size());
  var d = products.newest().get(0); assertEquals("Product 13", d.getName());
  assertEquals("Product 12", products.newest().get(1).getName());
  assertEquals("Category", d.getCategoryName());
  var created = d.getCreatedAt(); d.setImage("retained.png"); products.save(d);
  d.setImage(null); d.setName("Edited"); products.save(d);
  var actual = products.getById(d.getId());
  assertEquals("retained.png", actual.getImage()); assertEquals(created, actual.getCreatedAt());
  products.delete(d.getId()); assertNull(products.getById(d.getId()));
 }
 @Test void otpAttemptCommitsAndActivationIsSingleUseThroughSpringData() {
  var mail = new UserServiceTest.CaptureMail();
  var service = new UserServiceImpl(users, mail);
  RegisterDTO d = new RegisterDTO(); d.setUsername("springuser"); d.setFullName("Spring");
  d.setPassword("Password123"); d.setEmail("spring@example.com");
  service.register(d);
  String otp = mail.otp; String wrong = otp.equals("000000") ? "000001" : "000000";
  assertThrows(ValidationException.class, () -> service.activate(d.getEmail(), wrong));
  assertEquals(1, users.findByEmail(d.getEmail()).getActivationAttempts());
  service.activate(d.getEmail(), otp);
  assertNotNull(service.login(new LoginDTO(d.getUsername(), d.getPassword())));
  assertThrows(ValidationException.class, () -> service.activate(d.getEmail(), otp));
 }
}
