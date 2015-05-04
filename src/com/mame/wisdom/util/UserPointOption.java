package com.mame.wisdom.util;

public enum UserPointOption {
	POINT_CREATE_WISDOM, POINT_MODIFY_WISDOM, POINT_LIKE_WISDOM;

	private final static int CREATE = 10;

	private final static int MODIFY = 5;

	private final static int LIKE = 2;

	public static int getPoint(UserPointOption option) {
		switch (option) {
		case POINT_CREATE_WISDOM:
			return CREATE;
		case POINT_MODIFY_WISDOM:
			return MODIFY;
		case POINT_LIKE_WISDOM:
			return LIKE;
		}
		return 0;
	}
}

// private UserPointUpdater() {
// // This class should be used by static way.
// }
//
// public static int createWisdom(int currentPoint) {
// return currentPoint + WConstant.POINT_CREATE_WISDOM;
// }
//
// public static int modifyWisdom(int currentPoint) {
// return currentPoint + WConstant.POINT_MODIFY_WISDOM;
// }
//
// public static int likeToWisdom(int currentPoint) {
// return currentPoint + WConstant.POINT_LIKE_WISDOM;
// }
// }
