package wayoftime.bloodmagic.common.container;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.container.item.ContainerHolding;
import wayoftime.bloodmagic.common.container.item.ContainerFilter;
import wayoftime.bloodmagic.common.container.item.ContainerTrainingBracelet;
import wayoftime.bloodmagic.common.container.tile.ContainerAlchemicalReactionChamber;
import wayoftime.bloodmagic.common.container.tile.ContainerAlchemyTable;
import wayoftime.bloodmagic.common.container.tile.ContainerItemRoutingNode;
import wayoftime.bloodmagic.common.container.tile.ContainerMasterRoutingNode;
import wayoftime.bloodmagic.common.container.tile.ContainerSoulForge;
import wayoftime.bloodmagic.common.container.tile.ContainerTeleposer;

public class BloodMagicContainers
{
	// client/ClientEvents/registerContainerScreens might be helpful later
	public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BloodMagic.MODID);

	public static final RegistryObject<MenuType<ContainerSoulForge>> SOUL_FORGE_CONTAINER = CONTAINERS.register("soul_forge_container", () -> IForgeMenuType.create(ContainerSoulForge::new));
	public static final RegistryObject<MenuType<ContainerAlchemicalReactionChamber>> ARC_CONTAINER = CONTAINERS.register("arc_container", () -> IForgeMenuType.create(ContainerAlchemicalReactionChamber::new));
	public static final RegistryObject<MenuType<ContainerAlchemyTable>> ALCHEMY_TABLE_CONTAINER = CONTAINERS.register("alchemy_table_container", () -> IForgeMenuType.create(ContainerAlchemyTable::new));
	public static final RegistryObject<MenuType<ContainerTeleposer>> TELEPOSER_CONTAINER = CONTAINERS.register("teleposer_container", () -> IForgeMenuType.create(ContainerTeleposer::new));

	public static final RegistryObject<MenuType<ContainerMasterRoutingNode>> MASTER_ROUTING_NODE_CONTAINER = CONTAINERS.register("master_routing_node_container", () -> IForgeMenuType.create(ContainerMasterRoutingNode::new));
	public static final RegistryObject<MenuType<ContainerItemRoutingNode>> ROUTING_NODE_CONTAINER = CONTAINERS.register("routing_node_container", () -> IForgeMenuType.create(ContainerItemRoutingNode::new));

	public static final RegistryObject<MenuType<ContainerFilter>> FILTER_CONTAINER = CONTAINERS.register("filter_container", () -> IForgeMenuType.create(ContainerFilter::new));

	public static final RegistryObject<MenuType<ContainerHolding>> HOLDING_CONTAINER = CONTAINERS.register("holding_container", () -> IForgeMenuType.create(ContainerHolding::new));
	public static final RegistryObject<MenuType<ContainerTrainingBracelet>> TRAINING_BRACELET_CONTAINER = CONTAINERS.register("training_bracelet_container", () -> IForgeMenuType.create(ContainerTrainingBracelet::new));
}
