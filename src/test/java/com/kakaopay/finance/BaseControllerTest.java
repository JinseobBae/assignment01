package com.kakaopay.finance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kakaopay.finance.investing.application.service.InvestingItemService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    protected InvestingItemService investingItemService;
    @Autowired
    protected DataInitService dataInitService;

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

    @BeforeAll
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8",true)) //UTF-8 설정
                .addFilters(new CharacterEncodingFilter("UTF-8",true)) //UTF-8 설정
                .alwaysDo(print()) // 모든 mockMvc print() 수행
                .build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    /**
     * MockMvc perform get request without params
     * @param url
     * @param userId
     * @return
     * @throws Exception
     */
    protected ResultActions requestGet(String url, long userId) throws Exception {
        return requestGet(url, userId, new LinkedMultiValueMap<>());
    }

    /**
     * MockMvc perform get request
     * @param url
     * @param userId
     * @param params
     * @return
     * @throws Exception
     */
    protected ResultActions requestGet(String url, long userId, MultiValueMap<String, String> params) throws Exception {
        MockHttpServletRequestBuilder mmrb = MockMvcRequestBuilders.get(url)
                .header("X-USER-ID", userId)
                .params(params)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        return this.mockMvc.perform(mmrb);
    }


    /**
     * MockMvc perform post request
     * @param url
     * @param userId
     * @param bodyObject
     * @return
     * @throws Exception
     */
    protected ResultActions requestPost(String url, long userId, Object bodyObject) throws Exception {
        MockHttpServletRequestBuilder mmrb = MockMvcRequestBuilders.post(url)
                .header("X-USER-ID", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJsonString(bodyObject));
        return this.mockMvc.perform(mmrb);
    }

    /**
     * Convert Object to Json
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    protected String toJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * MockMvc expect exception by post request
     * @param url
     * @param userId
     * @param bodyObject
     * @param exception
     * @throws Exception
     */
    protected void assertThrowsRequestPost(String url, long userId, Object bodyObject, Class<?> exception) throws Exception {
        requestPost(url, userId, bodyObject)
                .andExpect((result) -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(exception)));
    }

    /**
     * Get data from request result
     * @param mvcResult
     * @param typeReference
     * @param <T>
     * @return
     * @throws Exception
     */
    protected  <T> T getMvcResultData(MvcResult mvcResult, TypeReference<?> typeReference) throws Exception{
        String content = mvcResult.getResponse().getContentAsString();
        return (T) objectMapper.readValue(content, typeReference);
    };


}
