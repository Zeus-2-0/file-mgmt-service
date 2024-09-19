package com.brihaspathee.zeus.web.model;

import com.brihaspathee.zeus.dto.account.AccountDto;
import com.brihaspathee.zeus.dto.account.PremiumPaymentDto;
import com.brihaspathee.zeus.test.ZeusTransactionControlNumber;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 25, December 2023
 * Time: 11:42 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.web.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EDITransactionProcessingRequest {

    /**
     * If an exception is expected the service in which the exception is expected
     */
    private String serviceName;

    /**
     * The name of the folder that is to be used for testing
     */
    private String folderName;

    /**
     * The name of the file that is to be used for testing
     */
    private String fileName;

    /**
     * The icn of the file that is to be used for testing
     */
    private String icn;

    /**
     * Contains the list of transaction control numbers that is to be used for the test
     */
    private List<ZeusTransactionControlNumber> transactionControlNumbers;

    /**
     * The account dto that is to be expected after the transaction is processed successfully
     */
    private Map<String,EDIExpectedOutput> expectedOutput;

    @Override
    public String toString() {
        return "EDITransactionProcessingRequest{" +
                "serviceName='" + serviceName + '\'' +
                ", folderName='" + folderName + '\'' +
                ", transactionControlNumbers=" + transactionControlNumbers +
                ", expectedOutput=" + expectedOutput +
                '}';
    }
}
