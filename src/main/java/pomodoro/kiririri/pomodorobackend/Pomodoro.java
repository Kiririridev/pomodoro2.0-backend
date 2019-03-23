package pomodoro.kiririri.pomodorobackend;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "POMODOROS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pomodoro
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int pomodoroId;
	@Column
	private String userId;
	@Column
	private Date pomodoroDate;
	@Column
	private int length;
	@Column
	private String description;
}
