package wayoftime.bloodmagic.common.routing;

import net.minecraft.core.Direction;

public interface IOutputRoutingNode extends IItemRoutingNode
{
	boolean isOutput(Direction side);

	IRoutingFilter getOutputFilterForSide(Direction side);
}
