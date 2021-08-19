package com.revature.beans;

public enum SprintStatus {

	PAST,

	CURRENT {
		private static final int LIMIT = 1;

		public int getLimit() {
			return LIMIT;
		}
	},

	FUTURE
}
