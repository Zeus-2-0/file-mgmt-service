package com.brihaspathee.zeus.web.model;

import com.brihaspathee.zeus.dto.account.AccountDto;
import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 20, July 2024
 * Time: 12:32 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.web.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpectedAccount {

    private AccountDto expectedAccountDto;

    private AccountDto expectedPBAccountDto;
}
