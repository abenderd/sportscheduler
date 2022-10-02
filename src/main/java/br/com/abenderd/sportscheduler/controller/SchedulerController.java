package br.com.abenderd.sportscheduler.controller;

import br.com.abenderd.sportscheduler.dto.SchedulePartialUpdateRequest;
import br.com.abenderd.sportscheduler.entity.Schedule;
import br.com.abenderd.sportscheduler.service.SchedulerService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/schedules")
@Validated
public class SchedulerController {

  @Autowired
  private SchedulerService schedulerService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void scheduler(HttpServletResponse httpServletResponse,
      UriComponentsBuilder uriComponentsBuilder,
      @Valid @RequestBody Schedule schedule) {
    Schedule scheduleResponse = schedulerService.createSchedule(schedule);
    httpServletResponse.setHeader("Location", uriComponentsBuilder.path("/schedules/{id}")
        .buildAndExpand(scheduleResponse.getId())
        .toString());
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Schedule> getSchedules(@RequestParam(name = "sport", required = false) String sport,
      @RequestParam(name = "description", required = false) String description,
      @RequestParam(name = "place", required = false) String place,
      @RequestParam(name = "appointmentDate", required = false) String appointmentDate) {
    return schedulerService.getSchedules(sport, description, place, appointmentDate);
  }

  @GetMapping("/{scheduleId}")
  @ResponseStatus(HttpStatus.OK)
  public Schedule getScheduleById(
      @PathVariable("scheduleId") @Size(max = 36, message = "Invalid path parameter 'scheduleId'.") String id)
      throws NotFoundException {
    return schedulerService.getScheduleById(id);
  }

  @PutMapping(path = "/{scheduleId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateScheduleById(HttpServletResponse httpServletResponse,
      UriComponentsBuilder uriComponentsBuilder,
      @Valid @RequestBody Schedule schedule,
      @PathVariable("scheduleId") @Size(max = 36, message = "Invalid path parameter 'scheduleId'.") String id)
      throws NotFoundException {

    Schedule sheduleResponse = schedulerService.updateScheduleById(id, schedule);

    httpServletResponse.setHeader("Location", uriComponentsBuilder.path("/schedules/{id}")
        .buildAndExpand(sheduleResponse.getId())
        .toString());
  }

  @PatchMapping(path = "/{scheduleId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void partialUpdateScheduleById(HttpServletResponse httpServletResponse,
      UriComponentsBuilder uriComponentsBuilder,
      @Valid @RequestBody SchedulePartialUpdateRequest schedulePartialUpdateRequest,
      @PathVariable("scheduleId") @Size(max = 36, message = "Invalid path parameter 'scheduleId'.") String id)
      throws NotFoundException {
    Schedule scheduleResponse = schedulerService.partialUpdateScheduleById(id,
        schedulePartialUpdateRequest);

    httpServletResponse.setHeader("Location", uriComponentsBuilder.path("/schedules/{id}")
        .buildAndExpand(id)
        .toString());
  }

  @DeleteMapping(path = "/{scheduleId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteScheduleById(@PathVariable String scheduleId) {
    schedulerService.deleteScheduleById(scheduleId);
  }

}
