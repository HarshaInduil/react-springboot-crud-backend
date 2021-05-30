package com.dms.adproject.service.impl;

import com.dms.adproject.dto.request.AdvertisementRequest;
import com.dms.adproject.dto.response.AdvertisementResponse;
import com.dms.adproject.exception.ResourceNotFoundException;
import com.dms.adproject.model.Advertisement;
import com.dms.adproject.repository.AdvertisementRepository;
import com.dms.adproject.service.AdvertisementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final Logger logger = LoggerFactory.getLogger(AdvertisementServiceImpl.class);

    private final AdvertisementRepository advertisementRepository;

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository, UserDetailsServiceImpl userDetailsService) {
        this.advertisementRepository = advertisementRepository;
        this.userDetailsService = userDetailsService;
    }

    public Advertisement getObject(Long id) {
        Optional<Advertisement> advertisement = advertisementRepository.findById(id);
        if (advertisement.isPresent()) {
            return advertisement.get();
        } else {
            logger.error("Cannot find Advertisement with ID :: {}", id);
            throw new ResourceNotFoundException("cannot find Advertisement with id :: " + id);
        }
    }

    @Override
    public AdvertisementResponse getById(Long id) {
        return new AdvertisementResponse(getObject(id));
    }

    @Override
    public List<AdvertisementResponse> getAllByUserID(Long userId) {
        return advertisementRepository.findAll().stream().map(AdvertisementResponse::new).collect(Collectors.toList());
    }

    @Override
    public Page<AdvertisementResponse> getAllWithPaginationAndFilters(PageRequest pageRequest, String searchKeyword) {
        if (searchKeyword.equals("")) {
            return advertisementRepository.findAll(pageRequest).map(AdvertisementResponse::new);
        } else {
            return advertisementRepository.findAllByTitleLike(pageRequest, searchKeyword).map(AdvertisementResponse::new);
        }
    }

    @Override
    public void add(AdvertisementRequest advertisementRequest) {
        Advertisement advertisement = advertisementRequest.getAdvertisement();
        advertisement.setUser(userDetailsService.findUserById(advertisementRequest.getUserId()));
        advertisementRepository.save(advertisement);
    }

    @Override
    public void update(AdvertisementRequest advertisementRequest) {
        Advertisement advertisement = getObject(advertisementRequest.getId());
        advertisement.setTitle(advertisementRequest.getTitle());
        advertisement.setDescription(advertisementRequest.getDescription());
        advertisement.setSalePrice(advertisementRequest.getSalePrice());
        advertisement.setAddress(advertisementRequest.getAddress());
        advertisement.setUser(userDetailsService.findUserById(advertisementRequest.getUserId()));
        advertisementRepository.save(advertisement);
    }

    @Override
    public void delete(Long id) {
        advertisementRepository.delete(getObject(id));
    }
}
