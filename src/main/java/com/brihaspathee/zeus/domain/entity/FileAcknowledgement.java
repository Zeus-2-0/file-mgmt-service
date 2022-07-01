package com.brihaspathee.zeus.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 28, June 2022
 * Time: 7:06 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file_acknowledgement")
public class FileAcknowledgement {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "file_ack_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID fileAckSK;

    /**
     * Identifies the file that the acknowledgment is associated
     */
    @ManyToOne
    @JoinColumn(name = "file_detail_sk")
    private FileDetail fileDetail;

    /**
     * The source of the acknowledgement
     */
    @Column(name = "ack_source")
    private String ackSource;

    /**
     * The acknowledgment key provided by the source for the receipt of the file
     */
    @Column(name = "acknowledgement")
    private String acknowledgement;

    /**
     * Date and time when the record was created
     */
    @CreationTimestamp
    @Column(name = "created_date", nullable = true)
    private LocalDateTime createdDate;

    /**
     * Date and time when the record was updated
     */
    @UpdateTimestamp
    @Column(name = "updated_date", nullable = true)
    private LocalDateTime updatedDate;

    /**
     * To String method
     * @return
     */
    @Override
    public String toString() {
        return "FileAcknowledgement{" +
                "fileAckSK=" + fileAckSK +
                ", ackSource='" + ackSource + '\'' +
                ", acknowledgement='" + acknowledgement + '\'' +
                '}';
    }
}
