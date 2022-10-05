package br.com.abenderd.sportscheduler;

import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import java.util.Collections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GetScheduleTests extends ScheduleAbstractTest {

  @BeforeAll
  public static void beforeClass() {
    RestAssured.baseURI = baseURI;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    createSchedule();
  }

  @Test
  void shouldGetAllSchedulesTest() {
    when()
        .get("/schedules")
        .then().log().all()
        .assertThat()
        .body(matchesJsonSchemaInClasspath("schemas/GetSchedulesSchema.json"))
        .statusCode(200);
  }

  @Test
  void shouldGetAllSchedulesFilteringByQueryParameterSportTest() {
    when()
        .get("/schedules?sport=Volei")
        .then().log().all()
        .assertThat()
        .body(matchesJsonSchemaInClasspath("schemas/GetSchedulesSchema.json"))
        .body("[0].sport", equalTo("Volei"))
        .statusCode(200);
  }

  @Test
  void shouldGetEmptySchedulesListFilteringByNonExistentQueryParameterTest() {
    when()
        .get("/schedules?sport=Voley")
        .then().log().all()
        .assertThat()
        .body(matchesJsonSchemaInClasspath("schemas/GetSchedulesSchema.json"))
        .body("", equalTo(Collections.emptyList()))
        .statusCode(200);
  }

}
