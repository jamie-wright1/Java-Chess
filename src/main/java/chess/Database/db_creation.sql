DROP TABLE IF EXISTS opening;
DROP TABLE IF EXISTS elo_change;
DROP TABLE IF EXISTS head_to_head;
DROP TABLE IF EXISTS turn;
DROP TABLE IF EXISTS game;
DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS friendship;
DROP TABLE IF EXISTS account;

CREATE TABLE account (
    username VARCHAR(20) PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    date_created DATE NOT NULL,
    elo INT NOT NULL,
    peak_elo INT NOT NULL,
    record VARCHAR(8) NOT NULL
);


CREATE table friendship (
    user_one VARCHAR(20),
    user_two VARCHAR(20),
    date_friended DATE NOT NULL,
    PRIMARY KEY( user_one, user_two),
    FOREIGN KEY (user_one) REFERENCES account(username),
    FOREIGN KEY (user_two) REFERENCES account(username)
);

CREATE TABLE message (
    user_one VARCHAR(20),
    user_two VARCHAR(20),
    user_sent VARCHAR(20) NOT NULL,
    message VARCHAR (80) NOT NULL,
    date_sent DATE NOT NULL,
    time_sent TIME NOT NULL,
    PRIMARY KEY (user_one, user_two, date_sent, time_sent),
    FOREIGN KEY (user_one) REFERENCES account(username),
    FOREIGN KEY (user_two) REFERENCES account(username)
);

CREATE table game (
    game_id INT PRIMARY KEY,
    date DATE NOT NULl,
    time TIME NOT NULL,
    white_player VARCHAR(20) NOT NULL,
    black_player VARCHAR(20) NOT NULL,
    white_won BOOlEAN NOT NULL,
    FOREIGN KEY (white_player) REFERENCES account(username),
    FOREIGN KEY (black_player) REFERENCES account(username)
);

CREATE TABLE turn (
    game_id INT,
    turn_number INT,
    move VARCHAR(10) NOT NULL,
    PRIMARY KEY (game_id, turn_number),
    FOREIGN KEY (game_id) REFERENCES game(game_id)
);

CREATE TABLE head_to_head (
    user_one VARCHAR(20),
    user_two VARCHAR(20),
    record VARCHAR(8) NOT NULL,
    PRIMARY KEY (user_one, user_two)
);

INSERT INTO account (username, name, date_created, elo, peak_elo, record) VALUES
('victorlee5', 'Victor', '2023-04-26', 1319, 1531, '28-32-10'),
('samharris36', 'Sam', '2022-12-18', 1267, 1493, '22-30-13'),
('ninaclark202', 'Nina', '2023-03-07', 1207, 1453, '40-22-8'),
('peterevans23', 'Peter', '2022-10-22', 1153, 1409, '38-18-7'),
('xavieroconnor8', 'Xavier', '2023-05-04', 1099, 1351, '34-23-12'),
('bobjohnson12', 'Bob', '2023-08-24', 1043, 1307, '30-20-5'),
('thomasjackson5', 'Thomas', '2023-02-22', 984, 1253, '45-15-5'),
('amyquinn24', 'Amy', '2023-11-18', 933, 1201, '25-30-12'),
('rapidmaster3000', 'David', '2022-11-06', 2257, 2523, '50-10-5'),
('alicesmith56', 'Alice', '2023-06-12', 879, 1123, '25-30-10'),
('Enter Username', 'j', '2024-12-11', 0, 0, '0-0-0'),
('j', 'j', '2024-12-12', 0, 0, '0-0-0'),
('jamie', 'jamie', '2024-12-13', 0, 0, '00-00-00'),
('Jamie Wright', 'Jamie Wright', '2024-12-13', 0, 0, '00-00-00'),
('rachelisbell7', 'Rachel', '2023-07-01', 1429, 1637, '421-10'),
('charliewilliams9', 'Charlie', '2023-03-15', 1373, 1601, '204-15'),
('Jogilvy', 'Jamie', '2024-12-07', 30, 0, '20-20-20'),
('blitzmaster2024', 'David', '2022-11-06', 2127, 2423, '61-05-00'),
('chessking87', 'Steve', '2022-09-13', 2113, 2347, '45-17-8'),
('bishopbrian99', 'Brian', '2023-03-21', 2027, 2293, '43-18-9'),
('strategistgirl21', 'Helen', '2023-04-01', 1993, 2209, '40-19-10'),
('rookiejack56', 'Jack', '2023-01-17', 1931, 2167, '40-20-10'),
('grandmastergeorge', 'George', '2022-11-10', 1879, 2191, '41-20-9'),
('queenjulia88', 'Julia', '2023-07-10', 1823, 2119, '42-22-6'),
('veracheckmate74', 'Vera', '2023-07-21', 1779, 2041, '42-22-6'),
('lucycheck31', 'Lucy', '2023-02-15', 1719, 1999, '42-18-8'),
('xaviernelson22', 'Xavier', '2023-09-18', 1667, 1933, '36-22-7'),
('pamschess13', 'Pam', '2023-09-10', 1583, 1897, '34-25-10'),
('gracemartinez45', 'Grace', '2023-05-30', 1537, 1861, '35-25-10'),
('umakingmove9', 'Uma', '2023-10-09', 1467, 1789, '35-25-10'),
('isaacthoughts3', 'Isaac', '2023-09-23', 1399, 1711, '28-22-10'),
('queenquinn7', 'Quinn', '2023-05-01', 1343, 1673, '28-22-15'),
('fisherquinn72', 'Quinn', '2023-11-01', 1291, 1613, '30-24-8'),
('danielmoves85', 'Daniel', '2023-01-08', 1243, 1567, '36-24-8'),
('ianchess49', 'Ian', '2023-09-05', 1189, 1507, '33-28-10'),
('yaraqueen111', 'Yara', '2023-06-03', 1137, 1463, '40-25-8'),
('rachelgreen22', 'Rachel', '2023-07-13', 1087, 1411, '42-19-9'),
('mikedawson33', 'Mike', '2023-08-30', 1039, 1343, '37-23-10'),
('liamadams57', 'Liam', '2023-06-25', 998, 1307, '40-20-5'),
('zackperez10', 'Zack', '2022-10-11', 953, 1249, '44-15-6'),
('hannahgamer92', 'Hannah', '2022-12-10', 917, 1189, '10-45-10'),
('tinaking25', 'Tina', '2023-08-01', 875, 1133, '30-25-10'),
('frankvance81', 'Frank', '2023-05-15', 841, 1087, '18-40-12'),
('kathywhite44', 'Kathy', '2023-04-18', 793, 1029, '18-38-9'),
('williamnewton10', 'William', '2023-06-18', 753, 981, '20-35-13'),
('frankgarcia20', 'Frank', '2023-02-05', 703, 941, '15-40-5'),
('wendychess88', 'Wendy', '2022-07-30', 669, 897, '12-38-10'),
('oliverfoster11', 'Oliver', '2022-10-17', 617, 859, '13-39-8'),
('evejones101', 'Eve', '2023-07-19', 1567, 1793, '45-15-10'),
('ericamoves23', 'Erica', '2023-06-09', 1501, 1739, '38-20-7'),
('jogilvy', 'Edwin Wright', '2005-09-08', 1663, 1777, '50-00-00');

