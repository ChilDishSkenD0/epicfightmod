package maninhouse.epicfight.item;

import maninhouse.epicfight.main.EpicFightMod;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EpicFightMod.MODID);
	
	public static final RegistryObject<Item> KATANA = ITEMS.register("katana", () -> new KatanaItem(new Item.Properties().group(EpicFightItemGroup.ITEMS).rarity(Rarity.RARE).defaultMaxDamage(650)));
	public static final RegistryObject<Item> KATANA_SHEATH = ITEMS.register("katana_sheath", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> STONE_GREATSWORD = ITEMS.register("stone_greatsword", () -> new GreatswordItem(new Item.Properties().group(EpicFightItemGroup.ITEMS).defaultMaxDamage(1561), ItemTier.STONE));
	public static final RegistryObject<Item> IRON_GREATSWORD = ITEMS.register("iron_greatsword", () -> new GreatswordItem(new Item.Properties().group(EpicFightItemGroup.ITEMS).defaultMaxDamage(1561), ItemTier.IRON));
	public static final RegistryObject<Item> GOLDEN_GREATSWORD = ITEMS.register("golden_greatsword", () -> new GreatswordItem(new Item.Properties().group(EpicFightItemGroup.ITEMS).defaultMaxDamage(1561), ItemTier.GOLD));
	public static final RegistryObject<Item> DIAMOND_GREATSWORD = ITEMS.register("diamond_greatsword", () -> new GreatswordItem(new Item.Properties().group(EpicFightItemGroup.ITEMS).defaultMaxDamage(1561), ItemTier.DIAMOND));
	public static final RegistryObject<Item> NETHERITE_GREATSWORD = ITEMS.register("netherite_greatsword", () -> new GreatswordItem(new Item.Properties().group(EpicFightItemGroup.ITEMS).defaultMaxDamage(1561), ItemTier.NETHERITE));
	
	public static final RegistryObject<Item> STONE_SPEAR = ITEMS.register("stone_spear", () -> new SpearItem(new Item.Properties().group(EpicFightItemGroup.ITEMS), ItemTier.STONE));
	public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register("iron_spear", () -> new SpearItem(new Item.Properties().group(EpicFightItemGroup.ITEMS), ItemTier.IRON));
	public static final RegistryObject<Item> GOLDEN_SPEAR = ITEMS.register("golden_spear", () -> new SpearItem(new Item.Properties().group(EpicFightItemGroup.ITEMS), ItemTier.GOLD));
	public static final RegistryObject<Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear", () -> new SpearItem(new Item.Properties().group(EpicFightItemGroup.ITEMS), ItemTier.DIAMOND));
	public static final RegistryObject<Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear", () -> new SpearItem(new Item.Properties().group(EpicFightItemGroup.ITEMS), ItemTier.NETHERITE));
	
	public static final RegistryObject<Item> WOODEN_TACHI = ITEMS.register("wooden_tachi", () -> new TachiItem(new Item.Properties().group(EpicFightItemGroup.ITEMS), ItemTier.WOOD));
	public static final RegistryObject<Item> STONE_TACHI = ITEMS.register("stone_tachi", () -> new TachiItem(new Item.Properties().group(EpicFightItemGroup.ITEMS), ItemTier.STONE));
	public static final RegistryObject<Item> IRON_TACHI = ITEMS.register("iron_tachi", () -> new TachiItem(new Item.Properties().group(EpicFightItemGroup.ITEMS), ItemTier.IRON));
	public static final RegistryObject<Item> GOLDEN_TACHI = ITEMS.register("golden_tachi", () -> new TachiItem(new Item.Properties().group(EpicFightItemGroup.ITEMS), ItemTier.GOLD));
	public static final RegistryObject<Item> DIAMOND_TACHI = ITEMS.register("diamond_tachi", () -> new TachiItem(new Item.Properties().group(EpicFightItemGroup.ITEMS), ItemTier.DIAMOND));
	public static final RegistryObject<Item> NETHERITE_TACHI = ITEMS.register("netherite_tachi", () -> new TachiItem(new Item.Properties().group(EpicFightItemGroup.ITEMS), ItemTier.NETHERITE));
	
	public static final RegistryObject<Item> KNUCKLE = ITEMS.register("knuckle", () -> new KnuckleItem(new Item.Properties().group(EpicFightItemGroup.ITEMS), ModItemTier.KNUCKLE));
	
	public static final RegistryObject<Item> STRAY_HAT = ITEMS.register("stray_hat", () -> new ArmorItem(ModArmorMaterials.STRAY_CLOTH, EquipmentSlotType.HEAD, new Item.Properties().group(EpicFightItemGroup.ITEMS)));
	public static final RegistryObject<Item> STRAY_ROBE = ITEMS.register("stray_robe", () -> new ArmorItem(ModArmorMaterials.STRAY_CLOTH, EquipmentSlotType.CHEST, new Item.Properties().group(EpicFightItemGroup.ITEMS)));
	public static final RegistryObject<Item> STRAY_PANTS = ITEMS.register("stray_pants", () -> new ArmorItem(ModArmorMaterials.STRAY_CLOTH, EquipmentSlotType.LEGS, new Item.Properties().group(EpicFightItemGroup.ITEMS)));
	public static final RegistryObject<Item> SKILLBOOK = ITEMS.register("skillbook", () -> new SkillBookItem(new Item.Properties().group(EpicFightItemGroup.ITEMS).rarity(Rarity.RARE)));
}