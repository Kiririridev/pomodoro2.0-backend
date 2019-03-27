package pomodoro.kiririri.pomodorobackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pomodoro.kiririri.pomodorobackend.db.PomodoroDatabaseAPI;
import pomodoro.kiririri.pomodorobackend.dto.Pomodoro;

import java.util.List;

@RestController
public class PomodoroBasicRestController {

	@Autowired
	private PomodoroDatabaseAPI databaseAPI;

	@PostMapping("/pomodoropost")
	public void pomodoroPost(@RequestBody Pomodoro pomodoro) {

		databaseAPI.persistPomodoroInDatabase(pomodoro);
	}

	@GetMapping("/pomodoroget")
	public List<Pomodoro> pomodoroGet() {

		return databaseAPI.selectAllPomodorosFromDatabase();
	}
}
