package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Data
public class Installment {

    private Integer amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "{" +
                "\"amount\":" + amount +
                ", \"date\":\"" + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+ '\"'+
                '}';
    }
}