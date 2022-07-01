package com.brihaspathee.zeus.web.model;

import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 21, June 2022
 * Time: 3:59 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.web.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponseDto {

    /**
     * The acknowledgement key for receiving the file detail
     */
    private String fileReceiptAck;

    /**
     * The name of the service that returns the acknowledgment
     */
    private String serviceName;

    /**
     * The zeus file control number of the file for which the acknowledgement is sent
     */
    private String zeusFileControlNumber;

    /**
     * toString method
     * @return
     */
    @Override
    public String toString() {
        return "FileResponseDto{" +
                "fileReceiptAck='" + fileReceiptAck + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", zeusFileControlNumber='" + zeusFileControlNumber + '\'' +
                '}';
    }
}
