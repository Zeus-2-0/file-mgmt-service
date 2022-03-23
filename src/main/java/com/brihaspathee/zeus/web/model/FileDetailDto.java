package com.brihaspathee.zeus.web.model;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 05, March 2022
 * Time: 9:43 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.web.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDetailDto {

    private UUID fileDetailSK;

    private String fileId;

    private String fileName;

    private LocalDateTime fileReceivedDate;

    private String tradingPartnerId;

    private String senderId;

    private String receiverId;

    private String lineOfBusinessTypeCode;

    private String marketplaceTypeCode;

    private String stateTypeCode;

    private String fileData;

    @Override
    public String toString() {
        return "FileDetailDto{" +
                "fileDetailSK=" + fileDetailSK +
                ", fileId='" + fileId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileReceivedDate=" + fileReceivedDate +
                ", tradingPartnerId='" + tradingPartnerId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", lineOfBusinessTypeCode='" + lineOfBusinessTypeCode + '\'' +
                ", marketplaceTypeCode='" + marketplaceTypeCode + '\'' +
                ", stateTypeCode='" + stateTypeCode + '\'' +
                ", fileData='" + fileData + '\'' +
                '}';
    }
}
