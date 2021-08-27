package com.revature.services.userFeatures;

import com.intuit.karate.junit5.Karate;


public class ViewTasksRunner {
	

     @Karate.Test
     Karate testviewScrumBoards() {
    	 return Karate.run("ViewTasks").relativeTo(getClass());
		}
	}


