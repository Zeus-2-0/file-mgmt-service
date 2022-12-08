package com.brihaspathee.zeus.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 30, March 2022
 * Time: 11:31 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file_detail")
public class FileDetail {

    @Id
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "file_detail_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID fileDetailSK;

    @Column(name = "zfcn", nullable = false, length = 100)
    private String zeusFileControlNumber;

    @Column(name = "file_name", nullable = false, length = 100)
    private String fileName;

    @Column(name = "file_received_date", nullable = false)
    private LocalDateTime fileReceivedDate;

    @Column(name = "sender_id", nullable = false, length=100)
    private String senderId;

    @Column(name = "receiver_id", nullable = false, length=100)
    private String receiverId;

    @Column(name = "trading_partner_id", nullable = true, length=100)
    private String tradingPartnerId;

    @Column(name = "line_of_business_type_code", nullable = true, length=50)
    private String lineOfBusinessTypeCode;

    @Column(name = "business_unit_type_code", nullable = true, length=50)
    private String businessUnitTypeCode;

    @Column(name = "marketplace_type_code", nullable = true, length=50)
    private String marketplaceTypeCode;

    @Column(name = "state_type_code", nullable = true, length=2)
    private String stateTypeCode;

    @CreationTimestamp
    @Column(name = "created_date", nullable = true)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = true)
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "fileDetail")
    private Set<FileAcknowledgement> fileAcknowledgement;

    @Override
    public String toString() {
        return "FileDetail{" +
                "fileDetailSK=" + fileDetailSK +
                ", zfcn='" + zeusFileControlNumber + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileReceivedDate=" + fileReceivedDate +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", tradingPartnerId='" + tradingPartnerId + '\'' +
                ", lineOfBusinessTypeCode='" + lineOfBusinessTypeCode + '\'' +
                ", marketplaceTypeCode='" + marketplaceTypeCode + '\'' +
                ", stateTypeCode='" + stateTypeCode + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
