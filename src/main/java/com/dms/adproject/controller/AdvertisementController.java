package com.dms.adproject.controller;

import com.dms.adproject.dto.request.AdvertisementRequest;
import com.dms.adproject.dto.response.AdvertisementResponse;
import com.dms.adproject.dto.response.MessageResponse;
import com.dms.adproject.service.AdvertisementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/app/advertisement")
public class AdvertisementController {

    private final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);

    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> getOne(@PathVariable("id") Long id) {
        logger.info("Get Advertisements Id {}", id);
        return ResponseEntity.ok(advertisementService.getById(id));
    }

    @GetMapping("/get-by-user/{id}")
    public ResponseEntity<List<AdvertisementResponse>> getByUser(@PathVariable("id") Long id) {
        logger.info("Get all Advertisements by User Id {}", id);
        return ResponseEntity.ok(advertisementService.getAllByUserID(id));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<AdvertisementResponse>> getOne(
            @Param("page") int page,
            @Param("size") int size,
            @Param("searchKey") String searchKeyword
    ) {
        logger.info("Get Advertisements page :: {} size :: {} ", page, size);
        return ResponseEntity.ok(advertisementService.getAllWithPaginationAndFilters(PageRequest.of(page, size), searchKeyword));
    }

    @PostMapping("/")
    public ResponseEntity<MessageResponse> create(@RequestBody AdvertisementRequest advertisementRequest) {
        logger.info("Create new Advertisement :: {} ", advertisementRequest);
        advertisementService.add(advertisementRequest);
        return new ResponseEntity<>(new MessageResponse("Advertisements created successfully!"), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<MessageResponse> update(@RequestBody AdvertisementRequest advertisementRequest) {
        logger.info("update Advertisement :: {} ", advertisementRequest);
        advertisementService.update(advertisementRequest);
        return new ResponseEntity<>(new MessageResponse("Advertisement Update successfully!"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") Long id) {
        logger.info("Delete a Advertisement ID :: {}", id);
        AdvertisementResponse response = advertisementService.getById(id);
        if (response == null) {
            return new ResponseEntity<>(new MessageResponse("Not found!"), HttpStatus.NOT_FOUND);
        }
        advertisementService.delete(id);
        return new ResponseEntity<>(new MessageResponse("Advertisement delete successfully!"), HttpStatus.OK);
    }
}
