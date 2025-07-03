package trabalhoEngSoftware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trabalhoEngSoftware.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTaskIdAndIsDeleted(Long taskId, boolean isDeleted);
}
