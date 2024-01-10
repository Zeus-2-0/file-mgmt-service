package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.test.ZeusTransactionControlNumber;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 21, March 2022
 * Time: 3:14 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface FileService {

    void processFile(Resource resource, List<ZeusTransactionControlNumber> testTransactionControlNumbers) throws IOException;
}
