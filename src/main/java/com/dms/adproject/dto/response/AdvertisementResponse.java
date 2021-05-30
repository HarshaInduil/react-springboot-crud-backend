package com.dms.adproject.dto.response;

import com.dms.adproject.model.Advertisement;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdvertisementResponse {
    private Long id;
    private String title;
    private String description;
    private String contactNumber;
    private String address;
    private BigDecimal salePrice;
    private String nameOfUser;

    public AdvertisementResponse(Advertisement advertisement) {
        this.id = advertisement.getId();
        this.title = advertisement.getTitle();
        this.description = advertisement.getDescription();
        this.contactNumber = advertisement.getContactNumber();
        this.address = advertisement.getAddress();
        this.salePrice = advertisement.getSalePrice();
        this.nameOfUser = advertisement.getUser().getName();
    }
}
