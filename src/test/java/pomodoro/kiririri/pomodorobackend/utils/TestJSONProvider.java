package pomodoro.kiririri.pomodorobackend.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class TestJSONProvider {

	public JSONObject getSamplePostRequest() throws JSONException {

		return new JSONObject()
				.put("userId", "kiri")
				.put("pomodoroDate", "1994-06-23")
				.put("length", 20)
				.put("description", "description")
				.put("tag", "tag");
	}
}
