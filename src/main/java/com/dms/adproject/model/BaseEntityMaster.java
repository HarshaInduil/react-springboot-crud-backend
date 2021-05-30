package com.dms.adproject.model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntityMaster implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(BaseEntityMaster.class);

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Version
    @Column(name = "version")
    private Long version;

    @PrePersist
    protected void onCreate() {
        String username = "";
        try {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            logger.info("User get issue on Application context : {}", e);
        }

        createdDate = LocalDateTime.now(ZoneId.systemDefault());
        createdBy = username;
        version = 0L;
    }

    @PreUpdate
    protected void onUpdate() {
        String username = "";
        try {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            logger.info("User get issue on Application context : {}", e);
        }
        updatedDate = LocalDateTime.now(ZoneId.systemDefault());
        updatedBy = username;
    }
}