INSERT INTO game VALUES
(1, '2024-12-01', '14:00:00', 'victorlee5', 'samharris36', true),
(2, '2024-12-01', '15:00:00', 'ninaclark202', 'peterevans23', false),
(3, '2024-12-02', '12:30:00', 'xavieroconnor8', 'bobjohnson12', true),
(4, '2024-12-02', '13:00:00', 'thomasjackson5', 'amyquinn24', false),
(5, '2024-12-03', '14:30:00', 'rapidmaster3000', 'alicesmith56', true),
(6, '2024-12-03', '15:00:00', 'Enter Username', 'j', false),
(7, '2023-12-02', '12:00:00', 'rachelisbell7', 'bishopbrian99', false),
(8, '2024-12-04', '17:00:00', 'rachelisbell7', 'charliewilliams9', false),
(9, '2024-12-05', '14:00:00', 'Jogilvy', 'blitzmaster2024', true),
(10, '2024-12-05', '15:00:00', 'chessking87', 'bishopbrian99', false),
(11, '2024-12-06', '16:00:00', 'strategistgirl21', 'rookiejack56', true),
(12, '2024-12-06', '17:00:00', 'grandmastergeorge', 'queenjulia88', false),
(13, '2024-12-07', '14:30:00', 'veracheckmate74', 'lucycheck31', true),
(14, '2024-12-07', '15:00:00', 'xaviernelson22', 'pamschess13', false),
(15, '2024-12-08', '12:00:00', 'gracemartinez45', 'umakingmove9', true),
(16, '2024-12-08', '13:30:00', 'isaacthoughts3', 'queenquinn7', false),
(17, '2024-12-09', '14:30:00', 'fisherquinn72', 'danielmoves85', true),
(18, '2024-12-09', '15:00:00', 'ianchess49', 'yaraqueen111', false),
(19, '2024-12-10', '16:00:00', 'rachelgreen22', 'mikedawson33', true),
(20, '2024-12-10', '17:30:00', 'liamadams57', 'zackperez10', false),
(21, '2024-12-11', '14:00:00', 'hannahgamer92', 'tinaking25', true),
(22, '2024-12-11', '15:30:00', 'frankvance81', 'kathywhite44', false),
(23, '2024-12-12', '16:30:00', 'williamnewton10', 'frankgarcia20', true),
(24, '2024-12-12', '17:00:00', 'wendychess88', 'oliverfoster11', false),
(25, '2024-12-13', '14:00:00', 'evejones101', 'ericamoves23', true),
(26, '2024-12-13', '15:00:00', 'victorlee5', 'samharris36', false),
(27, '2024-12-14', '14:30:00', 'ninaclark202', 'peterevans23', true),
(28, '2024-12-14', '15:30:00', 'xavieroconnor8', 'bobjohnson12', false),
(29, '2024-12-15', '12:30:00', 'thomasjackson5', 'amyquinn24', true),
(30, '2024-12-15', '13:00:00', 'rapidmaster3000', 'alicesmith56', false),
(31, '2024-12-16', '14:00:00', 'Enter Username', 'j', true),
(32, '2024-12-16', '14:00:00', 'rachelisbell7', 'j', true),
(33, '2024-12-17', '16:00:00', 'rachelisbell7', 'charliewilliams9', true),
(34, '2024-12-17', '17:00:00', 'Jogilvy', 'blitzmaster2024', false),
(35, '2024-12-18', '14:00:00', 'chessking87', 'bishopbrian99', true),
(36, '2024-12-18', '15:30:00', 'strategistgirl21', 'rookiejack56', false),
(37, '2024-12-19', '14:30:00', 'grandmastergeorge', 'queenjulia88', true),
(38, '2024-12-19', '15:00:00', 'veracheckmate74', 'lucycheck31', false),
(39, '2024-12-20', '16:00:00', 'xaviernelson22', 'pamschess13', true),
(40, '2024-12-20', '17:30:00', 'gracemartinez45', 'umakingmove9', false),
(41, '2024-12-21', '14:00:00', 'isaacthoughts3', 'queenquinn7', true),
(42, '2024-12-21', '15:00:00', 'fisherquinn72', 'danielmoves85', false),
(43, '2024-12-22', '16:00:00', 'ianchess49', 'yaraqueen111', true),
(44, '2024-12-22', '17:30:00', 'rachelgreen22', 'mikedawson33', false),
(45, '2024-12-23', '14:00:00', 'liamadams57', 'zackperez10', true),
(46, '2024-12-23', '15:00:00', 'hannahgamer92', 'tinaking25', false),
(47, '2024-12-24', '14:00:00', 'frankvance81', 'kathywhite44', true),
(48, '2024-12-24', '15:30:00', 'williamnewton10', 'frankgarcia20', false),
(49, '2024-12-25', '16:00:00', 'wendychess88', 'oliverfoster11', true),
(50, '2024-12-25', '17:00:00', 'evejones101', 'ericamoves23', false),
(51, '2024-12-26', '14:30:00', 'victorlee5', 'samharris36', true),
(52, '2024-12-26', '15:30:00', 'ninaclark202', 'peterevans23', false),
(53, '2024-12-27', '12:00:00', 'xavieroconnor8', 'bobjohnson12', true),
(54, '2024-12-27', '13:30:00', 'thomasjackson5', 'amyquinn24', false),
(55, '2024-12-28', '14:00:00', 'rapidmaster3000', 'alicesmith56', true),
(56, '2024-12-28', '15:00:00', 'Enter Username', 'j', false),
(57, '2024-12-28', '15:00:00', 'Enter Username', 'j', false),
(58, '2024-12-29', '17:30:00', 'rachelisbell7', 'charliewilliams9', false),
(59, '2024-12-30', '14:30:00', 'Jogilvy', 'blitzmaster2024', true),
(60, '2024-12-30', '15:00:00', 'chessking87', 'bishopbrian99', false),
(61, '2024-12-31', '14:00:00', 'strategistgirl21', 'rookiejack56', true),
(62, '2024-12-31', '15:30:00', 'grandmastergeorge', 'queenjulia88', false),
(63, '2025-01-01', '12:30:00', 'veracheckmate74', 'lucycheck31', true),
(64, '2025-01-01', '13:00:00', 'xaviernelson22', 'pamschess13', false),
(65, '2025-01-02', '14:30:00', 'gracemartinez45', 'umakingmove9', true),
(66, '2025-01-02', '15:00:00', 'isaacthoughts3', 'queenquinn7', false),
(67, '2025-01-03', '16:00:00', 'fisherquinn72', 'danielmoves85', true),
(68, '2025-01-03', '17:30:00', 'ianchess49', 'yaraqueen111', false),
(69, '2025-01-04', '14:00:00', 'rachelgreen22', 'mikedawson33', true),
(70, '2025-01-04', '15:00:00', 'liamadams57', 'zackperez10', false),
(71, '2025-01-05', '12:30:00', 'hannahgamer92', 'tinaking25', true),
(72, '2025-01-05', '13:00:00', 'frankvance81', 'kathywhite44', false),
(73, '2025-01-06', '14:00:00', 'williamnewton10', 'frankgarcia20', true),
(74, '2025-01-06', '15:30:00', 'wendychess88', 'oliverfoster11', false),
(75, '2025-01-07', '16:30:00', 'evejones101', 'ericamoves23', true),
(76, '2025-01-07', '17:00:00', 'victorlee5', 'samharris36', false),
(77, '2025-01-08', '14:00:00', 'ninaclark202', 'peterevans23', true),
(78, '2025-01-08', '15:00:00', 'xavieroconnor8', 'bobjohnson12', false),
(79, '2025-01-09', '14:30:00', 'thomasjackson5', 'amyquinn24', true),
(80, '2025-01-09', '15:00:00', 'rapidmaster3000', 'alicesmith56', false),
(81, '2025-01-10', '14:30:00', 'Enter Username', 'j', true),
(82, '2025-01-10', '14:30:00', 'Enter Username', 'j', true),
(83, '2025-01-11', '16:00:00', 'rachelisbell7', 'charliewilliams9', true),
(84, '2025-01-11', '17:00:00', 'Jogilvy', 'blitzmaster2024', false),
(85, '2025-01-12', '14:00:00', 'chessking87', 'bishopbrian99', true),
(86, '2025-01-12', '15:30:00', 'strategistgirl21', 'rookiejack56', false),
(87, '2025-01-12', '15:30:00', 'strategistgirl21', 'jogilvy', false),
(88, '2025-01-13', '15:00:00', 'veracheckmate74', 'lucycheck31', false),
(89, '2025-01-14', '16:00:00', 'xaviernelson22', 'pamschess13', true),
(90, '2025-01-14', '17:30:00', 'gracemartinez45', 'umakingmove9', false),
(91, '2025-01-15', '14:00:00', 'isaacthoughts3', 'queenquinn7', true),
(92, '2025-01-15', '15:00:00', 'fisherquinn72', 'danielmoves85', false),
(93, '2025-01-16', '16:00:00', 'ianchess49', 'yaraqueen111', true),
(94, '2025-01-16', '17:30:00', 'rachelgreen22', 'mikedawson33', false),
(95, '2025-01-17', '14:00:00', 'liamadams57', 'zackperez10', true),
(96, '2025-01-17', '15:00:00', 'hannahgamer92', 'tinaking25', false),
(97, '2025-01-18', '14:00:00', 'frankvance81', 'kathywhite44', true),
(98, '2025-01-18', '15:30:00', 'williamnewton10', 'frankgarcia20', false),
(99, '2025-01-19', '16:00:00', 'wendychess88', 'oliverfoster11', true),
(100, '2025-01-19', '17:00:00', 'evejones101', 'ericamoves23', false);

