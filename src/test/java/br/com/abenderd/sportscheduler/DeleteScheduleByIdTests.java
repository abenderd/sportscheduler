package br.com.abenderd.sportscheduler;

import static io.restassured.RestAssured.when;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DeleteScheduleByIdTests extends ScheduleAbstractTest {

  @BeforeAll
  public static void beforeClass() {
    RestAssured.baseURI = baseURI;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    createSchedule();
  }

  @Test
  @Order(1)
  void shouldDeleteScheduleByIdTest() {
    when()
        .delete("schedules/" + scheduleId)
        .then().log().all()
        .assertThat()
        .body(Matchers.emptyOrNullString())
        .statusCode(204);
  }

  @Test
  @Order(2)
  void shouldNotDeleteScheduleByNonExistentIdTest() {
    when()
        .delete("schedules/" + scheduleId)
        .then().log().all()
        .assertThat()
        .body(Matchers.emptyOrNullString())
        .statusCode(404);
  }

  @AfterAll
  static void shouldNotGetScheduleByIdTest() {
    when()
        .get("schedules/" + scheduleId)
        .then().log().all()
        .assertThat()
        .body(Matchers.emptyOrNullString())
        .statusCode(404);
  }

}
