package br.com.abenderd.sportscheduler;

import static io.restassured.RestAssured.given;

import br.com.abenderd.sportscheduler.entity.Schedule;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.hamcrest.Matchers;

public abstract class ScheduleAbstractTest {

  protected static Schedule scheduleRequestBody;
  protected static String place = "Praça do Derla";
  protected static String sport = "Volei";
  protected static String description = "Volei quinzenal no Derla.";
  protected static String scheduleId;
  protected static String scheduleLocationHeader;
  static String baseURI = "http://localhost:8083/sport-scheduler/v1";

  protected static void createSchedule() {

    scheduleLocationHeader = given()
        .header("Content-Type", "application/json")
        .body(scheduleFullFilledBodyBuilder())
        .when().log().all()
        .post("/schedules")
        .then().log().all()
        .body(Matchers.emptyOrNullString())
        .statusCode(201)
        .extract()
        .header("Location");

    setLocationId(scheduleLocationHeader);
  }

  protected static Schedule scheduleFullFilledBodyBuilder() {
    scheduleRequestBody = Schedule.builder()
        .place(place)
        .sport(sport)
        .appointmentDate(LocalDateTime.now().plusDays(15).truncatedTo(ChronoUnit.SECONDS))
        .description(description)
        .build();

    return scheduleRequestBody;
  }

  protected static void setLocationId(String location) {
    scheduleId = location.split("schedules/")[1];
  }

}