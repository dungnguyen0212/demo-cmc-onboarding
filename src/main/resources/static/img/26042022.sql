SELECT o.id, d.user_info_name FROM opportunity o
LEFT JOIN deal d on o.id = d.opportunity_id
WHERE DATE_ADD(o.end_date, INTERVAL 10 DAY) > now() AND o.funnel_id = 2
AND ((o.opportunity_status_id BETWEEN 1 AND 6) OR o.opportunity_status_id = 9)
AND d.published = 1
GROUP BY d.user_info_name;



WITH temp AS (
SELECT sgs.student_id, sgs.grade_id, sgs.subject_id, sgs.start_date, sgs.end_date, sgs.student_status_id,
ROW_NUMBER() OVER (
           PARTITION BY sgs.student_id
           ORDER BY
               sgs.created_at DESC) AS ranking
FROM lms_student_grade_subject sgs LEFT JOIN u_user_account uua on sgs.student_id = uua.id
WHERE uua.user_name IN :useu_user_accountrNames
AND sgs.student_status_id IN (2,5))
SELECT student_id, grade_id, subject_id, start_date, end_date, student_status_id
FROM temp WHERE temp.ranking = 1;

SELECT * FROM opportunity o WHERE opportunity_status_id = 8 AND o.funnel_id = 1;

INSERT INTO `clevai_ingest_db_dev`.`u_user_account` (`id`, `user_unique_id`, `user_name`, `user_account_type_id`, `first_name`, `last_name`, `full_name`, `job`, `district_id`, `school_id`, `school_year_id`, `grade_id`, `phone`, `gender`, `accept_term_of_services`, `account_status_id`, `created_at`, `updated_at`) VALUES ('2', '2', '0399444470', '1', 'Nguyễn Võ Phương', 'Nhi', 'Nguyễn Võ Phương Nhi', 'Học sinh', '1', '1', '1', '4', '0399444470', '1', '1', '3', '2020-08-03 10:27:29.000000', '2020-08-03 10:27:29.000000');
INSERT INTO `clevai_ingest_db_dev`.`u_user_account` (`id`, `user_unique_id`, `user_name`, `user_account_type_id`, `first_name`, `last_name`, `full_name`, `job`, `district_id`, `school_id`, `school_year_id`, `grade_id`, `phone`, `gender`, `accept_term_of_services`, `account_status_id`, `created_at`, `updated_at`) VALUES ('3', '3', '0798515185', '1', 'Nguyễn Hoàng', 'Nam', 'Nguyễn Hoàng Nam', 'Học sinh', '1', '1', '1', '4', '0798515185', '1', '1', '3', '2020-08-03 10:27:29.000000', '2020-08-03 10:27:29.000000');
INSERT INTO `clevai_ingest_db_dev`.`u_user_account` (`id`, `user_unique_id`, `user_name`, `user_account_type_id`, `first_name`, `last_name`, `full_name`, `job`, `district_id`, `school_id`, `school_year_id`, `grade_id`, `phone`, `gender`, `accept_term_of_services`, `account_status_id`, `created_at`, `updated_at`) VALUES ('4', '4', '0933557789', '1', 'Nguyễn Nhật', 'Nam', 'Nguyễn Nhật Nam', 'Học sinh', '1', '1', '1', '4', '0933557789', '1', '1', '3', '2020-08-03 10:27:29.000000', '2020-08-03 10:27:29.000000');
INSERT INTO `clevai_ingest_db_dev`.`u_user_account` (`id`, `user_unique_id`, `user_name`, `user_account_type_id`, `first_name`, `last_name`, `full_name`, `job`, `district_id`, `school_id`, `school_year_id`, `grade_id`, `phone`, `gender`, `accept_term_of_services`, `account_status_id`, `created_at`, `updated_at`) VALUES ('5', '5', '0902989588', '1', 'Nguyễn Thái', 'Ân', 'Nguyễn Thái Ân', 'Học sinh', '1', '1', '1', '4', '0902989588', '1', '1', '3', '2020-08-03 10:27:29.000000', '2020-08-03 10:27:29.000000');
INSERT INTO `clevai_ingest_db_dev`.`u_user_account` (`id`, `user_unique_id`, `user_name`, `user_account_type_id`, `first_name`, `last_name`, `full_name`, `job`, `district_id`, `school_id`, `school_year_id`, `grade_id`, `phone`, `gender`, `accept_term_of_services`, `account_status_id`, `created_at`, `updated_at`) VALUES ('6', '6', '0902600001', '1', 'Đinh Trí', 'Dũng', 'Đinh Trí Dũng', 'Học sinh', '1', '1', '1', '4', '0902600001', '1', '1', '3', '2020-08-03 10:27:29.000000', '2020-08-03 10:27:29.000000');
