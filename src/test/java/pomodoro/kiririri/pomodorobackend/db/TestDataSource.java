package pomodoro.kiririri.pomodorobackend.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import pomodoro.kiririri.pomodorobackend.annotations.TestQualifier;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@TestQualifier
public class TestDataSource {

	@PostConstruct
	public void init() throws ClassNotFoundException {
		Class.forName("org.h2.Driver");
	}

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("createPomodoroTable.sql")
				.build();
	}
}
