package pomodoro.kiririri.pomodorobackend.dto;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "POMODOROS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pomodoro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@Column
	private String tag;
}
