package br.com.abenderd.sportscheduler.service.impl;

import br.com.abenderd.sportscheduler.config.exception.NotFoundException;
import br.com.abenderd.sportscheduler.dto.SchedulePartialUpdateRequest;
import br.com.abenderd.sportscheduler.entity.Schedule;
import br.com.abenderd.sportscheduler.repository.ScheduleRepository;
import br.com.abenderd.sportscheduler.service.SchedulerService;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerServiceImpl implements SchedulerService {

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Override
  public Schedule createSchedule(Schedule schedule) {
    return scheduleRepository.save(schedule);
  }

  @Override
  public List<Schedule> getSchedules(String sport, String description, String place, String appointmentDate) {
    return scheduleRepository.findAllByFilters(sport, description, place, appointmentDate);
  }

  @Override
  public Schedule getScheduleById(String id) throws NotFoundException {
    return scheduleRepository.findById(UUID.fromString(id)).orElseThrow(NotFoundException::new);
  }

  @Override
  public Schedule updateScheduleById(String id, Schedule schedule) {
    schedule.setId(UUID.fromString(id));

    return scheduleRepository.save(schedule);
  }

  @Override
  public Schedule partialUpdateScheduleById(String id,
      @Valid SchedulePartialUpdateRequest schedulePartialUpdateRequest) {
    Schedule updatedSchedule = getScheduleById(id);

    if (ObjectUtils.isNotEmpty(schedulePartialUpdateRequest.getSport())) {
      updatedSchedule.setSport(schedulePartialUpdateRequest.getSport());
    }

    if (ObjectUtils.isNotEmpty(schedulePartialUpdateRequest.getDescription())) {
      updatedSchedule.setDescription(schedulePartialUpdateRequest.getDescription());
    }

    if (ObjectUtils.isNotEmpty(schedulePartialUpdateRequest.getPlace())) {
      updatedSchedule.setPlace(schedulePartialUpdateRequest.getPlace());
    }

    if (ObjectUtils.isNotEmpty(schedulePartialUpdateRequest.getAppointmentDate())) {
      updatedSchedule.setAppointmentDate(schedulePartialUpdateRequest.getAppointmentDate());
    }

    return scheduleRepository.save(updatedSchedule);
  }

  @Override
  public void deleteScheduleById(String id) {
    scheduleRepository.deleteById(UUID.fromString(id));
  }

}
