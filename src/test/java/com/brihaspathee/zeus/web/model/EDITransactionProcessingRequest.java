package com.brihaspathee.zeus.web.model;

import com.brihaspathee.zeus.dto.account.AccountDto;
import com.brihaspathee.zeus.test.ZeusTransactionControlNumber;
import lombok.*;

import java.util.List;

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
     * Identifies if an exception is expected
     */
    private boolean exceptionExpected;

    /**
     * If an exception is expected the service in which the exception is expected
     */
    private String serviceName;

    /**
     * The exception code when an exception is expected
     */
    private String exceptionCode;

    /**
     * The exception message when an exception is expected
     */
    private String exceptionMessage;

    /**
     * The name of the file that is to be used for testing
     */
    private String fileName;

    /**
     * Contains the list of transaction control numbers that is to be used for the test
     */
    private List<ZeusTransactionControlNumber> transactionControlNumbers;

    /**
     * The account dto that is to be expected after the transaction is processed successfully
     */
    private AccountDto expectedAccountDto;

    @Override
    public String toString() {
        return "EDITransactionProcessingRequest{" +
                "exceptionExpected=" + exceptionExpected +
                ", exceptionCode='" + exceptionCode + '\'' +
                ", exceptionMessage='" + exceptionMessage + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
