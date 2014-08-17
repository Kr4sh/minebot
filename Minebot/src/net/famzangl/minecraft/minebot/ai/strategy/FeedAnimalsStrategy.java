package net.famzangl.minecraft.minebot.ai.strategy;

import net.famzangl.minecraft.minebot.ai.AIHelper;
import net.famzangl.minecraft.minebot.ai.selectors.AndSelector;
import net.famzangl.minecraft.minebot.ai.selectors.ColorSelector;
import net.famzangl.minecraft.minebot.ai.selectors.FeedableSelector;
import net.famzangl.minecraft.minebot.ai.selectors.FilterFeedingItem;
import net.famzangl.minecraft.minebot.ai.selectors.OrSelector;
import net.famzangl.minecraft.minebot.ai.selectors.XPOrbSelector;
import net.famzangl.minecraft.minebot.ai.task.FaceAndInteractTask;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;

public class FeedAnimalsStrategy extends TaskStrategy {

	private static final int DISTANCE = 20;
	private final int color;

	public FeedAnimalsStrategy() {
		this(-1);
	}

	public FeedAnimalsStrategy(int color) {
		this.color = color;
	}

	@Override
	public void searchTasks(AIHelper helper) {
		feedWithFood(helper);
	}
	

	private void feedWithFood(AIHelper helper) {
		IEntitySelector selector = new FeedableSelector(helper);
		if (color >= 0) {
			selector = new AndSelector(selector, new ColorSelector(color));
		}

		final IEntitySelector collect = new XPOrbSelector();

		final Entity found = helper.getClosestEntity(DISTANCE, new OrSelector(
				selector, collect));

		if (found != null) {
			addTask(new FaceAndInteractTask(found, selector) {
				@Override
				protected void doInteractWithCurrent(AIHelper h) {
					final Entity over = h.getObjectMouseOver().entityHit;
					if (over instanceof EntityAnimal
							&& h.selectCurrentItem(new FilterFeedingItem(
									(EntityAnimal) over))) {
						super.doInteractWithCurrent(h);
					} else if (found == over) {
						interacted = true;
					}
				}
			});
		}
	}

	@Override
	public String getDescription() {
		return "Feeding";
	}
}