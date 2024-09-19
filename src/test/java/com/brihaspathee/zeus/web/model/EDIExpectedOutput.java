package com.brihaspathee.zeus.web.model;

import com.brihaspathee.zeus.dto.account.AccountDto;
import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11, January 2024
 * Time: 6:12 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.web.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EDIExpectedOutput {

    /**
     * Identifies if an exception is expected
     */
    private boolean exceptionExpected;

    /**
     * The exception code when an exception is expected
     */
    private String exceptionCode;

    /**
     * The exception message when an exception is expected
     */
    private String exceptionMessage;

    /**
     * The transaction dto object that is expected from processing the transaction
     */
    private TransactionDto expectedTransactionDto;

    /**
     * The account dto object that is expected from processing the transaction
     */
    private AccountDto expectedAccountDto;

    /**
     * The account dto object that is expected from premium billing
     */
    private AccountDto expectedPBAccountDto;

    /**
     * The way the accounts are expected to be once the premium payments are made
     * The key is the date when the payment was made
     * The value is the Expected Account object which contains
     * the MMS and Premium billing accounts post payment
     */
    private Map<LocalDate, ExpectedAccount> expectedAccounts;
}
