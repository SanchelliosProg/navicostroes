package com.tstasks.sanchellios.navicostores.phone_calls;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by alex on 06.08.16.
 */
public class PhoneFormatter {
    public String getReformattedPhone(String phone){
        ArrayList<Character> correctPhoneFormat = new ArrayList<>();
        char[] phoneChars = phone.toCharArray();
        for (char ch : phoneChars){
            if(Character.isDigit(ch)){
                correctPhoneFormat.add(ch);
            }
        }
        return getStringRepresentation(correctPhoneFormat);
    }

    private String getStringRepresentation(ArrayList<Character> list) {
        StringBuilder builder = new StringBuilder(list.size());
        for (Character ch : list) {
            builder.append(ch);
        }
        return builder.toString();
    }


}
