package com.yarolegovich.discretescrollview;

/**
 * @描述：      @Direction
 * @作者：      @黄卫旗
 * @创建时间：  @2018-03-31
 */
enum Direction {

    START {
        @Override
        public int applyTo(int delta) {
            return delta * -1;
        }

        @Override
        public boolean sameAs(int direction) {
            return direction < 0;
        }
    },
    END {
        @Override
        public int applyTo(int delta) {
            return delta;
        }

        @Override
        public boolean sameAs(int direction) {
            return direction > 0;
        }
    };

    public abstract int applyTo(int delta);

    public abstract boolean sameAs(int direction);

    public static Direction fromDelta(int delta) {
        return delta > 0 ? END : START;
    }
}
