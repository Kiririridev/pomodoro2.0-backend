package pomodoro.kiririri.pomodorobackend.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import pomodoro.kiririri.pomodorobackend.annotations.TestQualifier;
import pomodoro.kiririri.pomodorobackend.dto.Pomodoro;

import java.sql.Date;

import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.annotation.DirtiesContext.*;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		TestDataSource.class,
		PomodoroDatabaseAPI.class})
@TestQualifier
@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
public class PomodoroDatabaseAPITest {

	private static final String POMODOROS_TABLE = "POMODOROS";

	@Autowired
	private PomodoroDatabaseAPI databaseAPI;

	@Test
	public void shouldPersistPomodoroInDatabase() {

		Pomodoro testPomodoro = createSamplePomodoro();
		int initialRowAmount = countRowsInTable(databaseAPI.getJdbcTemplate(), POMODOROS_TABLE);

		databaseAPI.persistPomodoroInDatabase(testPomodoro);

		assertEquals(
				initialRowAmount + 1,
				countRowsInTable(databaseAPI.getJdbcTemplate(), POMODOROS_TABLE));
	}

	@Test
	public void shouldSelectPomodorosFromDatabase() {

		Pomodoro pomodoro = databaseAPI.selectAllPomodorosFromDatabase().get(0);

		assertSelectedPomodoro(pomodoro);
	}

	private void assertSelectedPomodoro(Pomodoro pomodoro) {
		assertEquals(
				"test",
				pomodoro.getUserId());
		assertEquals(
				"sample",
				pomodoro.getDescription());
		assertEquals(
				4,
				pomodoro.getLength());
		assertEquals(
				"TEST",
				pomodoro.getTag());
	}

	private Pomodoro createSamplePomodoro() {

		return new Pomodoro(1, "test", new Date(currentTimeMillis()), 1, "test", "TEST");
	}
}