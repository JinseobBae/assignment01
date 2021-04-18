package com.kakaopay.finance.investing.presentation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kakaopay.finance.BaseControllerTest;
import com.kakaopay.finance.exception.AlreadySoldOutException;
import com.kakaopay.finance.exception.AmountExceedException;
import com.kakaopay.finance.exception.MinimumAmountException;
import com.kakaopay.finance.exception.NoInvestingItemException;
import com.kakaopay.finance.investing.application.dto.InvestingDto;
import com.kakaopay.finance.investing.application.dto.InvestingItemDto;
import com.kakaopay.finance.investing.application.dto.InvestingResultDto;
import com.kakaopay.finance.investing.domain.InvestingItemRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InvestingControllerTest extends BaseControllerTest {

    private InvestingItemDto openItem;
    private InvestingItemDto closeItem;

    @Autowired
    InvestingItemRepository investingItemRepository;

    @BeforeEach
    private void setup() throws Exception {
        InvestingItemDto openItem = InvestingItemDto.builder()
                .itemTitle("투자중")
                .totalInvestingAmount(100000)
                .nowInvestingAmount(1000)
                .investorAmount(1)
                .itemStatus("1")
                .startedAt(LocalDateTime.of(2020,3,1,0,0,0))
                .finishedAt(LocalDateTime.of(2022,4,1,0,0,0))
                .build();

        InvestingItemDto closeItem = InvestingItemDto.builder()
                .itemTitle("투자종료")
                .totalInvestingAmount(100000)
                .nowInvestingAmount(100000)
                .investorAmount(10)
                .itemStatus("0")
                .startedAt(LocalDateTime.of(2020,3,1,0,0,0))
                .finishedAt(LocalDateTime.of(2022,4,1,0,0,0))
                .build();

        requestPost("/v1/add-item", 1234, openItem ).andExpect(status().isOk());
        requestPost("/v1/add-item", 1234, closeItem ).andExpect(status().isOk());

        MvcResult mvcResult = requestGet("/v1/investing", 1234).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<InvestingItemDto> items =  objectMapper.readValue(contentAsString, new TypeReference<List<InvestingItemDto>>() {});
        this.openItem = items.stream().filter(item -> item.getItemStatus().equals("1") ).collect(Collectors.toList()).get(0);
        this.closeItem = items.stream().filter(item -> item.getItemStatus().equals("0") ).collect(Collectors.toList()).get(0);
    }



    /**
     * Find all valid item API test
     * @throws Exception
     */
    @Test
    void findAllValidItemTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(
                            MockMvcRequestBuilders.get("/v1/investing" )
                            .accept(MediaType.APPLICATION_JSON)
                            )
                .andExpect(status().isOk())
                .andReturn();
        List<InvestingItemDto> items = getMvcResultData(mvcResult, new TypeReference<List<InvestingItemDto>>() {});
        assert items.size() > 0;

    }

    /**
     * Investing API test (Success case)
     * @throws Exception
     */
    @Test
    void investingControllerSuccess() throws Exception{
        MvcResult mvcResult = requestPost("/v1/investing", 1234, InvestingDto.builder().itemId(openItem.getItemId()).investingAmount(10000).build())
                .andExpect(status().isOk())
                .andReturn();
        InvestingResultDto resultDto = getMvcResultData(mvcResult, new TypeReference<InvestingResultDto>() {});
        assert resultDto.getResultCode().equals("1");
    }

    /**
     * 4 cases of exception test (NoInvestingItemException, AlreadySoldOutException, MinimumAmountException, AmountExceedException)
     * @throws Exception
     */
    @Test
    void investingControllerAssertException() throws Exception {
        assertThrowsRequestPost("/v1/investing", 1234, InvestingDto.builder().itemId(0).investingAmount(10000).build(), NoInvestingItemException.class);
        assertThrowsRequestPost("/v1/investing", 1234, InvestingDto.builder().itemId(closeItem.getItemId()).investingAmount(10000).build(), AlreadySoldOutException.class);
        assertThrowsRequestPost("/v1/investing", 1234, InvestingDto.builder().itemId(openItem.getItemId()).investingAmount(-1).build(), MinimumAmountException.class);
        assertThrowsRequestPost("/v1/investing", 1234, InvestingDto.builder().itemId(openItem.getItemId()).investingAmount(9999999).build(), AmountExceedException.class);
    }

    /**
     * Find user investing history test
     * @throws Exception
     */
    @Test
    void findMyInvestingControllerTest() throws Exception{
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/my-investing")
                .header("X-USER-ID", 1234)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    /**
     * Make investing item status to close test
     * @throws Exception
     */
    @Test
    void InvestingItemStatusToCloseTest() throws Exception {
        InvestingDto dto = InvestingDto.builder()
        .itemId(openItem.getItemId())
        .investingAmount(openItem.getTotalInvestingAmount() - openItem.getNowInvestingAmount())
        .build();
        requestPost("/v1/investing", 1234, dto).andExpect(status().isOk());

        MvcResult mvcResult = requestGet("/v1/investing", 1234).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<InvestingItemDto> items =  objectMapper.readValue(contentAsString, new TypeReference<List<InvestingItemDto>>() {});
        Optional<InvestingItemDto> investedItem  = items.stream()
                                        .filter(item -> item.getItemId() == openItem.getItemId())
                                        .findAny();

        assertTrue(investedItem.isPresent());
        assert(investedItem.get().getItemStatus().equals("0"));
    }



}