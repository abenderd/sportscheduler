package br.com.abenderd.sportscheduler;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import br.com.abenderd.sportscheduler.entity.Schedule;
import io.restassured.RestAssured;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostScheduleTests {

  static String baseURI = "http://localhost:8083/sport-scheduler/v1";
  private Schedule scheduleRequestBody;

  @BeforeAll
  public static void beforeClass() {
    RestAssured.baseURI = baseURI;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  @Test
  void shouldCreateScheduleTest() {
    given()
        .header("Content-Type", "application/json")
        .body(scheduleFullFilledBodyBuilder())
        .when().log().all()
        .post("/schedules")
        .then().log().all()
        .body(Matchers.emptyOrNullString()).statusCode(201);
  }

  @Test
  void shouldCreateScheduleWithFieldSport50CharactersLengthTest() {
    scheduleFullFilledBodyBuilder();

    scheduleRequestBody.setSport(RandomStringUtils.randomAlphabetic(50));

    given()
        .header("Content-Type", "application/json")
        .body(scheduleRequestBody)
        .when().log().all()
        .post("/schedules")
        .then().log().all()
        .body(Matchers.emptyOrNullString()).statusCode(201);
  }

  @Test
  void shouldNotCreateScheduleMissingMandatoryFieldPlaceTest() {
    given()
        .header("Content-Type", "application/json")
        .body(scheduleMissingMandatoryFieldPlaceBodyBuilder())
        .when().log().all()
        .post("/schedules")
        .then().log().all()
        .body("[0].message", equalTo("Field 'place' is mandatory."))
        .body("[1].message", Matchers.emptyOrNullString())
        .statusCode(400);
  }

  @Test
  public void testRequestBodyValidatingUserField() throws Exception {
    scheduleFullFilledBodyBuilder();

    scheduleRequestBody.setSport("");

    given()
        .header("Content-Type", "application/json")
        .body(scheduleRequestBody)
        .when().log().all()
        .post("/schedules")
        .then().log().all()
        .body("[0].message", equalTo("Field 'sport' is mandatory."))
        .body("[1].message", Matchers.emptyOrNullString())
        .statusCode(400);

    scheduleRequestBody.setSport(null);

    given()
        .header("Content-Type", "application/json")
        .body(scheduleRequestBody)
        .when().log().all()
        .post("/schedules")
        .then().log().all()
        .body("[0].message", equalTo("Field 'sport' is mandatory."))
        .body("[1].message", Matchers.emptyOrNullString())
        .statusCode(400);

    scheduleRequestBody.setSport(RandomStringUtils.randomAlphabetic(51));

    given()
        .header("Content-Type", "application/json")
        .body(scheduleRequestBody)
        .when().log().all()
        .post("/schedules")
        .then().log().all()
        .body("[0].message", equalTo("Field 'sport' is overpassing 50 characters limit."))
        .body("[1].message", Matchers.emptyOrNullString())
        .statusCode(400);

    given()
        .header("Content-Type", "application/json")
        .body(scheduleMissingMandatoryFieldSportBodyBuilder())
        .when().log().all()
        .post("/schedules")
        .then().log().all()
        .body("[0].message", equalTo("Field 'sport' is mandatory."))
        .body("[1].message", Matchers.emptyOrNullString())
        .statusCode(400);
  }

  private Schedule scheduleFullFilledBodyBuilder() {
    scheduleRequestBody = Schedule.builder().place("Derla").sport("Volei")
        .appointmentDate(LocalDateTime.now().plusDays(15).truncatedTo(
            ChronoUnit.SECONDS)).description("Volei quinzenal no Derla.").build();

    return scheduleRequestBody;
  }

  private Schedule scheduleMissingMandatoryFieldPlaceBodyBuilder() {
    return Schedule.builder().sport("Volei")
        .appointmentDate(LocalDateTime.now().plusDays(15).truncatedTo(
            ChronoUnit.SECONDS)).description("Volei quinzenal no Derla.").build();
  }

  private Schedule scheduleMissingMandatoryFieldSportBodyBuilder() {
    return Schedule.builder().place("Quadra de Volei Fis")
        .appointmentDate(LocalDateTime.now().plusDays(15).truncatedTo(
            ChronoUnit.SECONDS)).description("Volei quinzenal no Derla.").build();
  }

}