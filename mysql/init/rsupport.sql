CREATE DATABASE IF NOT EXISTS rsupportdb;
USE rsupportdb;

CREATE TABLE rsupportdb.`notice` (
              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
              `title` varchar(50) NOT NULL COMMENT '제목',
              `content` text COMMENT '내용',
              `start_date` timestamp NULL DEFAULT NULL COMMENT '공지 시작일시',
              `end_date` timestamp NULL DEFAULT NULL COMMENT '공지 종료일시',
              `view_count` bigint(20) NOT NULL COMMENT '조회수',
              `writer` varchar(100) NOT NULL COMMENT '작성자',
              `delete_flag` int(1) DEFAULT NULL COMMENT '삭제여부',
              `create_date` timestamp NULL DEFAULT NULL COMMENT '등록일시',
              `modify_date` timestamp NULL DEFAULT NULL COMMENT '수정일시',
              `version` bigint(20) DEFAULT NULL COMMENT '버전',
              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='공지사항';


CREATE TABLE rsupportdb.`notice_attachment` (
                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                 `notice_id` bigint(20) NOT NULL COMMENT '공지사항 ID',
                 `file_name` varchar(500) NOT NULL COMMENT '파일명',
                 `file` varchar(1000) DEFAULT NULL COMMENT '파일',
                 `create_date` timestamp NULL DEFAULT NULL COMMENT '등록일시',
                 `modify_date` timestamp NULL DEFAULT NULL COMMENT '수정일시',
                 `version` bigint(20) DEFAULT NULL COMMENT '버전',
                 PRIMARY KEY (`id`),
                 KEY `notice_attachment_FK` (`notice_id`),
                 CONSTRAINT `notice_attachment_FK` FOREIGN KEY (`notice_id`) REFERENCES `notice` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='공지사항 첨부파일';

INSERT INTO rsupportdb.notice
(title, content, start_date, end_date, view_count, writer, create_date, modify_date, version, delete_flag)
VALUES('title1', 'content!!', '2024-05-06 00:45:22', NULL, 0, 'SYSTEM', '2024-02-18 17:01:24', '2024-02-18 17:01:35', 1, 1);
INSERT INTO rsupportdb.notice
(title, content, start_date, end_date, view_count, writer, create_date, modify_date, version, delete_flag)
VALUES('title2', 'content!!!!!!', NULL, NULL, 0, 'SYSTEM', '2024-02-18 17:12:11', '2024-02-18 17:12:11', 0, 0);
