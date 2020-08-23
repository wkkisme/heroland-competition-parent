package com.heroland.competition.common.enums;


public enum HeroLevelEnum {

    /**
     * 逆境英雄
     */
    ADVERSITY_HERO,
    /**
     * 奋勇英雄
     */
    COURAGEOUS_HERO,

    /**
     * 至尊英雄
     */
    SUPREME_HERO;


    /**
     * 计算差距
     *
     * @param beforeLevel 前一个英雄等级
     * @param afterLevel  后一个英雄等级
     * @return 差距
     */
    public static int getLevelDistance(String beforeLevel, String afterLevel) {

        HeroLevelEnum before;
        try {
            before = HeroLevelEnum.valueOf(beforeLevel);
        } catch (Exception e) {

            before = ADVERSITY_HERO;
        }

        HeroLevelEnum after;
        try {
            after = HeroLevelEnum.valueOf(afterLevel);
        } catch (Exception e) {
            after = ADVERSITY_HERO;
        }
        if (before.equals(after)) {
            return 0;
        }

        if (before.equals(ADVERSITY_HERO)) {
            if (after.equals(COURAGEOUS_HERO)) {

                return -1;
            } else {

                return -2;
            }

        }
        if (before.equals(COURAGEOUS_HERO)) {

            if (after.equals(ADVERSITY_HERO)) {
                return 1;
            } else {
                return -1;
            }
        }

        if (before.equals(SUPREME_HERO)) {

            if (after.equals(ADVERSITY_HERO)) {
                return 2;
            } else {
                return 1;
            }

        }
        return 0;
    }

    /**
     * 勝高兩級對手，得6分；
     * 勝高一級對手，得5分；
     * 勝同級別對手，得4分；
     * 勝低一級對手，得3分；
     * 勝低兩級對手，得2分。
     *
     * @return score
     */
    public static int getLevelScore(String before, String after) {
        int levelDistance = getLevelDistance(before, after);
        if (levelDistance == 0) {
            return 4;
        } else if (levelDistance == -2) {
            return 2;
        } else if (levelDistance == -1) {
            return 3;
        } else if (levelDistance == 1) {
            return 5;
        } else {
            return 6;
        }

    }

}
