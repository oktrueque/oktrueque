package com.oktrueque.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Constants {

    public static final int TRUEQUE_STATUS_PENDING = 0;
    public static final int TRUEQUE_STATUS_ACTIVE = 1;
    public static final int TRUEQUE_STATUS_REJECTED = 2;
    public static final int TRUEQUE_STATUS_CANCELED = 3;
    public static final int TRUEQUE_STATUS_CONFIRMED = 4;

    public static final int ITEM_STATUS_PENDING = 0;
    public static final int ITEM_STATUS_ACTIVE = 1;
    public static final int ITEM_STATUS_DELETED = 2;
    public static final int ITEM_STATUS_BANNED = 3;

    private static final Map<Integer, String> defaultProfilePictures;
    static
    {
        defaultProfilePictures = new HashMap<>();
        defaultProfilePictures.put(1, "https://oktrueque.s3-sa-east-1.amazonaws.com/default/U-4");
        defaultProfilePictures.put(2, "https://oktrueque.s3-sa-east-1.amazonaws.com/default/U-2");
        defaultProfilePictures.put(3, "https://oktrueque.s3-sa-east-1.amazonaws.com/default/U-3");
        defaultProfilePictures.put(4, "https://oktrueque.s3-sa-east-1.amazonaws.com/default/U-4");
        defaultProfilePictures.put(5, "https://oktrueque.s3-sa-east-1.amazonaws.com/default/U-5");
        defaultProfilePictures.put(6, "https://oktrueque.s3-sa-east-1.amazonaws.com/default/U-6");
        defaultProfilePictures.put(7, "https://oktrueque.s3-sa-east-1.amazonaws.com/default/U-7");
        defaultProfilePictures.put(8, "https://oktrueque.s3-sa-east-1.amazonaws.com/default/U-8");
        defaultProfilePictures.put(9, "https://oktrueque.s3-sa-east-1.amazonaws.com/default/U-9");
    }

    public static String returnRandomImage(){
        Random r = new Random();
        int Low = 1;
        int High = 10;
        int result = r.nextInt(High-Low) + Low;
        return defaultProfilePictures.get(result);
    }
}
