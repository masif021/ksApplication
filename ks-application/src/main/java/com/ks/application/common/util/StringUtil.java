package com.ks.application.common.util;

public class StringUtil {

    public static boolean isNotEmpty(String str)
    {
	return str != null && str.length() > 0;
    }
 
    public static boolean isEmptyString(String str)
    {
	return str == null || str.length() == 0;
    }
 
    /**
     * Takes a String and strip from it whatever is in the delimeter array and
     * returns a new string with these chars removed.
     */
    public static String replaceInString(String stringToStrip, String[] whatArray, String... withArray)
    {
	String copyOfString = stringToStrip, stringPart1, stringPart2;
	for(int i = 0; i < whatArray.length; i++)
	{
	    if(copyOfString.indexOf(whatArray[i]) != -1)
	    {
		stringPart1 = copyOfString.substring(0, copyOfString.indexOf(whatArray[i]));
		stringPart2 = copyOfString.substring(copyOfString.indexOf(whatArray[i]) + whatArray[i].length());
		copyOfString = stringPart1 + withArray[i] + replaceInString(stringPart2, whatArray, withArray);
	    }
	}
	return copyOfString;
    }
 
    public static String maskString(String str, int start, int end,Character maskedChar) {
        if (str == null || str.length() < end) {
            return str; // safety check
        }

        StringBuilder sb = new StringBuilder(str);
        for (int i = start; i < end; i++) {
            sb.setCharAt(i, maskedChar);
        }
        return sb.toString();
    }

    /**
     * Takes a String and strip from it whatever is in the delimeter string and
     * returns a new string with these chars removed.
     *
     * @see #replaceInString(String stringToStrip, String[] whatArray, String[]
     *      withArray)
     */
    public static String replaceInString(String stringToStrip, String what, String with)
    {
	return replaceInString(stringToStrip, new String[] { what }, new String[] { with });
    }
 
    /**
     * returns substring of given string from beginning till specified length
     */
    public static String substring(String value, int length)
    {
	if(value == null)
	{
	    return null;
	}
	else
	{
	    if(value.length() <= length)
	    {
		return value;
	    }
	    else
	    {
		return value.substring(0, length);
	    }
	}
    }
 
    /**
     *
     * Used for stripping String from start index to specific length,
     * corresponds to PB mid method
     *
     * @param value Value to Substring
     * @param startIndex Start Index
     * @param length Length to Substring
     * @return
     */
    public static String substring(String value, int startIndex, int theLength)
    {
	String result = "";
	int length = theLength;
	int endIndex = 0;
	if(value != null)
	{
	    if(startIndex > value.length())
	    {
		return "";
	    }
	    if(length > value.length())
	    {
		length = value.length();
	    }
	    endIndex = startIndex - 1 + length;
	    if(endIndex > value.length())
	    {
		endIndex = value.length();
	    }
	    result = value.substring(startIndex - 1, endIndex).trim();
	}
 
	return result;
    }
 
    /**
     * returns substring of given string starting from the end
     */
    public static String laststring(String value, int length)
    {
	if(value == null)
	{
	    return null;
	}
	else
	{
	    if(value.length() <= length)
	    {
		return value;
	    }
	    else
	    {
		return value.substring(value.length() - length, value.length());
	    }
	}
    }
 
    /**
     * Replaces the null string with empty
     */
    public static String nullToEmpty(Object obj)
    {
	if(obj == null)
	{
	    return "";
	}
	else
	{
	    return obj.toString();
	}
    }
 
    /**
     * Replaces the null or empty string with given Value
     */
    public static String nullEmptyToValue(Object obj, Object toValue)
    {
	if(obj == null)
	{
	    if(toValue == null)
	    {
		return "";
	    }
	    else
	    {
		return toValue.toString();
	    }
	}
	else
	{
	    if("".equals(obj.toString()))
	    {
		if(toValue == null)
		{
		    return "";
		}
		else
		{
		    return toValue.toString();
		}
	    }
	    else
	    {
		return obj.toString();
	    }
	}
    }
 
    /**
     * Replaces the null String by an empty String
     *
     * @param nullString String
     * @return String
     */
    public static String nullToEmpty(String nullString)
    {
	if(nullString == null || "null".equals(nullString))
	{
	    return "";
	}
	else
	{
	    return nullString.trim();
	}
    }
 
}
