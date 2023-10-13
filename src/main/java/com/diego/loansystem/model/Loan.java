package com.diego.loansystem.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Loan {
    private double amount;
    private int duration;
    private String type;
}
