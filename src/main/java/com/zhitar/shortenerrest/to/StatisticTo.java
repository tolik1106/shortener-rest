package com.zhitar.shortenerrest.to;

import com.zhitar.shortenerrest.domain.Link;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticTo {

    private Link link;

    private Integer statisticCount;

    private List<Object[]> dateStatistic;
    private List<Object[]> browserStatistic;
    private List<Object[]> referrerStatistic;
}
