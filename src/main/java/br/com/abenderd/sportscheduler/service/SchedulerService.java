package br.com.abenderd.sportscheduler.service;

import br.com.abenderd.sportscheduler.dto.SchedulePartialUpdateRequest;
import br.com.abenderd.sportscheduler.entity.Schedule;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface SchedulerService {

  Schedule createSchedule(Schedule schedule);

  List<Schedule> getSchedules(String sport, String description, String place, String appointmentDate);

  Schedule getScheduleById(String id) throws NotFoundException;

  Schedule updateScheduleById(String id, Schedule schedule);

  Schedule partialUpdateScheduleById(String id,
      @Valid SchedulePartialUpdateRequest schedulePartialUpdateRequest) throws NotFoundException;

  void deleteScheduleById(String id);

}
