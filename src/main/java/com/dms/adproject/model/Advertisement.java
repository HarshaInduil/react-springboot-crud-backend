package com.dms.adproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "advertisement")
public class Advertisement extends BaseEntityMaster {
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;
}
