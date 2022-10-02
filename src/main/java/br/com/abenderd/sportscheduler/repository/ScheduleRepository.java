package br.com.abenderd.sportscheduler.repository;

import br.com.abenderd.sportscheduler.entity.Schedule;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, UUID> {

  @Query("select u from Schedule u "
      + "where (u.sport = :sport or :sport is null) "
      + "AND (u.description = :description or :description is null) "
      + "AND (u.place = :place or :place is null) "
      + "AND (u.appointmentDate = :appointmentDate or :appointmentDate is null)")
  List<Schedule> findAllByFilters(String sport, String description, String place,
      String appointmentDate);


  List<Schedule> findBySportOrDescription(String sport, String description);

}