INSERT INTO friendship (user_one, user_two, date_friended) VALUES
('xavieroconnor8', 'bobjohnson12', '2002-04-04'),
('thomasjackson5', 'amyquinn24', '2005-03-06'),
('rapidmaster3000', 'alicesmith56', '2002-10-05'),
('charliewilliams9', 'chessking87', '2001-03-02'),
('blitzmaster2024', 'strategistgirl21', '2003-08-04'),
('bishopbrian99', 'grandmastergeorge', '2003-06-04'),
('rookiejack56', 'grandmastergeorge', '2003-07-05'),
('queenjulia88', 'veracheckmate74', '2002-09-06'),
('lucycheck31', 'xaviernelson22', '2002-04-03'),
('pamschess13', 'gracemartinez45', '2005-05-02'),
('umakingmove9', 'isaacthoughts3', '2001-03-01'),
('kathywhite44', 'fisherquinn72', '2002-04-03'),
('danielmoves85', 'ianchess49', '2001-06-03'),
('yaraqueen111', 'rachelgreen22', '2002-05-04'),
('mikedawson33', 'liamadams57', '2003-04-03'),
('frankvance81', 'hannahgamer92', '2002-02-05'),
('tinaking25', 'frankvance81', '2006-07-02'),
('tinaking25', 'williamnewton10', '2001-03-06'),
('yaraqueen111', 'wendychess88', '2001-08-07'),
('oliverfoster11', 'evejones101', '2002-04-04'),
('ericamoves23', 'victorlee5', '2003-06-05'),
('samharris36', 'ninaclark202', '2001-03-03'),
('peterevans23', 'xavieroconnor8', '2001-07-06'),
('bobjohnson12', 'thomasjackson5', '2002-02-01'),
('bobjohnson12', 'rapidmaster3000', '2003-01-04'),
('jogilvy', 'jamie', '2000-06-05'),
('veracheckmate74', 'charliewilliams9', '2000-03-02'),
('jogilvy', 'blitzmaster2024', '2001-05-03'),
('chessking87', 'bishopbrian99', '2002-02-04'),
('veracheckmate74', 'rookiejack56', '2001-07-04'),
('jogilvy', 'queenjulia88', '2000-04-04'),
('veracheckmate74', 'lucycheck31', '2004-05-03'),
('xaviernelson22', 'pamschess13', '2002-06-04'),
('gracemartinez45', 'umakingmove9', '2001-03-02'),
('isaacthoughts3', 'queenquinn7', '2003-05-02'),
('fisherquinn72', 'yaraqueen111', '2000-06-05'),
('ianchess49', 'yaraqueen111', '2001-02-06'),
('rachelgreen22', 'mikedawson33', '2000-03-03'),
('liamadams57', 'yaraqueen111', '2001-04-04'),
('hannahgamer92', 'oliverfoster11', '2000-02-05'),
('frankvance81', 'samharris36', '2002-04-06'),
('williamnewton10', 'frankgarcia20', '2001-05-04'),
('wendychess88', 'jogilvy', '2002-03-07'),
('evejones101', 'victorlee5', '2001-06-03'),
('victorlee5', 'samharris36', '2001-08-02'),
('ninaclark202', 'peterevans23', '2002-04-03'),
('jamie', 'rachelisbell7', '2002-04-03'),
('charliewilliams9', 'jogilvy', '2001-02-01'),
('blitzmaster2024', 'chessking87', '2002-06-05'),
('bishopbrian99', 'strategistgirl21', '2001-05-03'),
('umakingmove9', 'grandmastergeorge', '2002-07-02'),
('umakingmove9', 'veracheckmate74', '2003-08-04'),
('queenquinn7', 'xaviernelson22', '2003-06-02'),
('mikedawson33', 'gracemartinez45', '2001-04-05'),
('umakingmove9', 'fisherquinn72', '2002-06-04'),
('queenquinn7', 'fisherquinn72', '2002-03-02'),
('mikedawson33', 'ianchess49', '2000-05-04'),
('frankgarcia20', 'rachelgreen22', '2002-02-03'),
('ericamoves23', 'liamadams57', '2002-04-05'),
('zackperez10', 'hannahgamer92', '2002-03-01'),
('bobjohnson12', 'frankvance81', '2001-06-04'),
('kathywhite44', 'williamnewton10', '2002-04-04'),
('frankgarcia20', 'wendychess88', '2001-05-06'),
('ninaclark202', 'evejones101', '2000-02-05'),
('peterevans23', 'victorlee5', '2001-04-05'),
('chessking87', 'ninaclark202', '2001-02-06'),
('amyquinn24', 'xavieroconnor8', '2003-05-02'),
('strategistgirl21', 'thomasjackson5', '2000-03-04'),
('amyquinn24', 'rapidmaster3000', '2002-02-03'),
('alicesmith56', 'jamie', '2000-03-06'),
('rachelisbell7', 'charliewilliams9', '2002-04-01'),
('chessking87', 'blitzmaster2024', '2001-06-02'),
('chessking87', 'rookiejack56', '2001-07-04'),
('strategistgirl21', 'rookiejack56', '2001-03-02'),
('grandmastergeorge', 'queenjulia88', '2000-04-03'),
('veracheckmate74', 'pamschess13', '2001-03-04'),
('xaviernelson22', 'danielmoves85', '2000-02-05'),
('gracemartinez45', 'tinaking25', '2002-03-01'),
('strategistgirl21', 'queenquinn7', '2003-04-03'),
('fisherquinn72', 'danielmoves85', '2000-02-05'),
('ianchess49', 'zackperez10', '2002-05-02'),
('rachelgreen22', 'kathywhite44', '2000-06-03'),
('liamadams57', 'zackperez10', '2001-05-04'),
('hannahgamer92', 'tinaking25', '2000-04-05'),
('frankvance81', 'kathywhite44', '2001-05-02'),
('williamnewton10', 'ericamoves23', '2000-06-05'),
('wendychess88', 'oliverfoster11', '2002-04-06'),
('evejones101', 'ericamoves23', '2001-03-02');

