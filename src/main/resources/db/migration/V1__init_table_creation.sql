drop table if exists `filemgmtdb`.`file_detail`;
CREATE TABLE IF NOT EXISTS `filemgmtdb`.`file_detail` (
    `file_detail_sk` VARCHAR(36) NOT NULL COMMENT 'Primary key that identifies the file within the table',
    `file_id` VARCHAR(100) NOT NULL COMMENT 'A unique file id that is assigned to the file and is exposed to the users',
    `file_name` VARCHAR(100) NOT NULL COMMENT 'The name of the file',
    `file_received_date` DATETIME NOT NULL COMMENT 'The date when the file was received',
    `sender_id` VARCHAR(100) NOT NULL COMMENT 'The sender id of the trading partner who sent the file',
    `receiver_id` VARCHAR(100) NOT NULL COMMENT 'The receiver id that was registered for us with the trading partner',
    `trading_partner_id` VARCHAR(100) NULL COMMENT 'The trading partner who sent the file',
    `lob_type_code` VARCHAR(45) NULL COMMENT 'The line of business associated with the trading partner',
    `marketplace_type_code` VARCHAR(45) NULL COMMENT 'The marketplace associated with the trading partner',
    `state_type_code` VARCHAR(45) NULL COMMENT 'The state code of the file',
    `created_date` DATETIME NULL COMMENT 'The created date of the record',
    `updated_date` DATETIME NULL COMMENT 'The updated date of the record',
    PRIMARY KEY (`file_detail_sk`),
    UNIQUE INDEX `file_id_UNIQUE` (`file_id` ASC) VISIBLE)
    ENGINE = InnoDB
    COMMENT = 'Contains the details of the file that was received.'