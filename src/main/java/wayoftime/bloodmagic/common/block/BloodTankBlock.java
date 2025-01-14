package wayoftime.bloodmagic.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.common.blockentity.BloodTankTile;

// LiquidBlockContainer#canPlaceLiquid is one of the checks done by FlowingFluid to see if it can break the block...
// Returning a full block for getCollisionShape might also work but that has other implications
public class BloodTankBlock extends Block implements LiquidBlockContainer, EntityBlock {
    protected static final VoxelShape BOX = Block.box(3, 0, 3, 13, 14, 13);

    public BloodTankBlock() {
        super(BlockBehaviour.Properties.of()
                .requiresCorrectToolForDrops()
                .strength(2.0F, 5.0F)
                .sound(SoundType.GLASS)
        );
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BOX;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        IFluidHandler tank = level.getCapability(Capabilities.FluidHandler.BLOCK, pos, null);
        if (tank == null) {
            return ItemInteractionResult.CONSUME;
        }
        if (!FluidUtil.interactWithFluidHandler(player, hand, tank)) {
            return ItemInteractionResult.CONSUME;
        }
        player.getInventory().setChanged();
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BloodTankTile(blockPos, blockState);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }
}
