-- Database: OnlineRPG
DROP SCHEMA IF EXISTS PROJECT_RGB;
CREATE SCHEMA PROJECT_RGB;
USE PROJECT_RGB;

-- Table: PlayerAccount
CREATE TABLE PlayerAccount (
    email VARCHAR(255),
    `name` VARCHAR(255) NOT NULL,
    `activeStatus` BOOL NOT NULL,
    CONSTRAINT pk_PlayerAccount_email PRIMARY KEY (email)
);

-- Table: Character
CREATE TABLE `Character` (
    characterID INT AUTO_INCREMENT,
    player VARCHAR(255) NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    CONSTRAINT pk_Character_characterID PRIMARY KEY (characterID),
    CONSTRAINT fk_Character_player FOREIGN KEY (player) REFERENCES PlayerAccount(email) 
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT uq_Character_fullName UNIQUE (firstName, lastName)
);

-- Table: Job
CREATE TABLE Job (
    jobName VARCHAR(50),
    CONSTRAINT pk_Job_jobName PRIMARY KEY (jobName)
);

-- Table: CharacterJob
CREATE TABLE CharacterJob (
    `character` INT,
    job VARCHAR(50),
	`level` INT NOT NULL,
    experiencePoints INT NOT NULL,
    CONSTRAINT pk_Characterjob_character_job PRIMARY KEY (`character`, job),
    CONSTRAINT fk_Characterjob_character FOREIGN KEY (`character`) REFERENCES `Character`(characterID) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_Characterjob_job FOREIGN KEY (job) REFERENCES Job(jobName) 
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Table: Attribute
CREATE TABLE Attribute (
    `name` VARCHAR(50),
    CONSTRAINT pk_Attribute_name PRIMARY KEY (`name`)
);

-- Table: CharacterAttributes
CREATE TABLE CharacterAttributes (
    `character` INT,
    attribute VARCHAR(50),
    `value` INT NOT NULL,
    CONSTRAINT pk_Characterattributes_character_attribute PRIMARY KEY (`character`, attribute),
    CONSTRAINT fk_Characterattributes_character FOREIGN KEY (`character`) REFERENCES `Character`(characterID) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_Characterattributes_attribute FOREIGN KEY (attribute) REFERENCES Attribute(`name`) 
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Table: Currency
CREATE TABLE Currency (
    `name` VARCHAR(50),
    cap INT NOT NULL,
    weeklyCap INT NOT NULL,
    CONSTRAINT pk_Currency_name PRIMARY KEY (`name`)
);

-- Table: Money
CREATE TABLE Money (
    `character` INT,
    currency VARCHAR(50),
    totalAmount INT NOT NULL,
    weeklyAmount INT NOT NULL,
    CONSTRAINT pk_Money_character_currency PRIMARY KEY (`character`, currency),
    CONSTRAINT fk_Money_character FOREIGN KEY (`character`) REFERENCES `Character`(characterID) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_Money_currency FOREIGN KEY (currency) REFERENCES Currency(`name`) 
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Table: Item
CREATE TABLE Item (
	itemID INT AUTO_INCREMENT,
    itemName VARCHAR(225),
    maxStackSize INT NOT NULL,
    vendorPrice INT,
    itemLevel INT NOT NULL,
    -- Updated after grading: Modified to define itemLevel in Item table
    CONSTRAINT pk_Item_itemID PRIMARY KEY (itemID)
);

-- Table: Equipment (Superclass)
CREATE TABLE Equipment (
    equipmentID INT AUTO_INCREMENT,
    slotName ENUM('main_hand', 'head', 'off_hand', 'body', 'earring', 'hands', 'wrist', 'legs', 'ring', 'feet') NOT NULL,
    requiredLevel INT NOT NULL,
    CONSTRAINT pk_Equipment_equipmentID PRIMARY KEY (equipmentID),
	CONSTRAINT fk_Equipment_equipmentID FOREIGN KEY (equipmentID) REFERENCES Item(itemID) 
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Table: Gear (Subclass of Equipment)
CREATE TABLE Gear (
    gearID INT AUTO_INCREMENT,
    defenseRating INT NOT NULL,
    magicDefenseRating INT NOT NULL,
    CONSTRAINT pk_Gear_gearID PRIMARY KEY (gearID),
    CONSTRAINT fk_Gear_gearID FOREIGN KEY (gearID) REFERENCES Equipment(equipmentID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Table: Weapon (Subclass of Equipment)
CREATE TABLE Weapon (
    weaponID INT AUTO_INCREMENT,
    damageDone INT NOT NULL,
    autoAttack INT NOT NULL,
    attackDelay INT NOT NULL,
    CONSTRAINT pk_Weapon_weaponID PRIMARY KEY (weaponID),
    CONSTRAINT fk_Weapon_weaponID FOREIGN KEY (weaponID) REFERENCES Equipment(equipmentID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Table: Consumable
CREATE TABLE Consumable (
    consumableID INT AUTO_INCREMENT,
    `description` TEXT NOT NULL,
    CONSTRAINT pk_Consumable_consumableID PRIMARY KEY (consumableID),
    CONSTRAINT fk_Consumable_consumableID FOREIGN KEY (consumableID) REFERENCES Item(itemID) 
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Table: ConsumableBonus
CREATE TABLE ConsumableBonus (
    itemID INT,
    attribute VARCHAR(50),
    percentage DECIMAL(5, 2) NOT NULL,
    cap INT NOT NULL,
    CONSTRAINT pk_ConsumableBonus_itemID_attribute PRIMARY KEY (itemID, attribute),
    CONSTRAINT fk_ConsumableBonus_itemID FOREIGN KEY (itemID) REFERENCES Consumable(consumableID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_ConsumableBonus_attribute FOREIGN KEY (attribute) REFERENCES Attribute(`name`) 
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Table: EquipmentBonus
CREATE TABLE EquipmentBonus (
    gearID INT,
    attribute VARCHAR(50),
    amount INT NOT NULL,
    CONSTRAINT pk_GearBonus_gearID_attribute PRIMARY KEY (gearID, attribute),
    CONSTRAINT fk_GearBonus_gearID FOREIGN KEY (gearID) REFERENCES Equipment(equipmentID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_GearBonus_attribute FOREIGN KEY (attribute) REFERENCES Attribute(`name`) 
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Table: ItemJob
CREATE TABLE ItemJob (
    itemID INT,
    job VARCHAR(50),
    CONSTRAINT pk_ItemJob_itemID_job PRIMARY KEY (itemID, job),
    CONSTRAINT fk_ItemJob_itemID FOREIGN KEY (itemID) REFERENCES Equipment(equipmentID) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_ItemJob_job FOREIGN KEY (job) REFERENCES Job(jobName) 
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Table: EquippedItem
CREATE TABLE EquippedItem (
    `character` INT,
    slotName ENUM('main_hand', 'head', 'off_hand', 'body', 'earring', 'hands', 'wrist', 'legs', 'ring', 'feet') NOT NULL,
    itemID INT UNIQUE,
    CONSTRAINT pk_EquippedItem_character_slotName PRIMARY KEY (`character`, slotName),
    CONSTRAINT fk_EquippedItem_character FOREIGN KEY (`character`) REFERENCES `Character`(characterID) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_EquippedItem_itemID FOREIGN KEY (itemID) REFERENCES Equipment(equipmentID) 
        ON DELETE CASCADE ON UPDATE CASCADE
	-- Updated after grading: Modified to get the foreign key from the equipment table
);

-- Table: Inventory
CREATE TABLE Inventory (
    `character` INT,
    locationIndex INT,
    item INT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT pk_Inventory_character_locationIndex PRIMARY KEY (`character`, locationIndex),
    CONSTRAINT fk_Inventory_character FOREIGN KEY (`character`) REFERENCES `Character`(characterID) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_Inventory_item FOREIGN KEY (item) REFERENCES Item(itemID) 
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE MoneyTransaction (
    transactionId INT AUTO_INCREMENT PRIMARY KEY,
    `character` INT NOT NULL,
    currency VARCHAR(255) NOT NULL,
    amount INT NOT NULL,
    previousAmount INT NOT NULL,
    newAmount INT NOT NULL,
    transactionType VARCHAR(50) NOT NULL,
    reason VARCHAR(255),
    transactionTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`character`) REFERENCES `Character`(characterID),
    FOREIGN KEY (currency) REFERENCES Currency(name)
);

CREATE TABLE JobLevelHistory (
    historyId INT AUTO_INCREMENT PRIMARY KEY,
    `character` INT NOT NULL,
    job VARCHAR(50) NOT NULL,
    oldLevel INT,            -- null when the character get this job at the first time
    newLevel INT NOT NULL,
    oldExp INT,             -- null when the character get this job at the first time
    newExp INT NOT NULL,
    changeTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    changeType ENUM('LEVEL_UP', 'EXP_GAIN', 'JOB_UNLOCK') NOT NULL,
    reason VARCHAR(255),
    FOREIGN KEY (`character`) REFERENCES `Character`(characterID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (job) REFERENCES Job(jobName) ON DELETE CASCADE ON UPDATE CASCADE
);
-- Test case
-- Populate PlayerAccount
INSERT INTO PlayerAccount (email, `name`, `activeStatus`) VALUES
('jiayiliu@gmail.com', 'JiayiLiu', TRUE),
('richengyang@gmail.com', 'RichengYang', TRUE),
('yezhang@gmail.com', 'YeZhang', FALSE),
('meiyiguang@gmail.com', 'MeiyiGuang', TRUE),
('xukunzhang@gmail.com', 'XukunZhang', TRUE),
('peterparker@gmail.com', 'PeterParker', TRUE),
('tonystark@gmail.com', 'TonyStark', TRUE),
('brucewayne@gmail.com', 'BruceWayne', FALSE),
('clarkkent@gmail.com', 'ClarkKent', TRUE),
('dianaprince@gmail.com', 'DianaPrince', TRUE);

-- Populate Character
INSERT INTO `Character` (player, firstName, lastName) VALUES
('jiayiliu@gmail.com', 'Aragorn', 'Elessar'),
('richengyang@gmail.com', 'Legolas', 'Greenleaf'),
('yezhang@gmail.com', 'Gandalf', 'Grey'),
('meiyiguang@gmail.com', 'Frodo', 'Baggins'),
('xukunzhang@gmail.com', 'Gimli', 'Gloin'),
('peterparker@gmail.com', 'Spiderman', 'Parker'),
('tonystark@gmail.com', 'Ironman', 'Stark'),
('brucewayne@gmail.com', 'Batman', 'Wayne'),
('clarkkent@gmail.com', 'Superman', 'Kent'),
('dianaprince@gmail.com', 'Wonderwoman', 'Prince');

-- Populate Job
INSERT INTO Job (jobName) VALUES
('Warrior'),
('Healer'),
('Ranger'),
('Paladin'),
('Strength'),
('Assassin'),
('Priest'),
('Knight'),
('Sorcerer'),
('Archer'),
('Druid'),
('Berserker'),
('Necromancer'),
('Shaman'),
('Thief'),
('Bard'),
('Tank'),
('Summoner'),
('Alchemist'),
('Mage');

-- Populate CharacterJob
INSERT INTO CharacterJob (`character`, job, `level`, experiencePoints) VALUES
(1, 'Warrior', 15, 1450),
(1, 'Paladin', 1, 80),
(2, 'Ranger', 45, 4470),
(3, 'Mage', 60, 5910),
(4, 'Healer', 35, 3440),
(5, 'Assassin', 25, 2200),
(6, 'Tank', 30, 3100),
(7, 'Summoner', 20, 1900),
(8, 'Bard', 18, 1700),
(9, 'Alchemist', 22, 2100);

-- Populate Attribute
INSERT INTO Attribute (`name`) VALUES
('Strength'),
('Intelligence'),
('Dexterity'),
('Defense'),
('Vitality'),
('Spirit'),
('Luck'),
('HP Regeneration'),
('MP Regeneration'),
('Wisdom'),
('Critical Hit Rate'),
('Debuff Immunity'),
('Agility'),
('HP'),
('Charisma'),
('Perception'),
('Happiness');

-- Populate CharacterAttributes
INSERT INTO CharacterAttributes (`character`, attribute, `value`) VALUES
(1, 'Strength', 50),
(1, 'Vitality', 40),
(2, 'Dexterity', 45),
(3, 'Intelligence', 55),
(4, 'Spirit', 40),
(5, 'Agility', 60),
(6, 'Charisma', 50),
(7, 'Wisdom', 55),
(8, 'Luck', 45),
(9, 'Perception', 40);

-- Populate Currency
INSERT INTO Currency (`name`, cap, weeklyCap) VALUES
('Gold', 1000000, 100000),
('Silver', 500000, 50000),
('Bronze', 100000, 10000),
('Platinum', 2000000, 200000),
('Crystal', 50000, 5000),
('Emerald', 300000, 30000),
('Ruby', 400000, 40000),
('Sapphire', 500000, 50000),
('Diamond', 600000, 60000),
('Pearl', 700000, 70000);

-- Populate Money
INSERT INTO Money (`character`, currency, totalAmount, weeklyAmount) VALUES
(1, 'Gold', 50000, 5000),
(1, 'Silver', 25000, 2500),
(2, 'Gold', 75000, 7500),
(3, 'Platinum', 100000, 10000),
(4, 'Crystal', 2500, 250),
(5, 'Emerald', 30000, 3000),
(6, 'Ruby', 40000, 4000),
(7, 'Sapphire', 50000, 5000),
(8, 'Diamond', 60000, 6000),
(9, 'Pearl', 70000, 7000);

-- Populate Item (base items)
INSERT INTO Item (itemName, maxStackSize, vendorPrice, itemLevel) VALUES
('Excalibur', 1, 10000, 50),
('Dragon Plate', 1, 8000, 45),
('Mystic Staff', 1, 9000, 48),
('Shadow Bow', 1, 7500, 43),
('Crystal Shield', 1, 6000, 40),
('Phoenix Blade', 1, 11000, 55),
('Golden Helm', 1, 7000, 42),
('Titanic Armor', 1, 12000, 60),
('Vampire Fang', 1, 8500, 47),
('Thunderstrike Mace', 1, 9500, 50),
('Silver Cloak', 1, 5000, 38),
('Dragon Fang', 1, 8000, 45),
('Storm Bow', 1, 9000, 48),
('Inferno Sword', 1, 10500, 52),
('Emerald Shield', 1, 6500, 41);

-- Populate Equipment
INSERT INTO Equipment (equipmentID, slotName, requiredLevel) VALUES
(1, 'main_hand', 45),
(2, 'body', 40),
(3, 'main_hand', 43),
(4, 'main_hand', 38),
(5, 'off_hand', 35),
(6, 'head', 30),
(7, 'legs', 32),
(8, 'feet', 28),
(9, 'hands', 34),
(10, 'ring', 20),
(11, 'ring', 22),
(15, 'main_hand', 50);

-- Populate Gear
INSERT INTO Gear (gearID, defenseRating, magicDefenseRating) VALUES
(1, 100, 80),
(2, 100, 80),
(3, 100, 80),
(4, 100, 80),
(5, 100, 80),
(6, 100, 80),
(7, 100, 80),
(8, 100, 80),
(9, 100, 80),
(10, 75, 60);

-- Populate Weapon
INSERT INTO Weapon (weaponID, damageDone, autoAttack, attackDelay) VALUES
(1, 200, 100, 2),
(2, 180, 90, 3),
(4, 150, 75, 2),
(5, 150, 75, 2),
(6, 150, 75, 2),
(7, 110, 20, 1),
(8, 98, 20,  4),
(9, 110, 20, 1),
(11, 98, 20,  4),
(15, 110, 20, 1);
-- Additional Items for Consumables
INSERT INTO Item (itemName, maxStackSize, vendorPrice, itemLevel) VALUES
('Health Potion', 99, 100, 1),
('Mana Potion', 99, 100, 1),
('Strength Elixir', 20, 500, 30),
('Intelligence Potion', 20, 500, 30),
('Speed Potion', 20, 500, 30),
('Healing Salve', 99, 150, 2),
('Mystic Brew', 99, 200, 5),
('Agility Elixir', 20, 600, 35),
('Wisdom Potion', 20, 600, 35),
('Vitality Brew', 20, 550, 33),
('Mana Elixir', 20, 700, 40),
('Fortune Tonic', 99, 250, 10),
('Endurance Potion', 20, 450, 25),
('Fire Resistance Potion', 20, 650, 45),
('Ice Resistance Brew', 20, 650, 48);

-- Populate Consumable
INSERT INTO Consumable (consumableID, `description`) VALUES
(6, 'Restores 1000 HP instantly'),
(7, 'Restores 1000 MP instantly'),
(8, 'Increases Strength by 30% for 30 minutes'),
(9, 'Increases Intelligence by 30% for 30 minutes'),
(10, 'Increases movement speed by 40% for 30 minutes'),
(11, 'Restores 500 HP instantly'),
(12, 'Restores 500 MP instantly'),
(13, 'Increases Defense by 20% for 20 minutes'),
(14, 'Increases Agility by 25% for 25 minutes'),
(15, 'Boosts Luck by 15% for 30 minutes'),
(1, 'Regenerates 100 HP per second for 10 seconds'),
(2, 'Regenerates 100 MP per second for 10 seconds'),
(3, 'Increases Critical Hit Rate by 20% for 30 minutes'),
(4, 'Provides immunity to debuffs for 1 hour'),
(5, 'Reduces cooldown times by 10% for 15 minutes');

-- Populate ConsumableBonus
INSERT INTO ConsumableBonus (itemID, attribute, percentage, cap) VALUES
(8, 'Strength', 30.00, 300),
(9, 'Intelligence', 30.00, 300),
(1, 'Dexterity', 40.00, 400),
(3, 'Defense', 20.00, 200),
(4, 'Agility', 25.00, 250),
(5, 'Luck', 15.00, 150),
(6, 'HP Regeneration', 100.00, 1000),
(7, 'MP Regeneration', 100.00, 1000),
(2, 'Critical Hit Rate', 20.00, 200),
(10, 'Debuff Immunity', 0.00, 111);
 

-- Populate EquipmentBonus
INSERT INTO EquipmentBonus (gearID, attribute, amount) VALUES
(2, 'Vitality', 50),
(2, 'Spirit', 30),
(5, 'Strength', 25),
(5, 'Vitality', 20),
(2, 'Strength', 35),
(7, 'Luck', 15),
(7, 'Critical Hit Rate', 10),
(8, 'Defense', 50),
(4, 'Intelligence', 30),
(8, 'HP', 200);

-- Populate ItemJob
INSERT INTO ItemJob (itemID, job) VALUES
(1, 'Warrior'),
(1, 'Paladin'),
(2, 'Warrior'),
(3, 'Mage'),
(4, 'Ranger'),
(5, 'Assassin'),
(6, 'Priest'),
(7, 'Knight'),
(8, 'Sorcerer'),
(9, 'Archer'),
(10, 'Druid'),
(11, 'Berserker'),
(11, 'Necromancer'),
(11, 'Shaman'),
(11, 'Thief');
-- Populate EquippedItem
INSERT INTO EquippedItem (`character`, slotName, itemID) VALUES
(1, 'main_hand', 1),
(1, 'body', 2),
(2, 'main_hand', 4),
(3, 'main_hand', 3),
(4, 'off_hand', 5),
(5, 'main_hand', 6),
(5, 'earring', 7),
(6, 'main_hand', 8),
(7, 'off_hand', 9),
(7, 'body', 10);

-- Populate Inventory
INSERT INTO Inventory (`character`, locationIndex, item, quantity) VALUES
(1, 0, 6, 10),
(1, 1, 7, 10),
(2, 0, 8, 5),
(3, 0, 9, 5),
(4, 0, 10, 5),
(5, 0, 11, 15),
(5, 1, 12, 20),
(6, 0, 13, 8),
(7, 0, 14, 12),
(8, 0, 15, 7);
-- Populate JobLevelHistory
INSERT INTO JobLevelHistory (`character`, job, oldLevel, newLevel, oldExp, newExp, changeType, reason)
VALUES 
(1, 'Warrior', 14, 15, 1300, 1400, 'LEVEL_UP', 'Defeated boss'),
(1, 'Warrior', 15, 15, 1400, 1450, 'EXP_GAIN', 'Completed minor quest'),
(2, 'Ranger', 44, 45, 4300, 4400, 'LEVEL_UP', 'Completed raid'),
(2, 'Ranger', 45, 45, 4400, 4470, 'EXP_GAIN', 'Finished a challenging hunt'),
(3, 'Mage', 59, 60, 5800, 5900, 'LEVEL_UP', 'Cleared dungeon'),
(3, 'Mage', 60, 60, 5900, 5910, 'EXP_GAIN', 'Found hidden treasure'),
(4, 'Healer', 34, 35, 3300, 3400, 'LEVEL_UP', 'Quest completed'),
(4, 'Healer', 35, 35, 3400, 3440, 'EXP_GAIN', 'Healed a wounded party member'),
(5, 'Assassin', 24, 25, 2100, 2200, 'LEVEL_UP', 'Defeated enemy'),
(6, 'Tank', 29, 30, 3000, 3100, 'LEVEL_UP', 'Defended stronghold'),
(7, 'Summoner', 19, 20, 1800, 1900, 'LEVEL_UP', 'Summoned a rare beast'),
(8, 'Bard', 17, 18, 1600, 1700, 'LEVEL_UP', 'Entertained crowd successfully'),
(9, 'Alchemist', 21, 22, 2000, 2100, 'LEVEL_UP', 'Created a powerful potion'),
(9, 'Alchemist', 22, 22, 2100, 2200, 'EXP_GAIN', 'Experimented with new formulas');


-- Populate MoneyTransactionHistory
INSERT INTO MoneyTransaction (`character`, currency, amount, previousAmount, newAmount, transactionType, reason) 
VALUES 
(1, 'Crystal', 100, 2400, 2500, 'INCREASE', 'Daily bonus'),
(1, 'Crystal', -50, 2500, 2450, 'DECREASE', 'Item purchase'),
(1, 'Crystal', 0, 2450, 2450, 'NO_CHANGE', 'Account check'),
(2, 'Gold', 10000, 40000, 50000, 'INCREASE', 'Quest reward'),
(2, 'Gold', -5000, 45000, 40000, 'DECREASE', 'Equipment purchase');

-- Queries to view data
SELECT * FROM PlayerAccount;
SELECT * FROM `Character`;
SELECT * FROM Job;

SELECT c.firstName, c.lastName, cj.job, cj.level, cj.experiencePoints 
FROM CharacterJob cj
JOIN `Character` c ON c.characterID = cj.character;

SELECT * FROM Attribute;

SELECT c.firstName, c.lastName, ca.attribute, ca.value 
FROM CharacterAttributes ca
JOIN `Character` c ON c.characterID = ca.character;

SELECT * FROM Currency;

SELECT c.firstName, c.lastName, m.currency, m.totalAmount, m.weeklyAmount 
FROM Money m
JOIN `Character` c ON c.characterID = m.character;

SELECT * FROM Item;

SELECT i.itemName, e.slotName, e.requiredLevel 
FROM Equipment e
JOIN Item i ON i.itemID = e.equipmentID;

SELECT i.itemName, g.defenseRating, g.magicDefenseRating 
FROM Gear g
JOIN Item i ON i.itemID = g.gearID;

SELECT i.itemName, w.damageDone, w.autoAttack, w.attackDelay 
FROM Weapon w
JOIN Item i ON i.itemID = w.weaponID;

SELECT i.itemName, c.description 
FROM Consumable c
JOIN Item i ON i.itemID = c.consumableID;

SELECT i.itemName, cb.attribute, cb.percentage, cb.cap 
FROM ConsumableBonus cb
JOIN Item i ON i.itemID = cb.itemID;

SELECT i.itemName, gb.attribute, gb.amount 
FROM EquipmentBonus gb
JOIN Item i ON i.itemID = gb.gearID;

SELECT i.itemName, ij.job 
FROM ItemJob ij
JOIN Item i ON i.itemID = ij.itemID;

SELECT c.firstName, c.lastName, ei.slotName, i.itemName 
FROM EquippedItem ei
JOIN `Character` c ON c.characterID = ei.character
JOIN Item i ON i.itemID = ei.itemID;

SELECT c.firstName, c.lastName, i.itemName, inv.quantity 
FROM Inventory inv
JOIN `Character` c ON c.characterID = inv.character
JOIN Item i ON i.itemID = inv.item;
