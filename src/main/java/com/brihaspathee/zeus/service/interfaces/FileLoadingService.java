package com.brihaspathee.zeus.service.interfaces;


import org.springframework.core.io.Resource;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 21, March 2022
 * Time: 1:23 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface FileLoadingService {

    Resource[] loadEDIFiles() throws Exception;
}
