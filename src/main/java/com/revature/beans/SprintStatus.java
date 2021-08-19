package com.revature.beans;

public enum SprintStatus {

	PAST,

	CURRENT {
		private static final int limit = 1;

		public int getLimit() {
			return limit;
		}
	},

	FUTURE
}
