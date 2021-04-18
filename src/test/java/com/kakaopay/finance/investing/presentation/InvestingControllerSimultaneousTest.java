package com.kakaopay.finance.investing.presentation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kakaopay.finance.BaseControllerTest;
import com.kakaopay.finance.investing.application.dto.InvestingDto;
import com.kakaopay.finance.investing.application.dto.InvestingItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InvestingControllerSimultaneousTest extends BaseControllerTest {

    private InvestingItemDto openItem;
    private long totalAmount = 0;


    @BeforeEach
    private void setup() throws Exception {
        InvestingItemDto openItem = InvestingItemDto.builder()
                .itemTitle("동시투자")
                .totalInvestingAmount(100000)
                .nowInvestingAmount(1000)
                .investorAmount(1)
                .itemStatus("1")
                .startedAt(LocalDateTime.of(2020,3,1,0,0,0))
                .finishedAt(LocalDateTime.of(2022,4,1,0,0,0))
                .build();

        requestPost("/v1/add-item", 1234, openItem ).andExpect(status().isOk());

        MvcResult mvcResult = requestGet("/v1/investing", 1234).andReturn();
        List<InvestingItemDto> items = getMvcResultData(mvcResult, new TypeReference<List<InvestingItemDto>>() {});
        this.openItem = items.stream().filter(item -> item.getItemStatus().equals("1") && item.getItemTitle().equals("동시투자") ).collect(Collectors.toList()).get(0);
    }

    /**
     * Investing simultaneous request test
     * @throws Exception
     */
    @Test
    public void multiInvestingRequestTest() throws Exception {
        int poolSize = 50;
        int count = 10;
        assertMultiThreadCall(poolSize, count,  (i) -> {
            long amount = i + 1;
            long userId = i + 10;
            return new Callable<ResultActions>() {
                public ResultActions call() throws Exception {
                    InvestingDto dto = InvestingDto.builder()
                            .itemId(openItem.getItemId())
                            .investingAmount(amount)
                            .userId(userId)
                            .build();
                    ResultActions resultActions = requestPost("/v1/investing", userId, dto);
                    return resultActions.andExpect(status().isOk());
                }
            };
        });

        for (int i=0; i<count; i++) {
            long amount = i + 1;
            totalAmount += amount;
        }

        MvcResult mvcResult = requestGet("/v1/investing", 1234).andReturn();
        List<InvestingItemDto> items = getMvcResultData(mvcResult, new TypeReference<List<InvestingItemDto>>() {});
        InvestingItemDto nowItem = items.stream().filter(item -> item.getItemId() == openItem.getItemId() ).collect(Collectors.toList()).get(0);

        assert openItem.getNowInvestingAmount() + totalAmount == nowItem.getNowInvestingAmount();
    }

    protected void assertMultiThreadCall(int poolSize, int count, Function<Integer, Callable<?>> function) throws InterruptedException, ExecutionException {
        ExecutorService exec = Executors.newFixedThreadPool(poolSize);
        List<Future<?>> futures = new ArrayList<Future<?>>();
        try {
            Callable<?> task = null;
            // Count만큼 수행
            for (int i=0; i<count; i++) {
                task = function.apply(i);
                futures.add(exec.submit(task));
            }
            // 결과 출력
            for (Future<?> future : futures) {
                future.get();
            }
        } finally {
            exec.shutdown();
        }
    }
}
