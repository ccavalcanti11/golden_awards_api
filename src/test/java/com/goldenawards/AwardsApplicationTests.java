package com.goldenawards;

import com.goldenawards.services.FileReaderService;
import com.univocity.parsers.common.record.Record;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AwardsApplicationTests {

	private static final String MOVIELIST = "movielist.csv";

	@Autowired
	private FileReaderService fileReaderService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void isYearHeaderPresentInFile() throws FileNotFoundException {
		List<String> headers = this.getFileHeaders();
		assertTrue(headers.contains("year"));
	}

	@Test
	void isTitleHeaderPresentInFile() throws FileNotFoundException {
		List<String> headers = this.getFileHeaders();
		assertTrue(headers.contains("title"));
	}

	@Test
	void isStudiosHeaderPresentInFile() throws FileNotFoundException {
		List<String> headers = this.getFileHeaders();
		assertTrue(headers.contains("studios"));
	}

	@Test
	void isYearProducersPresentInFile() throws FileNotFoundException {
		List<String> headers = this.getFileHeaders();
		assertTrue(headers.contains("producers"));
	}

	@Test
	void isWinnerHeaderPresentInFile() throws FileNotFoundException {
		List<String> headers = this.getFileHeaders();
		assertTrue(headers.contains("winner"));
	}

	@Test
	void isWinningRangeReturning() throws Exception {
		String jsonResult = "{\"min\":[{\"producer\":\"Joel Silver\",\"interval\":1,\"previousWin\":1990,\"followingWin\":1991}],\"max\":[{\"producer\":\"Matthew Vaughn\",\"interval\":13,\"previousWin\":2002,\"followingWin\":2015}]}";
		mockMvc.perform(get("/movies/range"))
				.andExpect(status().isOk())
				.andExpect(content().string(jsonResult));
	}

	private List<String> getFileHeaders() throws FileNotFoundException {
		List<Record> fileRecords = fileReaderService.readCsvFile(fileReaderService.getFileInputStream(MOVIELIST),
				Boolean.FALSE);
		List<String> headers = Arrays.asList(fileRecords.iterator().next().getValues());
		return headers;
	}

}
