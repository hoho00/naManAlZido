package com.hackerton.googlemap.data;

import android.provider.BaseColumns;

public class ReviewContract {
    private ReviewContract(){}

    public static class ReviewEntry implements BaseColumns {
        public static final String TABLE_NAME = "reviews";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_CONTENTS = "contents";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_SCORE = "score";
    }
}
