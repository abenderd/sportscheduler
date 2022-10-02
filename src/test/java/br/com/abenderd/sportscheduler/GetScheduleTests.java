package br.com.abenderd.sportscheduler;

import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GetScheduleTests {

  static String baseURI = "http://localhost:8083/sport-scheduler/v1";

  @BeforeAll
  public static void beforeClass() {
    RestAssured.baseURI = baseURI;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  @Test
  void shouldGetAllSchedules() {
    when()
        .get("/schedules")
        .then()
        .log()
        .all()
        .assertThat()
        .body(matchesJsonSchemaInClasspath("schemas/GetSchedulesSchema.json"));
  }

}
