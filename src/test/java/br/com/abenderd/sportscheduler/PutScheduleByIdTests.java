package br.com.abenderd.sportscheduler;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import br.com.abenderd.sportscheduler.entity.Schedule;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PutScheduleByIdTests extends ScheduleAbstractTest {

  @BeforeAll
  public static void beforeClass() {
    RestAssured.baseURI = baseURI;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    postSchedule();
  }

  @Test
  void shouldUpdateScheduleByIdTest() {
    given()
        .header("Content-Type", "application/json")
        .body(scheduleRequestBody)
        .when()
        .put("schedules/" + scheduleId)
        .then().log().all()
        .assertThat()
        .body(Matchers.emptyOrNullString())
        .statusCode(204);
  }

  @Test
  void shouldNotUpdateScheduleByNonExistentIdTest() {
    given()
        .header("Content-Type", "application/json")
        .body(scheduleRequestBody)
        .when()
        .put("/schedules/nonexistent")
        .then().log().all()
        .assertThat()
        .body(Matchers.emptyOrNullString())
        .statusCode(404);
  }

  @Test
  void shouldNotUpdateScheduleMissingMandatoryFieldAppointmentDateTest() {
    given()
        .header("Content-Type", "application/json")
        .body(scheduleMissingMandatoryFieldAppointmentDateBodyBuilder())
        .when().log().all()
        .put("schedules/" + scheduleId)
        .then().log().all()
        .body("[0].message", equalTo("Field 'appointment_date' is mandatory."))
        .body("[1].message", Matchers.emptyOrNullString())
        .statusCode(400);
  }

  private Schedule scheduleMissingMandatoryFieldAppointmentDateBodyBuilder() {
    return Schedule.builder().sport("Volei")
        .place("Praca do Willy").description("Volei quinzenal no Derla.").build();
  }

}
