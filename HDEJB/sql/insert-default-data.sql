INSERT INTO FIT5042.USERGROUP
	VALUES (1,'Sunshine Rd','ww@w.com','Eddie' , 'Leung', '8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918','0411234567','Gov Worker');
INSERT INTO FIT5042.USERGROUP
	VALUES (2,'Queen Av','ww@q.com', 'Jennifer','Huang','8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918', '0422334335','Gov Worker'); 
INSERT INTO FIT5042.USERGROUP 
	VALUES (3, 'Queen Av','pu@q.com','Luke', 'Steller','EFA1F375D76194FA51A3556A97E641E61685F914D446979DA50A551A4333FFD7', '0409233432','Public');
INSERT INTO FIT5042.USERGROUP 
	VALUES (4, 'Prince HW','pu@w.com','Ella', 'Swift','EFA1F375D76194FA51A3556A97E641E61685F914D446979DA50A551A4333FFD7', '0409255532','Public');

--all password for government is admin
--all password for public user is public

INSERT INTO FIT5042.SERVICE 
	VALUES (1,'Give at most 10% refund to your goods', 'Tax Refund', 'available','../img/dutyFree.png', 'visitor');
INSERT INTO FIT5042.SERVICE
	VALUES (2,'Business coopereation', 'Business coopereation', 'available','../img/business.png', 'business');
INSERT INTO FIT5042.SERVICE 
	VALUES (3, 'Help you find a suitable job', 'Job seeker', 'available','../img/jobSeeker.png', 'citizen');
INSERT INTO FIT5042.SERVICE
	VALUES (4, 'provide basic training to help you get a job', 'Business training', 'available','../img/training.png', 'citizen');
INSERT INTO FIT5042.SERVICE
	VALUES (5, 'documentation and history search', 'document search', 'unavailable','../img/docSearch.png', 'citizen');
INSERT INTO FIT5042.SERVICE
	VALUES (6, 'consult about migration', 'Migration and refugee', 'available','../img/migration.png', 'visitor');

