package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.web.model.FileDetailDto;
import com.brihaspathee.zeus.web.model.FileResponseDto;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 22, June 2022
 * Time: 11:45 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface FileTransmissionService {

    void postFileDetails(String url, FileDetailDto fileDetailDto);
}
