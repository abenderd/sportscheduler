package br.com.abenderd.sportscheduler;

import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GetScheduleByIdTests extends ScheduleAbstractTest {

  @BeforeAll
  public static void beforeClass() {
    RestAssured.baseURI = baseURI;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    createSchedule();
  }

  @Test
  void shouldGetScheduleByIdTest() {
    when()
        .get("schedules/" + scheduleId)
        .then().log().all()
        .assertThat()
        .body(matchesJsonSchemaInClasspath("schemas/GetScheduleByIdSchema.json"))
        .body("id", equalTo(scheduleId))
        .body("sport", equalTo(sport))
        .body("place", equalTo(place))
        .body("description", equalTo(description))
        .statusCode(200);
  }

  @Test
  void shouldNotGetScheduleByNonExistentIdTest() {
    when()
        .get("/schedules/nonexistent")
        .then().log().all()
        .assertThat()
        .body(Matchers.emptyOrNullString())
        .statusCode(404);
  }

}
