package com.revature.services.userFeatures;

import com.intuit.karate.junit5.Karate;

public class ViewScrumBoardsRunner {

	@Karate.Test
	Karate testviewScrumBoards() {
		return Karate.run("ViewScrumBoards").relativeTo(getClass());
	}
}
