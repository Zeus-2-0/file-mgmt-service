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

    private String fileReceiptAck;

    @Override
    public String toString() {
        return "FileResponseDto{" +
                "fileReceiptAck='" + fileReceiptAck + '\'' +
                '}';
    }
}
