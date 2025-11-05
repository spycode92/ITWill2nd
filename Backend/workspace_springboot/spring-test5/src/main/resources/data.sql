INSERT INTO common_code(id, group_code, common_code, common_code_name, description) VALUES (common_code_seq.NEXTVAL, '', 'MEMBER_ROLE', '사용자권한', '사용자권한 상위코드');
INSERT INTO common_code(id, group_code, common_code, common_code_name, description) VALUES (common_code_seq.NEXTVAL, 'MEMBER_ROLE', 'ROLE_ADMIN', '전체 관리자 권한', '');
INSERT INTO common_code(id, group_code, common_code, common_code_name, description) VALUES (common_code_seq.NEXTVAL, 'MEMBER_ROLE', 'ROLE_USER', '일반 사용자 권한', '');
INSERT INTO common_code(id, group_code, common_code, common_code_name, description) VALUES (common_code_seq.NEXTVAL, 'MEMBER_ROLE', 'ROLE_ADMIN_SUB', '보조 관리자 권한', '');
INSERT INTO common_code(id, group_code, common_code, common_code_name, description) VALUES (common_code_seq.NEXTVAL, '', 'MENU', '시스템 메뉴', '시스템 메뉴 상위코드');
INSERT INTO common_code(id, group_code, common_code, common_code_name, description) VALUES (common_code_seq.NEXTVAL, 'MENU', '1', '메뉴 - 공지사항', '');
INSERT INTO common_code(id, group_code, common_code, common_code_name, description) VALUES (common_code_seq.NEXTVAL, 'MENU', '2', '메뉴 - 상품페이지', '');
INSERT INTO common_code(id, group_code, common_code, common_code_name, description) VALUES (common_code_seq.NEXTVAL, '', 'BOARD', '게시판', '게시판 상위코드');
INSERT INTO common_code(id, group_code, common_code, common_code_name, description) VALUES (common_code_seq.NEXTVAL, 'BOARD', '1', '게시판 - 공지', '');
INSERT INTO common_code(id, group_code, common_code, common_code_name, description) VALUES (common_code_seq.NEXTVAL, 'BOARD', '2', '게시판 - 자유', '');

INSERT INTO members(id, name, email, passwd, post_code, address1, address2, reg_date) 
VALUES (
	members_seq.NEXTVAL, 
	'홍길동', 
	'hong@hong.com', 
	'$2a$10$99m24js2BeI5vEKciMTz3OEf7/ljRXhyLbHTz6kKrY5NftPegqBQm', 
	'12345', 
	'부산', 
	'동천로', 
	SYSDATE);
	
INSERT INTO members(id, name, email, passwd, post_code, address1, address2, reg_date) 
VALUES (
	members_seq.NEXTVAL, 
	'관리자', 
	'admin@admin.com', 
	'$2a$10$99m24js2BeI5vEKciMTz3OEf7/ljRXhyLbHTz6kKrY5NftPegqBQm', 
	'22222', 
	'부산', 
	'삼한골든게이트', 
	SYSDATE);

INSERT INTO member_role(id, member_id, member_role_id)
VALUES (member_role_seq.NEXTVAL, 1, 101);

INSERT INTO member_role(id, member_id, member_role_id)
VALUES (member_role_seq.NEXTVAL, 2, 101);

INSERT INTO member_role(id, member_id, member_role_id)
VALUES (member_role_seq.NEXTVAL, 2, 51);


