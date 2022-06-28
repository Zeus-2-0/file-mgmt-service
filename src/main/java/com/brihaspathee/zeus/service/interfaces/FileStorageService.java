package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.web.model.FileDetailDto;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 21, June 2022
 * Time: 4:46 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface FileStorageService {

    void postFileDetails(FileDetailDto fileDetailDto);
}
