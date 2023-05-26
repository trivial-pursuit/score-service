package info.ognibeni.club.score

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.containsString
import org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


fun ResultActions.andExpectResult(objectMapper: ObjectMapper, expectedResult: Any): ResultActions {
	this
		.andExpect(status().isOk)
		.andExpect(content().json(objectMapper.writeValueAsString(expectedResult)))

	return this
}

fun ResultActions.andExpectNotFoundError(problemType: String, problemDetailFragment: String): ResultActions =
	this.andExpectError(status().isNotFound, problemType, problemDetailFragment)

fun ResultActions.andExpectAuthenticationError(): ResultActions =
	this.andExpectError(status().isUnauthorized, "UNAUTHORIZED")

fun ResultActions.andExpectError(resultMatcher: ResultMatcher,
                                 problemType: String,
                                 problemDetailFragment: String? = null): ResultActions {
	this
		.andExpect(resultMatcher)
		.andExpect(content().contentType(APPLICATION_PROBLEM_JSON))
		.andExpect(jsonPath("type").value("problem:$problemType"))

	problemDetailFragment?.let { this.andExpect(jsonPath("detail").value(containsString(it))) }

	return this
}
