package br.com.abenderd.sportscheduler.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Valid
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="schedule")
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="id")
  @ApiModelProperty(required = false, hidden = true)
  UUID id;

  @NotBlank(message = "Field 'sport' is mandatory.")
  @Size(message= "Field 'sport' is overpassing 50 characters limit.", max=50)
  @Column(name="sport")
  String sport;

  @NotBlank(message = "Field 'place' is mandatory.")
  @Size(message= "Field 'place' is overpassing 100 characters limit.", max=100)
  @Column(name="place")
  String place;

  @Size(message= "Field 'description' is overpassing 150 characters limit.", max=150)
  @Column(name="description")
  String description;

  @JsonProperty("appointment_date")
  @NotNull(message = "Field 'appointment_date' is mandatory.")
  @Column(name="appointment_date")
  LocalDateTime appointmentDate;

}