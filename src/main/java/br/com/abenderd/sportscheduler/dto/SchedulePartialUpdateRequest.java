package br.com.abenderd.sportscheduler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class SchedulePartialUpdateRequest {

  UUID id;

  @Size(message = "Field 'sport' is overpassing 50 characters limit.", max = 50)
  String sport;

  @Size(message = "Field 'place' is overpassing 100 characters limit.", max = 100)
  String place;

  @Size(message = "Field 'description' is overpassing 150 characters limit.", max = 150)
  String description;

  @JsonProperty("appointment_date")
  LocalDateTime appointmentDate;

}