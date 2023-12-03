package wayoftime.bloodmagic.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import wayoftime.bloodmagic.common.tile.BloodMagicTileEntities;
import wayoftime.bloodmagic.common.tile.TileBloodTank;

public class BlockBloodTank extends Block implements EntityBlock
{
	protected static final VoxelShape BODY = Block.box(4, 0, 4, 12, 13, 12);

    public BlockBloodTank()
    {
        super(Properties.of(Material.STONE).strength(2.0f, 5.0f).sound(SoundType.STONE).requiresCorrectToolForDrops());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new TileBloodTank(BloodMagicTileEntities.BLOOD_TANK_TYPE.get(), pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
    {
        return BODY;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockRayTraceResult)
    {
        if (world.isClientSide)
            return InteractionResult.SUCCESS;

        BlockEntity tile = world.getBlockEntity(pos);
        if (!(tile instanceof TileBloodTank))
            return InteractionResult.FAIL;
        
        if (!player.isShiftKeyDown())
        {
            ItemStack handStack = player.getItemInHand(hand);
            if (!handStack.isEmpty() && FluidUtil.interactWithFluidHandler(player, hand, ((TileBloodTank) tile).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).resolve().get()))
                return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }
}