INSERT INTO message VALUES
('victorlee5', 'samharris36', 'victorlee5', 'Hey, how are you?', '2024-12-01', '09:00'),
('ninaclark202', 'peterevans23', 'peterevans23', 'I have a great idea for a new game.', '2024-12-01', '10:15'),
('xavieroconnor8', 'bobjohnson12', 'xavieroconnor8', 'Good game last night!', '2024-12-02', '11:20'),
('thomasjackson5', 'amyquinn24', 'amyquinn24', 'Lets play again soon!', '2024-12-02', '12:30'),
('rapidmaster3000', 'alicesmith56', 'rapidmaster3000', 'I love your strategy, very impressive!', '2024-12-03', '14:00'),
('Enter Username', 'j', 'j', 'Welcome to the game!', '2024-12-03', '15:45'),
('jamie', 'jamie', 'jamie', 'Cant wait to get started!', '2024-12-04', '08:00'),
('rachelisbell7', 'charliewilliams9', 'charliewilliams9', 'I think we should try a new opening.', '2024-12-04', '09:25'),
('Jogilvy', 'blitzmaster2024', 'blitzmaster2024', 'Ive been practicing for our next match.', '2024-12-05', '10:45'),
('chessking87', 'bishopbrian99', 'chessking87', 'Im improving every day!', '2024-12-05', '11:55'),
('strategistgirl21', 'rookiejack56', 'strategistgirl21', 'I’m really enjoying these matches.', '2024-12-06', '13:05'),
('grandmastergeorge', 'queenjulia88', 'queenjulia88', 'Your last move was brilliant!', '2024-12-06', '14:15'),
('veracheckmate74', 'lucycheck31', 'veracheckmate74', 'Nice job on the defense.', '2024-12-07', '16:25'),
('xaviernelson22', 'pamschess13', 'xaviernelson22', 'I think I’ll win this one!', '2024-12-07', '17:35'),
('gracemartinez45', 'umakingmove9', 'umakingmove9', 'This move might surprise you!', '2024-12-08', '18:45'),
('isaacthoughts3', 'queenquinn7', 'queenquinn7', 'I’ve been reading about new tactics.', '2024-12-08', '19:55'),
('fisherquinn72', 'danielmoves85', 'danielmoves85', 'I’ll be ready for your next challenge!', '2024-12-09', '20:15'),
('ianchess49', 'yaraqueen111', 'ianchess49', 'I feel confident in this game.', '2024-12-09', '21:00'),
('rachelgreen22', 'mikedawson33', 'rachelgreen22', 'Let’s see who wins this one.', '2024-12-10', '22:10'),
('liamadams57', 'zackperez10', 'liamadams57', 'Your tactics are tough to counter.', '2024-12-10', '23:20'),
('hannahgamer92', 'tinaking25', 'tinaking25', 'This match will be intense!', '2024-12-11', '09:35'),
('frankvance81', 'kathywhite44', 'frankvance81', 'Good luck with the next game.', '2024-12-11', '10:45'),
('williamnewton10', 'frankgarcia20', 'frankgarcia20', 'I think this will be a close one.', '2024-12-12', '11:50'),
('wendychess88', 'oliverfoster11', 'wendychess88', 'Nice move, didn’t expect that.', '2024-12-12', '12:30'),
('evejones101', 'ericamoves23', 'evejones101', 'Cannot wait for our next match.', '2024-12-13', '14:05'),
('victorlee5', 'samharris36', 'samharris36', 'Lets play again soon!', '2024-12-13', '15:30'),
('ninaclark202', 'peterevans23', 'ninaclark202', 'The game was intense, I almost had you.', '2024-12-14', '16:20'),
('xavieroconnor8', 'bobjohnson12', 'xavieroconnor8', 'I’ll practice more for next time.', '2024-12-14', '17:10'),
('thomasjackson5', 'amyquinn24', 'thomasjackson5', 'Your strategy caught me off guard.', '2024-12-15', '18:25'),
('rapidmaster3000', 'alicesmith56', 'rapidmaster3000', 'I’ll be ready for a rematch!', '2024-12-15', '19:35'),
('Enter Username', 'j', 'Enter Username', 'Looking forward to your next move!', '2024-12-16', '20:45'),
('jamie', 'Jogilvy', 'jamie', 'Ready to play! Let’s start.', '2024-12-16', '21:50'),
('rachelisbell7', 'charliewilliams9', 'rachelisbell7', 'That game was close, great match.', '2024-12-17', '22:05'),
('Jogilvy', 'blitzmaster2024', 'blitzmaster2024', 'I’m improving too, watch out!', '2024-12-17', '09:15'),
('chessking87', 'bishopbrian99', 'bishopbrian99', 'Great move on your part.', '2024-12-18', '10:25'),
('strategistgirl21', 'rookiejack56', 'strategistgirl21', 'Let’s have a rematch soon.', '2024-12-18', '11:30'),
('grandmastergeorge', 'queenjulia88', 'grandmastergeorge', 'Your moves are sharp, I’ll have to focus more.', '2024-12-19', '12:50'),
('veracheckmate74', 'lucycheck31', 'veracheckmate74', 'I’m getting better with my openings.', '2024-12-19', '13:00'),
('xaviernelson22', 'pamschess13', 'xaviernelson22', 'Great game, I’ll play again later.', '2024-12-20', '14:30'),
('gracemartinez45', 'umakingmove9', 'umakingmove9', 'Can’t wait for the next one!', '2024-12-20', '15:40'),
('isaacthoughts3', 'queenquinn7', 'isaacthoughts3', 'I’ll be on top next time!', '2024-12-21', '16:10'),
('fisherquinn72', 'danielmoves85', 'danielmoves85', 'You’ve improved a lot, nice moves.', '2024-12-21', '17:15'),
('ianchess49', 'yaraqueen111', 'ianchess49', 'It’s fun to challenge you!', '2024-12-22', '18:00'),
('rachelgreen22', 'mikedawson33', 'mikedawson33', 'That was an interesting game, well played.', '2024-12-22', '19:25'),
('liamadams57', 'zackperez10', 'zackperez10', 'Nice defense, I couldn’t break through.', '2024-12-23', '20:30'),
('hannahgamer92', 'tinaking25', 'tinaking25', 'I’m up for another round anytime.', '2024-12-23', '21:00'),
('frankvance81', 'kathywhite44', 'frankvance81', 'Good luck in your next game.', '2024-12-24', '22:00'),
('williamnewton10', 'frankgarcia20', 'frankgarcia20', 'I’m learning from every match.', '2024-12-24', '23:10'),
('wendychess88', 'oliverfoster11', 'wendychess88', 'You’re a tough opponent, I enjoy playing you.', '2024-12-25', '09:25'),
('evejones101', 'ericamoves23', 'evejones101', 'I’ll get you next time!', '2024-12-25', '10:30'),
('victorlee5', 'samharris36', 'victorlee5', 'We’ll do better next time!', '2024-12-26', '11:40'),
('ninaclark202', 'peterevans23', 'peterevans23', 'Great strategy, I couldn’t predict that.', '2024-12-26', '12:50'),
('xavieroconnor8', 'bobjohnson12', 'bobjohnson12', 'You’ve got a sharp eye for moves.', '2024-12-27', '13:35'),
('thomasjackson5', 'amyquinn24', 'thomasjackson5', 'I’ll need to adjust my strategy.', '2024-12-27', '14:45'),
('rapidmaster3000', 'alicesmith56', 'rapidmaster3000', 'I’ll keep practicing for the next match.', '2024-12-28', '15:50'),
('Enter Username', 'j', 'j', 'Great start! Let’s continue.', '2024-12-28', '16:00'),
('jamie', 'Jogilvy', 'jamie', 'Excited for the next challenge!', '2024-12-29', '17:10');

