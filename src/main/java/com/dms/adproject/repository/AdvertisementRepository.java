package com.dms.adproject.repository;

import com.dms.adproject.model.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    Page<Advertisement> findAllByTitleLike(PageRequest pageRequest, String searchKeyword);
}
