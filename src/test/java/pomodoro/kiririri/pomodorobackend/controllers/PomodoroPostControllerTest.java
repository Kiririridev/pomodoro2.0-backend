package pomodoro.kiririri.pomodorobackend.controllers;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pomodoro.kiririri.pomodorobackend.PomodoroBackendApplication;
import pomodoro.kiririri.pomodorobackend.db.PomodoroDatabaseAPI;
import pomodoro.kiririri.pomodorobackend.dto.Pomodoro;
import pomodoro.kiririri.pomodorobackend.utils.TestJSONProvider;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
		webEnvironment = WebEnvironment.RANDOM_PORT,
		classes = {
				PomodoroPostController.class,
				PomodoroBackendApplication.class})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class PomodoroPostControllerTest {

	private static final String POMODOROGET_ENDPOINT = "/pomodoroget";
	private static final String POMODOROPOST_ENDPOINT = "/pomodoropost";
	private static final String POMODORO_ID_JSON_FORMAT = "\"pomodoroId\":%d";
	private static final int ID_1 = 323;
	private static final int ID_2 = 142;

	@Autowired
	private MockMvc mockMVC;
	@MockBean
	private PomodoroDatabaseAPI databaseAPI;
	private TestJSONProvider testJSONProvider;

	@Before
	public void setup() {
		testJSONProvider = new TestJSONProvider();
	}

	@Test
	public void shouldCallDatabaseAPIOnPOST() throws Exception {

		mockPomodoroDatabaseAPIForPOST();

		performPOST();

		verifyIfDatabaseAPIPersistWasCalled();
	}

	@Test
	public void shouldCallDatabaseAPIOnGETAndReturnList() throws Exception {

		mockDatabaseAPIForGET();

		MockHttpServletResponse response = performGET();

		assertResultAfterGET(response);
	}

	private void performPOST() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(POMODOROPOST_ENDPOINT)
				.header("Content-Type", APPLICATION_JSON.toString())
				.content(getJsonString());

		mockMVC.perform(requestBuilder)
				.andExpect(status().isOk());
	}

	private MockHttpServletResponse performGET() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(POMODOROGET_ENDPOINT);

		return mockMVC.perform(requestBuilder).andReturn().getResponse();
	}

	private void assertResultAfterGET(MockHttpServletResponse response) throws UnsupportedEncodingException {

		verifyIfDatabaseAPISelectWasCalledWith();

		assertThatContentContainsID(response, ID_1);
		assertThatContentContainsID(response, ID_2);
	}

	private void assertThatContentContainsID(MockHttpServletResponse response, int id) throws UnsupportedEncodingException {

		assertThat(response.getContentAsString())
				.containsSequence(
						format(POMODORO_ID_JSON_FORMAT, id));
	}

	private void mockDatabaseAPIForGET() {

		List<Pomodoro> pomodoros = createPomodorosList();

		when(databaseAPI.selectAllPomodorosFromDatabase()).thenReturn(pomodoros);
	}

	private List<Pomodoro> createPomodorosList() {

		return asList(
				createPomodoroWithID(ID_1),
				createPomodoroWithID(ID_2)
		);
	}

	private Pomodoro createPomodoroWithID(int id1) {
		return new Pomodoro(id1, "test", new Date(System.currentTimeMillis()), 1, "test", "TAG");
	}

	private String getJsonString() throws JSONException {
		return testJSONProvider.getSamplePostRequest().toString();
	}

	private void verifyIfDatabaseAPIPersistWasCalled() {
		verify(databaseAPI).persistPomodoroInDatabase(any(Pomodoro.class));
	}

	private void verifyIfDatabaseAPISelectWasCalledWith() {
		verify(databaseAPI).selectAllPomodorosFromDatabase();
	}

	private void mockPomodoroDatabaseAPIForPOST() {
		doNothing().when(databaseAPI).persistPomodoroInDatabase(any(Pomodoro.class));
	}
}