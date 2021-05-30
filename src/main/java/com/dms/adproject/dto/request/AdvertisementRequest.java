package com.dms.adproject.dto.request;

import com.dms.adproject.model.Advertisement;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdvertisementRequest {
    private Long id;
    private String title;
    private String description;
    private String contactNumber;
    private String address;
    private BigDecimal salePrice;
    private Long userId;

    public Advertisement getAdvertisement() {
        return new Advertisement(
                this.title,
                this.description,
                this.contactNumber,
                this.address,
                this.salePrice,
                null
        );
    }
}
