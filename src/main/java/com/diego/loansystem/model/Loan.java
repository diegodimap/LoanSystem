package com.diego.loansystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Loan {
    private double amount;
    private int duration;
    private String type;
}
