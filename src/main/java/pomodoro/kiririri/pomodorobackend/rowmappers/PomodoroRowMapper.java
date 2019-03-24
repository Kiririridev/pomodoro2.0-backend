package pomodoro.kiririri.pomodorobackend.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import pomodoro.kiririri.pomodorobackend.dto.Pomodoro;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PomodoroRowMapper implements RowMapper<Pomodoro> {

	private static final String USER_ID = "USER_ID";
	private static final String POMODORO_ID = "POMODORO_ID";
	private static final String LENGTH = "LENGTH";
	private static final String POMODORO_DATE = "POMODORO_DATE";
	private static final String DESCRIPTION = "DESCRIPTION";
	private static final String TAG = "TAG";

	@Override
	public Pomodoro mapRow(ResultSet resultSet, int i) throws SQLException {
		Pomodoro pomodoro = new Pomodoro();

		pomodoro.setPomodoroId(resultSet.getInt(POMODORO_ID));
		pomodoro.setUserId(resultSet.getString(USER_ID));
		pomodoro.setPomodoroDate(resultSet.getDate(POMODORO_DATE));
		pomodoro.setLength(resultSet.getInt(LENGTH));
		pomodoro.setDescription(resultSet.getString(DESCRIPTION));
		pomodoro.setTag(resultSet.getString(TAG));

		return pomodoro;
	}
}
