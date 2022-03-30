package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.web.model.TradingPartnerDto;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 30, March 2022
 * Time: 11:16 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TradingPartnerService {

    TradingPartnerDto getTradingPartner(String senderId, String receiverId);
}
