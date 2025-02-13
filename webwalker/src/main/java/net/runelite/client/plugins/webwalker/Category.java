/*
 * Copyright (c) 2019-2020, ganom <https://github.com/Ganom>
 * All rights reserved.
 * Licensed under GPL3, see LICENSE for the full scope.
 */
package net.runelite.client.plugins.webwalker;

import lombok.Getter;

@Getter
public enum Category
{
	BANKS("Banks"),
	QUEST("Quest"),
	BARCRAWL("Barcrawl"),
	CITIES("Cities"),
	FARMING("Farming"),
	GUILDS("Guilds"),
	SKILLING("Skilling"),
	SLAYER("Slayer"),
	MISC("Misc"),
	CUSTOM("Custom");

	private final String name;

	Category(String name)
	{
		this.name = name;
	}

}
