package wayoftime.bloodmagic.common.routing;

import net.minecraft.core.Direction;

public interface IInputRoutingNode extends IItemRoutingNode
{
	boolean isInput(Direction side);

	IRoutingFilter getInputFilterForSide(Direction side);
}
