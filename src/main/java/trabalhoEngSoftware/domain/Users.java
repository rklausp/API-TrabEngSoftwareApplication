package trabalhoEngSoftware.domain;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String password;
    private boolean isDeleted;

    @ManyToMany
    @JoinTable(name = "users_task",
               joinColumns = @JoinColumn(name = "users_id"),
               inverseJoinColumns = @JoinColumn(name = "task_id"))
    private List<Task> tasks = new ArrayList<>();


    public void addTask(Task task) {
        this.getTasks().add(task);
        task.getResponsible().add(this);
    }

    public void removeTask(Task task) {
        this.getTasks().remove(task);
        task.getResponsible().remove(this);
    }

}
