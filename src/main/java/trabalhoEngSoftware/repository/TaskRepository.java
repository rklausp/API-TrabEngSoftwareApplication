package trabalhoEngSoftware.repository;

import trabalhoEngSoftware.domain.Priority;
import trabalhoEngSoftware.domain.Status;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByResponsibleAndIsDeleted(Users users, boolean isDeleted);

    List<Task> findByResponsibleAndStatusAndPriorityAndDueDateBeforeAndIsDeleted(Users users, Status status, Priority priority, LocalDate dueBefore, boolean isDeleted);

}