INSERT INTO turn VALUES
(1, 1, 'Pe4'), (1, 2, 'Pe5'), (1, 3, 'Nf3'), (1, 4, 'Nc6'), (1, 5, 'Bb5'),
(2, 1, 'Pd4'), (2, 2, 'Pd5'), (2, 3, 'Pc4'), (2, 4, 'Pe6'), (2, 5, 'Nc3'),
(3, 1, 'Pe4'), (3, 2, 'Pc5'), (3, 3, 'Nf3'), (3, 4, 'Nc6'), (3, 5, 'Bb5'),
(4, 1, 'Nf3'), (4, 2, 'Pd5'), (4, 3, 'Pg3'), (4, 4, 'Bg7'), (4, 5, 'Bg2'),
(5, 1, 'Pe4'), (5, 2, 'Pd6'), (5, 3, 'Pd4'), (5, 4, 'Nf6'), (5, 5, 'Nc3'),
(6, 1, 'Pd4'), (6, 2, 'Pe6'), (6, 3, 'Nf3'), (6, 4, 'Pd5'), (6, 5, 'Bf4'),
(7, 1, 'Pc4'), (7, 2, 'Pe6'), (7, 3, 'Nc3'), (7, 4, 'Nf6'), (7, 5, 'Nf3'),
(8, 1, 'Pe4'), (8, 2, 'Pe5'), (8, 3, 'Nf3'), (8, 4, 'Nc6'), (8, 5, 'Bc4'),
(10, 1, 'Pe4'), (10, 2, 'Pe5'), (10, 3, 'Nf3'), (10, 4, 'Nc6'), (10, 5, 'Bb5'),
(11, 1, 'Pe4'), (11, 2, 'Pd5'), (11, 3, 'Pxd5'), (11, 4, 'Nf6'), (11, 5, 'Nc3'),
(12, 1, 'Pe4'), (12, 2, 'Pe5'), (12, 3, 'Nf3'), (12, 4, 'Nc6'), (12, 5, 'Bc4'),
(13, 1, 'Pd4'), (13, 2, 'Pd5'), (13, 3, 'Nf3'), (13, 4, 'Pe6'), (13, 5, 'Bf4'),
(14, 1, 'Pc4'), (14, 2, 'Pe6'), (14, 3, 'Nf3'), (14, 4, 'Pd5'), (14, 5, 'Nc3'),
(15, 1, 'Pe4'), (15, 2, 'Pe5'), (15, 3, 'Nf3'), (15, 4, 'Nc6'), (15, 5, 'Bb5'),
(9, 1, 'Pd4'), (9, 2, 'Pd5'), (9, 3, 'Pc4'), (9, 4, 'Pe6'), (9, 5, 'Nc3'),
(9, 6, 'Nf6'), (9, 7, 'Bf4'), (9, 8, 'Bd6'), (9, 9, 'Bd6'), (9, 10, 'Nd6'),
(9, 11, 'Nf3'), (9, 12, 'Be7'),(9, 13, 'e3'), (9, 14, 'O-O'), (9, 15, 'Be2');

