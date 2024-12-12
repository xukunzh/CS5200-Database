package tools;

import java.sql.SQLException;
import java.util.List;

import dal.*;
import model.*;

public class Inserter {
	public static void main(String[] args) throws SQLException {
		
		// DAO instances
		ItemDao itemDao = ItemDao.getInstance();
		EquipmentDao equipmentDao = EquipmentDao.getInstance();
		WeaponDao weaponDao = WeaponDao.getInstance();
		GearDao gearDao = GearDao.getInstance();
		ConsumableDao consumableDao = ConsumableDao.getInstance();
		PlayerAccountDao playerAccountDao = PlayerAccountDao.getInstance();
		CharacterClassDao characterClassDao = CharacterClassDao.getInstance();
		CharacterJobDao characterJobDao = CharacterJobDao.getInstance();
		JobDao jobDao = JobDao.getInstance();
		AttributeDao attributeDao = AttributeDao.getInstance();
		CharacterAttributeDao characterAttributeDao = CharacterAttributeDao.getInstance();
        ConsumableBonusDao consumableBonusDao = ConsumableBonusDao.getInstance();
        EquipmentBonusDao equipmentBonusDao = EquipmentBonusDao.getInstance();
		
		Item item1 = new Item("it1", 1, 100, 99);
		item1 = itemDao.create(item1);
		Item item2 = new Item("it2", 2, 200, 98);
		item2 = itemDao.create(item2);
		Item item3 = new Item("it3", 3, 300, 97);
		item3 = itemDao.create(item3);
		
		Equipment equipment1 = new Equipment("equip1", 1, 100, 99, Equipment.EquipmentSlotType.main_hand, 10);
		equipment1 = equipmentDao.create(equipment1);
		Equipment equipment2 = new Equipment("equip2", 2, 200, 98, Equipment.EquipmentSlotType.legs, 20);
		equipment2 = equipmentDao.create(equipment2);
		Equipment equipment3 = new Equipment("equip3", 3, 3100, 97, Equipment.EquipmentSlotType.legs, 30);
		equipment3 = equipmentDao.create(equipment3);
		
		Weapon weapon1 = new Weapon("weap1", 1, 100, 99, 10, 111, 11, 1);
		weapon1 = weaponDao.create(weapon1);
		Weapon weapon2 = new Weapon("weap2", 2, 200, 98, 20, 222, 22, 2);
		weapon2 = weaponDao.create(weapon2);
		
		Gear gear1 = new Gear("gear1", 1, 100, 99, Equipment.EquipmentSlotType.earring, 10, 11, 111);
		gear1 = gearDao.create(gear1);
		Gear gear2 = new Gear("gear2", 2, 200, 98, Equipment.EquipmentSlotType.body, 20, 22, 222);
		gear2 = gearDao.create(gear2);
		
		Consumable consumable1 = new Consumable("cons1", 1, 100, 99, "An Apple");
		consumable1 = consumableDao.create(consumable1);
		Consumable consumable2 = new Consumable("cons2", 2, 200, 98, "An Orange");
		consumable2 = consumableDao.create(consumable2);
		
		// READ
		Item it1 = itemDao.getItemByItemID(1);
		System.out.format("Reading Item: itemID:%s | itemName:%s | maxStackSize:%s | vendorPrice:%s | itemLevel:%s \n", 
				it1.getItemID(), it1.getItemName(), it1.getMaxStackSize(), it1.getVendorPrice(), it1.getItemLevel());
		
		Equipment equip1 = equipmentDao.getEquipmentByEquipmentID(4);
		System.out.format("Reading Equipment: equipmentID:%s | equipmentName:%s | maxStackSize:%s | vendorPrice:%s | itemLevel:%s | slotName:%s | requiredLevel:%s \n", 
				equip1.getItemID(), equip1.getItemName(), equip1.getMaxStackSize(), equip1.getVendorPrice(), equip1.getItemLevel(), equip1.getSlotName().name(), equip1.getRequiredLevel());
		List<Equipment> equipments = equipmentDao.getAllEquipmentBySlot(Equipment.EquipmentSlotType.legs);
		for (Equipment e : equipments) {
			System.out.format("Looping Equipments which slot is legs: equipmentID:%s | equipmentName:%s | maxStackSize:%s | vendorPrice:%s | itemLevel:%s | slotName:%s | requiredLevel:%s \n", 
					e.getItemID(), e.getItemName(), e.getMaxStackSize(), e.getVendorPrice(), e.getItemLevel(), e.getSlotName().name(), e.getRequiredLevel());		}
		
		Weapon weap1 = weaponDao.getWeaponByWeaponID(7);
		System.out.format("Reading Weapon: weaponID:%s | weaponName:%s | maxStackSize:%s | vendorPrice:%s | itemLevel:%s | slotName:%s | requiredLevel:%s | damageDone:%s | autoAttack:%s | attackDelay:%s \n", 
				weap1.getItemID(), weap1.getItemName(), weap1.getMaxStackSize(), weap1.getVendorPrice(), weap1.getItemLevel(), weap1.getSlotName().name(), weap1.getRequiredLevel(), weap1.getDamageDone(), weap1.getAutoAttack(), weap1.getAttackDelay());
		
		Gear ge1 = gearDao.getGearByGearID(9);
		System.out.format("Reading Gear: gearID:%s | gearName:%s | maxStackSize:%s | vendorPrice:%s | itemLevel:%s | slotName:%s | requiredLevel:%s | defenseRating:%s | magicDefenseRating:%s \n", 
				ge1.getItemID(), ge1.getItemName(), ge1.getMaxStackSize(), ge1.getVendorPrice(), ge1.getItemLevel(), ge1.getSlotName().name(), ge1.getRequiredLevel(), ge1.getDefenseRating(), ge1.getMagicDefenseRating());
		
		Consumable cons1 = consumableDao.getConsumableByConsumableID(11);
		System.out.format("Reading Consumable: consumableID:%s | consuambleName:%s | maxStackSize:%s | vendorPrice:%s | itemLevel:%s | description:%s \n", 
				cons1.getItemID(), cons1.getItemName(), cons1.getMaxStackSize(), cons1.getVendorPrice(), cons1.getItemLevel(), cons1.getDescription());
		
		// Update
		System.out.format("Old Consumable: consumableID:%s | consuambleName:%s | maxStackSize:%s | vendorPrice:%s | itemLevel:%s | description:%s \n", 
				consumable1.getItemID(), consumable1.getItemName(), consumable1.getMaxStackSize(), consumable1.getVendorPrice(), consumable1.getItemLevel(), consumable1.getDescription());
		consumable1 = consumableDao.updateDescription(consumable1, "A Banana");
		System.out.format("New Consumable: consumableID:%s | consuambleName:%s | maxStackSize:%s | vendorPrice:%s | itemLevel:%s | description:%s \n", 
				consumable1.getItemID(), consumable1.getItemName(), consumable1.getMaxStackSize(), consumable1.getVendorPrice(), consumable1.getItemLevel(), consumable1.getDescription());
		
		// Delete
		List<Item> items = itemDao.getAllItems();
        System.out.println("Users in the Database before item2 deletion:");
        for (Item it : items) {
    		System.out.format("itemID:%s | itemName:%s | maxStackSize:%s | vendorPrice:%s | itemLevel:%s \n", 
    				it.getItemID(), it.getItemName(), it.getMaxStackSize(), it.getVendorPrice(), it.getItemLevel());
        }
        System.out.println();
        item2 = itemDao.delete(item2);
		System.out.println("Items in the Database after item2 deletion:");
		items = itemDao.getAllItems();
        for (Item it : items) {
    		System.out.format("itemID:%s | itemName:%s | maxStackSize:%s | vendorPrice:%s | itemLevel:%s \n", 
    				it.getItemID(), it.getItemName(), it.getMaxStackSize(), it.getVendorPrice(), it.getItemLevel());
        }
        System.out.println();
		
		
		PlayerAccount newPlayer = new PlayerAccount("testuser@example.com", "Test User", true);
		PlayerAccount createdPlayer = playerAccountDao.create(newPlayer);
		System.out.println("Player created: " + createdPlayer.getEmail());
		
		PlayerAccount retrievedPlayer = playerAccountDao.getPlayerAccountByEmail("testuser@example.com");
        if (retrievedPlayer != null) {
            System.out.println("Retrieved Player: " + retrievedPlayer.getName() + ", Active: " + retrievedPlayer.getActiveStatus());
        } else {
            System.out.println("No player found with the given email.");
        }
        
        CharacterClass characterClass = new CharacterClass(newPlayer, "John", "Doe");
        CharacterClass createdCharacter = characterClassDao.create(characterClass);
        System.out.println("Character created with ID: " + createdCharacter.getCharacterID());
        
        
        int existingCharacterID = 1; 
        int nonExistingCharacterID = 9999;
        
        try {
            CharacterClass character = characterClassDao.getCharacterByID(existingCharacterID);
            if (character != null) {
                System.out.println("Test 1 Passed: Character found. " + "ID: " + character.getCharacterID() );
            } else {
                System.out.println("Test 1 Failed: Character not found.");
            }
        } catch (SQLException e) {
            System.out.println("Test 1 Failed with exception: " + e.getMessage());
        }
        
        try {
            CharacterClass character = characterClassDao.getCharacterByID(nonExistingCharacterID);
            if (character == null) {
                System.out.println("Test 2 Passed: No character found for non-existing ID.");
            } else {
                System.out.println("Test 2 Failed: Unexpected character found.");
            }
        } catch (SQLException e) {
            System.out.println("Test 2 Failed with exception: " + e.getMessage());
        }
        
        Job testJob = jobDao.create(new Job("warrior1"));
        CharacterClass testCharacter = characterClassDao.getCharacterByID(1);
        CharacterJob characterJob = new CharacterJob(testCharacter, testJob, 5, 1200);
        
        try {
            CharacterJob createdCharacterJob = characterJobDao.create(characterJob);
            if (createdCharacterJob != null) {
                System.out.println("Test Passed: CharacterJob successfully created. With Character ID: " 
            + createdCharacterJob.getCharacterClass().getCharacterID() + "; Job Name: " +  createdCharacterJob.getJob().getJobName());
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("CharacterJob with the same character and job already exists")) {
                System.out.println("Test Passed: Duplicate entry detected as expected.");
            } else {
                System.out.println("Test Failed with unexpected exception: " + e.getMessage());
            }
        }
        
        int testCharacterID = 1;
        String testJobName = "warrior";
        
        CharacterJob testCharacterJob = characterJobDao.getBlogPostByCharacterAndJob(testCharacterID, testJobName);
        
        if (testCharacterJob != null) {
            System.out.println("Character Job found:");
            System.out.println("Character ID: " + characterJob.getCharacterClass().getCharacterID());
            System.out.println("Job Name: " + characterJob.getJob().getJobName());
            System.out.println("Level: " + characterJob.getLevel());
            System.out.println("Experience Points: " + characterJob.getExperiencePoints());
        } else {
            System.out.println("No Character Job found for the provided character ID and job name.");
        }
        
        // INSERT objects for Attribute.
        Attribute strength = new Attribute("Strength1" );
        Attribute agility = new Attribute("Agility1" );
        strength = attributeDao.create(strength);
        agility = attributeDao.create(agility);

        // INSERT objects for CharacterAttribute.
        PlayerAccount player1 = new PlayerAccount("testplayer@example.com", "Jake Smith", true);
        player1 = playerAccountDao.create(player1);

        PlayerAccount player2 = new PlayerAccount("testplayer2@example.com", "Coe Doe", true);
        player2 = playerAccountDao.create(player2);

        
     
        // INSERT objects for CharacterClass
        CharacterClass character1 = new CharacterClass(player1, "Joe", "Smith");
        character1 = characterClassDao.create(character1);  

        CharacterClass character2 = new CharacterClass(player2, "Joe", "Doe");
        character2 = characterClassDao.create(character2);  

        // Now insert CharacterAttributes
        CharacterAttributes charAttr = new CharacterAttributes(character1, strength, "10");
        charAttr = characterAttributeDao.create(charAttr);

        CharacterAttributes charAttr2 = new CharacterAttributes(character2, agility, "8");
        charAttr2 = characterAttributeDao.create(charAttr2);


     // INSERT objects for Consumables
        
        Consumable healthPotion = new Consumable(201, "Health Potion", 10, 50, 1, "Restores 50 health points.");
        healthPotion = consumableDao.create(healthPotion);  // Insert into Consumable table

        Consumable manaPotion = new Consumable(202, "Mana Potion", 10, 60, 1, "Restores 50 mana points.");
        manaPotion = consumableDao.create(manaPotion);  // Insert into Consumable table

    
        ConsumableBonus potionBonus = new ConsumableBonus(50, 0.2, healthPotion, strength);
        potionBonus = consumableBonusDao.create(potionBonus);

        ConsumableBonus elixirBonus = new ConsumableBonus(30, 0.15, manaPotion, agility);
        elixirBonus = consumableBonusDao.create(elixirBonus);

     // INSERT objects for Equipment (should be done before EquipmentBonus)
        Equipment equipment4 = new Equipment("equip1", 1, 100, 99, Equipment.EquipmentSlotType.main_hand, 10);
		equipment1 = equipmentDao.create(equipment4);
		Equipment equipment5 = new Equipment("equip2", 2, 200, 98, Equipment.EquipmentSlotType.legs, 20);
		equipment2 = equipmentDao.create(equipment5);
        // insert objects for EquipmentBonus
        EquipmentBonus swordBonus = new EquipmentBonus(15, equipment4, strength);
        swordBonus = equipmentBonusDao.create(swordBonus);

        EquipmentBonus helmetBonus = new EquipmentBonus(10, equipment5, agility);
        helmetBonus = equipmentBonusDao.create(helmetBonus);

        
 
       // READ examples for Attribute.
        Attribute readStrength = attributeDao.getAttributeByName("Strength");
        System.out.printf("Read Attribute: Name=%s", 
                         readStrength.getName());
        System.out.println();
         // READ examples for CharacterAttribute.
         int character1ID = character1.getCharacterID();
         String strengthName = strength.getName();
         CharacterAttributes readCharAttr = characterAttributeDao.getCharacterAttributeByCharacterIDAndAttribute(character1ID, strengthName);
        System.out.printf("Read CharacterAttribute: Character=%s, Attribute=%s, Value=%s", 
                         readCharAttr.getCharacter().getCharacterID(), 
                          readCharAttr.getAttribute().getName(), 
                           readCharAttr.getValue());
        System.out.println();
         // READ examples for ConsumableBonus.
        ConsumableBonus readPotionBonus = consumableBonusDao.getConsumableBonusByIDAndAttribute(manaPotion.getItemID(), agility.getName());

       System.out.printf("Read ConsumableBonus: Consumable=%s, Cap=%d, Percentage=%.2f, Attribute=%s", 
    		   			readPotionBonus.getItem().getItemID(),
                         readPotionBonus.getCap(), 
                           readPotionBonus.getPercentage(),
       					readPotionBonus.getAttribute().getName());
       System.out.println();
       
       	// READ examples for EquipmentBonus.
       EquipmentBonus readEquipmentBonus = equipmentBonusDao.getEquipmentBonusByIDAndAttribute(equipment4.getItemID(), strength.getName());

       if (readEquipmentBonus != null) {
           System.out.printf("Read EquipmentBonus: Equipment=%s, Amount=%d, Attribute=%s\n",
               readEquipmentBonus.getGear().getItemID(),
               readEquipmentBonus.getAmount(),
               readEquipmentBonus.getAttribute().getName());
       } else {
           System.err.println("Error: EquipmentBonus not found for the given Equipment ID and attribute.");
       }
       
        
       // DELETE EquipmentBonus
       EquipmentBonus deletedEquipmentBonusRows = equipmentBonusDao.delete(equipment4.getItemID(), strength.getName());
 

       // DELETE ConsumableBonus
       ConsumableBonus deletedConsumableBonusRows = consumableBonusDao.delete(manaPotion.getItemID(), agility.getName());
     
       // DELETE CharacterAttribute
       CharacterAttributes deletedCharAttrRows = characterAttributeDao.delete(character1ID, strengthName);
       
       // DELETE Attribute
       Attribute deletedStrengthRows = attributeDao.delete("strength");
        
        

        
        
		
	}
	
}
