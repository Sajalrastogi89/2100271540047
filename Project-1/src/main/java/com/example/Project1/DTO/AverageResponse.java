package com.example.Project1.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AverageResponse {
    private List<Integer> numbers;
    private List<Integer> windowPrevState;
    private List<Integer> windowCurrState;
    private double avg;
}
