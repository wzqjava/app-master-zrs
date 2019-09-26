package com.wd.health.utils;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created :  LiZhIX
 * Date :  2019/8/8 15:25
 * Description  :  禁止输入空格
 */
public class SpaceFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        //返回null表示接收输入的字符,返回空字符串表示不接受输入的字符
        if (source.equals(" "))
            return "";
        return null;
    }
}