package pomodoro.kiririri.pomodorobackend.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pomodoro.kiririri.pomodorobackend.dto.Pomodoro;
import pomodoro.kiririri.pomodorobackend.rowmappers.PomodoroRowMapper;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Component
public class PomodoroDatabaseAPI {

	private static final String INSERT_POMODOROS = "INSERT INTO POMODOROS (USER_ID, POMODORO_DATE, LENGTH, DESCRIPTION, TAG) VALUES (?, ? ,?, ?, ?)";
	private static final String SELECT_POMODOROS = "SELECT * FROM POMODOROS";

	@Value("${spring.datasource.url}")
	private String dbURL;
	@Value("${spring.datasource.username}")
	private String user;
	@Value("${spring.datasource.password}")
	private String password;

	@Autowired
	private DataSource pomodoroDataSource;
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void init() {
		jdbcTemplate = new JdbcTemplate(pomodoroDataSource);
	}

	public void persistPomodoroInDatabase(Pomodoro pomodoro) {

		jdbcTemplate.update(INSERT_POMODOROS,
				pomodoro.getUserId(),
				pomodoro.getPomodoroDate(),
				pomodoro.getLength(),
				pomodoro.getDescription(),
				pomodoro.getTag());
	}

	public List<Pomodoro> selectAllPomodorosFromDatabase() {

		List<Pomodoro> pomodoros = jdbcTemplate.query(SELECT_POMODOROS, new PomodoroRowMapper());

		return pomodoros;
	}

	JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
