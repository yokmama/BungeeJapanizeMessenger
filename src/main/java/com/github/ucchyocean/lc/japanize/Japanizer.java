/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2014
 */
package com.github.ucchyocean.lc.japanize;

import java.util.HashMap;

/**
 * ローマ字表記を漢字変換して返すユーティリティ
 * @author ucchy
 */
public class Japanizer {

    private static final String REGEX_URL = "https?://[\\w/:%#\\$&\\?\\(\\)~\\.=\\+\\-]+";

    public static boolean is2Byte(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if ((c <= '\u007e') || // 英数字
            (c == '\u00a5') || // \記号
            (c == '\u203e') || // ~記号
            (c >= '\uff61' && c <= '\uff9f') // 半角カナ
        ) {
                //System.out.print("半");
            } else {
                //全角
                return true;
            }
        }
        return false;
    }


    /**
     * メッセージの日本語化をする
     * @param org
     * @param type
     * @param dictionary
     * @return
     */
    public static String japanize(String org, JapanizeType type,
            HashMap<String, String> dictionary) {

        // 変換不要なら空文字列を返す
        if ( type == JapanizeType.NONE || !isNeedToJapanize(org) ) {
            return "";
        }else if(is2Byte(org)){
            //2byte文字を含んでいるなら変換不要
            return "";
        }

        // URL削除
        String deletedURL = org.replaceAll(REGEX_URL, " ");

        // キーワードをロック
        HashMap<String, String> keywordMap = new HashMap<String, String>();
        int index = 0;
        String keywordLocked = deletedURL;
        for ( String dickey : dictionary.keySet() ) {
            if ( keywordLocked.contains(dickey) ) {
                index++;
                String key = "＜" + makeMultibytesDigit(index) + "＞";
                keywordLocked = keywordLocked.replace(dickey, key);
                keywordMap.put(key, dictionary.get(dickey));
            }
        }

        // カナ変換
        String japanized = KanaConverter.conv(keywordLocked);

        // IME変換
        if ( type == JapanizeType.GOOGLE_IME ) {
            japanized = IMEConverter.convByGoogleIME(japanized);
//        } else if ( type == JapanizeType.SOCIAL_IME ) {
//            japanized = IMEConverter.convBySocialIME(japanized);
        }

        // キーワードのアンロック
        for ( String key : keywordMap.keySet() ) {
            japanized = japanized.replace(key, keywordMap.get(key));
        }

        // 返す
        return japanized.trim();
    }

    /**
     * 日本語化が必要かどうかを判定する
     * @param org
     * @return
     */
    private static boolean isNeedToJapanize(String org) {
        return ( org.getBytes().length == org.length()
                && !org.matches("[ \\uFF61-\\uFF9F]+") );
    }

    /**
     * 数値を、全角文字の文字列に変換して返す
     * @param digit
     * @return
     */
    private static String makeMultibytesDigit(int digit) {

        String half = Integer.toString(digit);
        StringBuilder result = new StringBuilder();
        for ( int index=0; index < half.length(); index++ ) {
            switch ( half.charAt(index) ) {
            case '0' : result.append("０"); break;
            case '1' : result.append("１"); break;
            case '2' : result.append("２"); break;
            case '3' : result.append("３"); break;
            case '4' : result.append("４"); break;
            case '5' : result.append("５"); break;
            case '6' : result.append("６"); break;
            case '7' : result.append("７"); break;
            case '8' : result.append("８"); break;
            case '9' : result.append("９"); break;
            }
        }
        return result.toString();
    }
}
