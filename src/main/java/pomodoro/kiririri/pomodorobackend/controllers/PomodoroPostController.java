package pomodoro.kiririri.pomodorobackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pomodoro.kiririri.pomodorobackend.db.PomodoroDataSource;
import pomodoro.kiririri.pomodorobackend.dto.Pomodoro;
import pomodoro.kiririri.pomodorobackend.rowmappers.PomodoroRowMapper;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class PomodoroPostController {

	private static final String INSERT_POMODOROS = "INSERT INTO POMODOROS (USER_ID, POMODORO_DATE, LENGTH, DESCRIPTION, TAG) VALUES (?, ? ,?, ?, ?)";
	private static final String SELECT_POMODOROS = "SELECT * FROM POMODOROS";

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
				pomodoro.getUserId(),
				pomodoro.getPomodoroDate(),
				pomodoro.getLength(),
				pomodoro.getDescription(),
				pomodoro.getTag());
	}

	@GetMapping("/pomodoroget")
	public List<Pomodoro> pomodoroGet() {
		List<Pomodoro> pomodoros = jdbcTemplate.query(SELECT_POMODOROS, new PomodoroRowMapper());

		return pomodoros;
	}
}
