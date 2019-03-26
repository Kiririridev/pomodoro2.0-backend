package pomodoro.kiririri.pomodorobackend.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pomodoro.kiririri.pomodorobackend.annotations.TestQualifier;
import pomodoro.kiririri.pomodorobackend.dto.Pomodoro;

import java.sql.Date;

import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		TestDataSource.class,
		PomodoroDatabaseAPI.class})
@TestQualifier
public class PomodoroDatabaseAPITest {

	private static final int ONE_ROW = 1;
	private static final String POMODOROS_TABLE = "POMODOROS";

	@Autowired
	private PomodoroDatabaseAPI databaseAPI;

	@Test
	public void shouldPersistPomodoroInDatabase() {

		Pomodoro testPomodoro = createSamplePomodoro();

		databaseAPI.persistPomodoroInDatabase(testPomodoro);

		assertEquals(
				ONE_ROW,
				countRowsInTable(databaseAPI.getJdbcTemplate(), POMODOROS_TABLE));
	}

	private Pomodoro createSamplePomodoro() {

		return new Pomodoro(1, "test", new Date(currentTimeMillis()), 1, "test", "TEST");
	}
}