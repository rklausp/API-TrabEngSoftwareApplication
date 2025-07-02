package trabalhoEngSoftware.repository;

import trabalhoEngSoftware.domain.Priority;
import trabalhoEngSoftware.domain.Status;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByResponsible(Users users);

    List<Task> findByResponsibleAndStatusAndPriorityAndDueDateBefore(Users users, Status status, Priority priority, LocalDate dueBefore);

}