INSERT INTO head_to_head VALUES
('xavieroconnor8', 'bobjohnson12', '4-4-2'),
('thomasjackson5', 'amyquinn24', '6-3-5'),
('rapidmaster3000', 'alicesmith56', '10-5-2'),
('charliewilliams9', 'chessking87', '3-2-1'),
('blitzmaster2024', 'strategistgirl21', '8-4-3'),
('bishopbrian99', 'grandmastergeorge', '6-4-3'),
('rookiejack56', 'grandmastergeorge', '7-5-3'),
('queenjulia88', 'veracheckmate74', '9-6-2'),
('lucycheck31', 'xaviernelson22', '4-3-2'),
('pamschess13', 'gracemartinez45', '5-2-5'),
('umakingmove9', 'isaacthoughts3', '3-1-1'),
('kathywhite44', 'fisherquinn72', '4-3-2'),
('danielmoves85', 'ianchess49', '6-3-1'),
('yaraqueen111', 'rachelgreen22', '5-4-2'),
('mikedawson33', 'liamadams57', '4-3-3'),
('frankvance81', 'hannahgamer92', '2-5-2'),
('tinaking25', 'frankvance81', '7-2-6'),
('tinaking25', 'williamnewton10', '3-6-1'),
('yaraqueen111', 'wendychess88', '8-7-1'),
('oliverfoster11', 'evejones101', '4-4-2'),
('ericamoves23', 'victorlee5', '6-5-3'),
('samharris36', 'ninaclark202', '3-3-1'),
('peterevans23', 'xavieroconnor8', '7-6-1'),
('bobjohnson12', 'thomasjackson5', '2-1-2'),
('bobjohnson12', 'rapidmaster3000', '1-4-3'),
('jogilvy', 'jamie', '6-5-0'),
('veracheckmate74', 'charliewilliams9', '3-2-0'),
('jogilvy', 'blitzmaster2024', '5-3-1'),
('chessking87', 'bishopbrian99', '2-4-2'),
('veracheckmate74', 'rookiejack56', '7-4-1'),
('jogilvy', 'queenjulia88', '4-4-0'),
('veracheckmate74', 'lucycheck31', '5-3-4'),
('xaviernelson22', 'pamschess13', '6-4-2'),
('gracemartinez45', 'umakingmove9', '3-2-1'),
('isaacthoughts3', 'queenquinn7', '5-2-3'),
('fisherquinn72', 'yaraqueen111', '6-5-0'),
('ianchess49', 'yaraqueen111', '2-6-1'),
('rachelgreen22', 'mikedawson33', '3-3-0'),
('liamadams57', 'yaraqueen111', '4-4-1'),
('hannahgamer92', 'oliverfoster11', '2-5-0'),
('frankvance81', 'samharris36', '4-6-2'),
('williamnewton10', 'frankgarcia20', '5-4-1'),
('wendychess88', 'jogilvy', '3-7-2'),
('evejones101', 'victorlee5', '6-3-1'),
('victorlee5', 'samharris36', '8-2-1'),
('ninaclark202', 'peterevans23', '4-3-2'),
('jamie', 'rachelisbell7', '4-3-2'),
('charliewilliams9', 'jogilvy', '2-1-1'),
('blitzmaster2024', 'chessking87', '6-5-2'),
('bishopbrian99', 'strategistgirl21', '5-3-1'),
('umakingmove9', 'grandmastergeorge', '7-2-2'),
('umakingmove9', 'veracheckmate74', '8-4-3'),
('queenquinn7', 'xaviernelson22', '6-2-3'),
('mikedawson33', 'gracemartinez45', '4-5-1'),
('umakingmove9', 'fisherquinn72', '6-4-2'),
('queenquinn7', 'fisherquinn72', '3-2-2'),
('mikedawson33', 'ianchess49', '5-4-0'),
('frankgarcia20', 'rachelgreen22', '2-3-0'),
('ericamoves23', 'liamadams57', '4-5-2'),
('zackperez10', 'hannahgamer92', '3-1-2'),
('bobjohnson12', 'frankvance81', '6-4-1'),
('kathywhite44', 'williamnewton10', '4-4-0'),
('frankgarcia20', 'wendychess88', '5-6-1'),
('ninaclark202', 'evejones101', '2-5-0'),
('peterevans23', 'victorlee5', '4-5-1'),
('chessking87', 'ninaclark202', '2-6-1'),
('amyquinn24', 'xavieroconnor8', '5-2-3'),
('strategistgirl21', 'thomasjackson5', '3-4-0'),
('amyquinn24', 'rapidmaster3000', '2-3-2'),
('alicesmith56', 'jamie', '3-6-0'),
('rachelisbell7', 'charliewilliams9', '4-1-3'),
('chessking87', 'blitzmaster2024', '6-2-1'),
('chessking87', 'rookiejack56', '7-4-2'),
('strategistgirl21', 'rookiejack56', '3-2-1'),
('grandmastergeorge', 'queenjulia88', '4-3-0'),
('veracheckmate74', 'pamschess13', '3-4-1'),
('xaviernelson22', 'danielmoves85', '2-5-0'),
('gracemartinez45', 'tinaking25', '3-1-2'),
('strategistgirl21', 'queenquinn7', '4-3-1'),
('fisherquinn72', 'danielmoves85', '2-5-1'),
('ianchess49', 'zackperez10', '5-2-2'),
('rachelgreen22', 'kathywhite44', '6-3-0'),
('liamadams57', 'zackperez10', '5-4-1'),
('hannahgamer92', 'tinaking25', '4-5-0'),
('frankvance81', 'kathywhite44', '5-2-1'),
('williamnewton10', 'ericamoves23', '6-5-0'),
('wendychess88', 'oliverfoster11', '4-6-2'),
('evejones101', 'ericamoves23', '3-2-2');