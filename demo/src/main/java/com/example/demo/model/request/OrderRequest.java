package com.example.demo.model.request;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
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
    private String status;

    //TASK 2.4: FIX RACE CONDITION - These are added to fix the race condition
    private Boolean paymentDone;
    private Boolean inventoryDone;
    private Boolean notificationDone;

    //TASK 2.3: LOST UPDATE PROBLEM - added for optimistic locking
    @Version
    private Long version;
}
