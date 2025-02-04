package com.teammetallurgy.aquaculture.block;

import com.teammetallurgy.aquaculture.block.tileentity.TackleBoxTileEntity;
import com.teammetallurgy.aquaculture.init.AquaBlocks;
import com.teammetallurgy.aquaculture.misc.StackHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

public class TackleBoxBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape NORTH_SOUTH = Block.box(0.8D, 0.0D, 3.9D, 15.2D, 9.0D, 12.2D);
    private static final VoxelShape EAST_WEST = Block.box(3.9D, 0.0D, 0.8D, 12.2D, 9.0D, 15.2D);

    public TackleBoxBlock() {
        super(Block.Properties.of(Material.METAL, MaterialColor.METAL).strength(4.0F, 5.0F).sound(SoundType.METAL));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new TackleBoxTileEntity(pos, state);
    }

    @Override
    @Nonnull
    public RenderShape getRenderShape(@Nonnull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    @Nonnull
    public VoxelShape getShape(BlockState state, @Nonnull BlockGetter blockReader, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        switch (state.getValue(FACING)) {
            case NORTH:
            case SOUTH:
                return NORTH_SOUTH;
            case EAST:
            case WEST:
                return EAST_WEST;
        }
        return super.getShape(state, blockReader, pos, context);
    }

    @Override
    @Nonnull
    public InteractionResult use(@Nonnull BlockState state, Level world, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            MenuProvider container = this.getMenuProvider(state, world, pos);
            if (container != null && player instanceof ServerPlayer serverPlayer) {
                if (player.isShiftKeyDown()) {
                    BlockEntity tileEntity = world.getBlockEntity(pos);
                    if (tileEntity != null) {
                        StackHelper.giveItem(serverPlayer, StackHelper.storeTEInStack(new ItemStack(this), tileEntity));
                        world.removeBlock(pos, false);
                        world.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.6F, 0.8F);
                    }
                } else {
                    NetworkHooks.openGui(serverPlayer, container, pos);
                }
            }
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction placerFacing = context.getHorizontalDirection().getOpposite();
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, placerFacing).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void setPlacedBy(@Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState state, LivingEntity placer, @Nonnull ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            BlockEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof TackleBoxTileEntity) {
                ((TackleBoxTileEntity) tileentity).setCustomName(stack.getHoverName());
            }
        }
    }

    @Override
    public void onRemove(BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            world.updateNeighbourForOutputSignal(pos, this);
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    @Nonnull
    public BlockState updateShape(BlockState state, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor world, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    @Nonnull
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean hasAnalogOutputSignal(@Nonnull BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(@Nonnull BlockState state, Level world, @Nonnull BlockPos pos) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof TackleBoxTileEntity) {
            Optional<Integer> redstone = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).map(ItemHandlerHelper::calcRedstoneFromInventory);
            return redstone.orElse(0);
        }
        return 0;
    }

    @Override
    @Nonnull
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    @Nonnull
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(@Nonnull BlockState state, @Nonnull BlockGetter blockReader, @Nonnull BlockPos pos, @Nonnull PathComputationType type) {
        return false;
    }

    @Override
    public void playerDestroy(@Nonnull Level world, Player player, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable BlockEntity tileEntity, @Nonnull ItemStack stack) {
        player.awardStat(Stats.BLOCK_MINED.get(this));
        player.causeFoodExhaustion(0.005F);
        if (tileEntity != null) {
            popResource(world, pos, StackHelper.storeTEInStack(new ItemStack(this), tileEntity));
        }
    }

    @Override
    public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
        if (!world.isClientSide) {
            this.dropInventory(world, pos);
        }
        super.onBlockExploded(state, world, pos, explosion);
    }

    private void dropInventory(Level world, BlockPos pos) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(handler -> StackHelper.dropInventory(world, pos, handler));
        }
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? createTickerHelper(blockEntityType, AquaBlocks.AquaTileEntities.TACKLE_BOX, TackleBoxTileEntity::lidAnimateTick) : null;
    }

    @Override
    public void tick(@Nonnull BlockState state, ServerLevel serverLevel, @Nonnull BlockPos pos, @Nonnull Random rand) {
        BlockEntity blockEntity = serverLevel.getBlockEntity(pos);
        if (blockEntity instanceof TackleBoxTileEntity) {
            ((TackleBoxTileEntity) blockEntity).recheckOpen();
        }
    }
}