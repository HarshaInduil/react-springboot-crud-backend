package com.dms.adproject.service;

import com.dms.adproject.dto.request.AdvertisementRequest;
import com.dms.adproject.dto.response.AdvertisementResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AdvertisementService {
    /**
     * Get Advertisement By ID
     *
     * @param id
     * @return
     */
    AdvertisementResponse getById(Long id);

    /**
     * Get All Advertisement with filter by User ID
     *
     * @param userId
     * @return
     */
    List<AdvertisementResponse> getAllByUserID(Long userId);


    /**
     * Get All with pagination and filter By user input string value (title)
     *
     * @param pageRequest
     * @param searchKeyword
     * @return
     */
    Page<AdvertisementResponse> getAllWithPaginationAndFilters(PageRequest pageRequest, String searchKeyword);

    /**
     * Add Advertisement
     *
     * @param advertisementRequest
     */
    void add(AdvertisementRequest advertisementRequest);

    /**
     * Update Advertisement
     *
     * @param advertisementRequest
     */
    void update(AdvertisementRequest advertisementRequest);

    /**
     * Delete Advertisement
     *
     * @param id
     */
    void delete(Long id);
}
