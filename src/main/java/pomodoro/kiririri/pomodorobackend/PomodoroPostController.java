package pomodoro.kiririri.pomodorobackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class PomodoroPostController {

	private static final String INSERT_POMODOROS = "INSERT INTO POMODOROS (POMODORO_ID, USER_ID, POMODORO_DATE, LENGTH, DESCRIPTION) VALUES (?, ?, ? ,?, ?)";

	@Value("${spring.datasource.url}")
	private String dbURL;
	@Value("${spring.datasource.username}")
	private String user;
	@Value("${spring.datasource.password}")
	private String password;

	@Autowired
	private PomodoroDataSource pomodoroDataSource;
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void init() {
		jdbcTemplate = new JdbcTemplate(pomodoroDataSource);
	}

	@PostMapping("/pomodoropost")
	public void pomodoroPost(@RequestBody Pomodoro pomodoro) {

		jdbcTemplate.update(INSERT_POMODOROS,
				pomodoro.getPomodoroId(),
				pomodoro.getUserId(),
				pomodoro.getPomodoroDate(),
				pomodoro.getLength(),
				pomodoro.getDescription());
	}

}
