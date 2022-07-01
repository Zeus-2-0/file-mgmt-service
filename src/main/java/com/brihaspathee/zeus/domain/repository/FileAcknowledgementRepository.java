package com.brihaspathee.zeus.domain.repository;

import com.brihaspathee.zeus.domain.entity.FileAcknowledgement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 29, June 2022
 * Time: 5:44 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.domain.repository
 * To change this template use File | Settings | File and Code Template
 */
@Repository
public interface FileAcknowledgementRepository extends JpaRepository<FileAcknowledgement, UUID> {
}
