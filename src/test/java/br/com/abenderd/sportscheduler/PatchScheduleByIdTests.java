package br.com.abenderd.sportscheduler;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import br.com.abenderd.sportscheduler.entity.Schedule;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestMethod;

@SpringBootTest
class PatchScheduleByIdTests extends ScheduleAbstractTest {

  @BeforeAll
  public static void beforeClass() {
    RestAssured.baseURI = baseURI;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    postSchedule();
  }

  @Test
  void shouldPartialUpdateScheduleByIdWithFullFilledTest() {
    given()
        .header("Content-Type", "application/json")
        .body(scheduleRequestBody)
        .when()
        .patch("schedules/" + scheduleId)
        .then().log().all()
        .assertThat()
        .body(Matchers.emptyOrNullString())
        .statusCode(204);
  }

  @Test
  void shouldNotPartialUpdateScheduleByNonExistentIdTest() {
    given()
        .header("Content-Type", "application/json")
        .body(scheduleRequestBody)
        .when()
        .patch("/schedules/nonexistent")
        .then().log().all()
        .assertThat()
        .body(Matchers.emptyOrNullString())
        .statusCode(404);
  }

  @Test
  void shouldPartialUpdateScheduleWithoutFieldAppointmentDateTest() {
    given()
        .header("Content-Type", "application/json")
        .body(scheduleWithoutFieldAppointmentDateBodyBuilder())
        .when().log().all()
        .patch("schedules/" + scheduleId)
        .then().log().all()
        .body(Matchers.emptyOrNullString())
        .statusCode(204);
  }

  @AfterAll
  static void shouldGetScheduleByIdTest() {
    when()
        .get("schedules/" + scheduleId)
        .then().log().all()
        .assertThat()
        .body(matchesJsonSchemaInClasspath("schemas/GetScheduleByIdSchema.json"))
        .body("id", equalTo(scheduleId))
        .body("sport", equalTo("Basquete"))
        .body("place", equalTo("Praca Joana D Arc"))
        .body("description", equalTo("Basquete da ABS"))
        .statusCode(200);
  }

  private Schedule scheduleWithoutFieldAppointmentDateBodyBuilder() {
    return Schedule.builder().sport("Basquete")
        .place("Praca Joana D Arc").description("Basquete da ABS").build();
  }

}
