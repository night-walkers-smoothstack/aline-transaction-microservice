insert into applicant (id, address, city, created_at, date_of_birth, drivers_license, email, first_name, gender, income, last_modified_at, last_name, mailing_address, mailing_city, mailing_state, mailing_zipcode, middle_name, phone, social_security, state, zipcode)
values (1, '123 Address St', 'City', '2021-08-25', '1997-05-03', 'DC123456', 'test_boy@email.com', 'Test', 'MALE', 5500000, '2021-08-25', 'Boy', 'PO Box 123', 'City', 'New York', '12345', 'Dummy', '(888) 555-5555', '555-55-5555', 'New York', '12345');

insert into applicant (id, address, city, created_at, date_of_birth, drivers_license, email, first_name, gender, income,
                       last_modified_at, last_name, mailing_address, mailing_city, mailing_state, mailing_zipcode,
                       middle_name, phone, social_security, state, zipcode)
values (2, '123 Little Blvd', 'City', '2021-08-06', '1997-06-02', 'DC654987', 'test_man@email.com',
        'Test', 'MALE', 30000000,  '2021-08-21', 'Man', '123 Little Blvd', 'City', 'Maine', '12345',
        'The', '(555) 888-8888', '888-88-8888', 'Maine', '12345');

insert into applicant (id, address, city, created_at, date_of_birth, drivers_license, email, first_name, gender, income,
                       last_modified_at, last_name, mailing_address, mailing_city, mailing_state, mailing_zipcode,
                       middle_name, phone, social_security, state, zipcode)
values (3, '123 Little Blvd', 'City', '2021-08-06', '1997-07-02', 'DC845454', 'test_woman@email.com',
        'Test', 'FEMALE', 30000000,  '2021-08-21', 'Woman', '123 Little Blvd', 'City', 'Maine', '12345',
        'The', '(555) 888-8889', '888-88-8889', 'Maine', '12345');

insert into member (id, membership_id, applicant_id, branch_id)
values (1, '12345678', 1, 1);

insert into member (id, membership_id, applicant_id, branch_id)
values (2, '87654321', 2, 1);

insert into member (id, membership_id, applicant_id, branch_id)
values (3, '98765432', 3, 1);

insert into user (role, id, enabled, password, username, member_id)
values ('MEMBER', 1, 1, 'P@ssword123', 'test_boy', 1);

insert into user (role, id, enabled, password, username, member_id)
values ('MEMBER', 2, 1, 'P@ssword123', 'test_man', 2);

insert into user (role, id, enabled, password, username, member_id)
values ('MEMBER', 3, 1, 'P@ssword123', 'test_woman', 3);

insert into account (account_type, id, account_number, balance, status, available_balance,
                     primary_account_holder_id)
values ('CHECKING', 1, '0011011234', 100000, 'ACTIVE', 100000, 1);

insert into account (account_type, id, account_number, balance, status, apy,
                     primary_account_holder_id)
values ('SAVINGS', 2, '0012021234', 10000000, 'ACTIVE', 0.01, 1);

insert into account (account_type, id, account_number, balance, status, available_balance,
                     primary_account_holder_id)
values ('CHECKING', 3, '0011011235', 100000000, 'ACTIVE', 100000000, 2);

insert into account_holder (member_id, account_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (3, 3);

insert into merchant (code, address, city, description, name, state, zipcode)
values ('ALNE', '123 Address St', 'Townsville', 'Aline Financial Bank', 'Aline Financial Bank', 'Maine', '12345');

insert into transaction (id, type, method, amount, date, account_id, initial_balance, posted_balance, status, merchant_code)
values (1, 'WITHDRAWAL', 'ACH', 10000, '2021-08-01', 1, 100000, 90000, 'APPROVED', 'ALNE');

insert into transaction (id, type, method, amount, date, account_id, initial_balance, posted_balance, status, merchant_code)
values (2, 'PURCHASE', 'CARD', 10000, '2021-08-01', 1, 90000, 80000, 'APPROVED', 'ALNE');

insert into transaction (id, type, method, amount, date, account_id, initial_balance, posted_balance, status, merchant_code)
values (3, 'PURCHASE', 'CARD', 10000, '2021-08-01', 3, 50000, 80000, 'APPROVED', 'ALNE');

insert into transaction (id, type, method, amount, date, account_id, initial_balance, posted_balance, status, merchant_code)
values (4, 'DEPOSIT', 'ACH', 500000, '2021-08-01', 3, 1300000, 80000, 'APPROVED', 'ALNE');

insert into transaction (id, type, method, amount, date, account_id, initial_balance, posted_balance, status, merchant_code)
values (5, 'DEPOSIT', 'ACH', 500000, '2021-08-01', 1, 1300000, 80000, 'APPROVED', 'ALNE');

insert into transaction (id, type, method, amount, date, account_id, initial_balance, posted_balance, status, merchant_code)
values (6, 'WITHDRAWAL', 'ACH', 500000, '2021-08-01', 3, 1800000, 1300000, 'APPROVED', 'ALNE');

insert into transaction (id, type, method, amount, date, account_id, initial_balance, posted_balance, status, merchant_code)
values (7, 'TRANSFER', 'APP', 500000, '2021-08-01', 2, 1300000, 80000, 'APPROVED', 'ALNE');
