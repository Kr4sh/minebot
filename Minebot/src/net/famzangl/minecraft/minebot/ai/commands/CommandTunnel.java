package net.famzangl.minecraft.minebot.ai.commands;

import net.famzangl.minecraft.minebot.Pos;
import net.famzangl.minecraft.minebot.ai.AIHelper;
import net.famzangl.minecraft.minebot.ai.command.AICommand;
import net.famzangl.minecraft.minebot.ai.command.AICommandInvocation;
import net.famzangl.minecraft.minebot.ai.command.AICommandParameter;
import net.famzangl.minecraft.minebot.ai.command.ParameterType;
import net.famzangl.minecraft.minebot.ai.path.TunnelPathFinder;
import net.famzangl.minecraft.minebot.ai.path.TunnelPathFinder.TorchSide;
import net.famzangl.minecraft.minebot.ai.strategy.AIStrategy;
import net.famzangl.minecraft.minebot.ai.strategy.PathFinderStrategy;
import net.famzangl.minecraft.minebot.ai.strategy.ValueActionStrategy;
import net.minecraftforge.common.util.ForgeDirection;

@AICommand(helpText = "Build a tunnel with the given profile", name = "minebot")
public class CommandTunnel {
	@AICommandInvocation()
	public static AIStrategy run(
			AIHelper helper,
			@AICommandParameter(type = ParameterType.FIXED, fixedName = "tunnel", description = "") String nameArg) {
		return run(helper, nameArg, helper.getLookDirection(), 0, 0);
	}

	@AICommandInvocation()
	public static AIStrategy run(
			AIHelper helper,
			@AICommandParameter(type = ParameterType.FIXED, fixedName = "tunnel", description = "") String nameArg,
			@AICommandParameter(type = ParameterType.ENUM, description = "direction") ForgeDirection inDirection) {
		return run(helper, nameArg, inDirection, 0, 0);
	}

	@AICommandInvocation()
	public static AIStrategy run(
			AIHelper helper,
			@AICommandParameter(type = ParameterType.FIXED, fixedName = "tunnel", description = "") String nameArg,
			@AICommandParameter(type = ParameterType.NUMBER, description = "add to side") int addToSide,
			@AICommandParameter(type = ParameterType.NUMBER, description = "add to top") int addToTop) {
		return run(helper, nameArg, helper.getLookDirection(), addToSide,
				addToTop);
	}

	@AICommandInvocation()
	public static AIStrategy run(
			AIHelper helper,
			@AICommandParameter(type = ParameterType.FIXED, fixedName = "tunnel", description = "") String nameArg,
			@AICommandParameter(type = ParameterType.NUMBER, description = "add to side") int addToSide,
			@AICommandParameter(type = ParameterType.NUMBER, description = "add to top") int addToTop,
			@AICommandParameter(type = ParameterType.ENUM, description = "torche side") TorchSide torches) {
		return run(helper, nameArg, helper.getLookDirection(), addToSide,
				addToTop, torches);
	}

	@AICommandInvocation()
	public static AIStrategy run(
			AIHelper helper,
			@AICommandParameter(type = ParameterType.FIXED, fixedName = "tunnel", description = "") String nameArg,
			@AICommandParameter(type = ParameterType.ENUM, description = "direction") ForgeDirection inDirection,
			@AICommandParameter(type = ParameterType.NUMBER, description = "add to side") int addToSide,
			@AICommandParameter(type = ParameterType.NUMBER, description = "add to top") int addToTop) {
		return run(helper, inDirection.offsetX, inDirection.offsetZ, addToSide,
				addToTop, TorchSide.NONE);
	}

	@AICommandInvocation()
	public static AIStrategy run(
			AIHelper helper,
			@AICommandParameter(type = ParameterType.FIXED, fixedName = "tunnel", description = "") String nameArg,
			@AICommandParameter(type = ParameterType.ENUM, description = "direction") ForgeDirection inDirection,
			@AICommandParameter(type = ParameterType.NUMBER, description = "add to side") int addToSide,
			@AICommandParameter(type = ParameterType.NUMBER, description = "add to top") int addToTop,
			@AICommandParameter(type = ParameterType.ENUM, description = "torche side") TorchSide torches) {
		return run(helper, inDirection.offsetX, inDirection.offsetZ, addToSide,
				addToTop, torches);
	}

	public static AIStrategy run(AIHelper helper, int dx, int dz,
			int addToSide, int addToTop, TorchSide torches) {
		final Pos pos = helper.getPlayerPosition();
		return ValueActionStrategy.makeSafe(new PathFinderStrategy(
				new TunnelPathFinder(dx, dz, pos.x, pos.y, pos.z, addToSide,
						addToTop, torches), "Tunneling"));
	}
}
