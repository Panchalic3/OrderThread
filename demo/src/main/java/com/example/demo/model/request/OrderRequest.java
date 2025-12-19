package com.example.demo.model.request;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class OrderRequest {

    private String customerId;
    @Id
    private String orderID;
    private List<String> items;
    private List<Integer> quantities;
    private String paymentMethod;

}
