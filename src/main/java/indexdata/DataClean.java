/*
package indexdata;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

*/
/**
 *  数据清洗规则
 *  数据库【tianyanchagongshang】
 *  数据表【company】
 *  @Author fss
 *  @Date 18:10 2018/06/12
 * Created by admin on 2018/6/12.
 *//*

public class DataClean {
    //保存当不匹配企业性质条件时的信息（可能用不到了，不需要保存）
    List list_company_org_type = new ArrayList();

    */
/**
     * 过滤企业名称name字段
     * @param name String
     * @return 转换后的name字段
     *//*

    //============================【 规则一   过滤企业名称（name） 】================================
    public static String filter_Name(String name){
        if(StringUtils.isBlank(name)){
            return "";
        }
        //括号统一为英文半角 1.1》 1.7》
        name = conversion_Parentheses(name);
        //英文字母统一为英文半角 1.2》 1.4》
        name = ToDBC(name);
        //去掉特殊符号或空格 1.3》
        name = special_Symbols(name);
        //过滤字段前面的数字和字母 1.5》
        name = letter_Digital(name);
        return name;
    }

    */
/**
     * 1、	括号统一为英文半角括号
     * @param name String
     * @return 转换为英文半角括号的name
     *//*

    public static String conversion_Parentheses(String name){
        // 转换小括号
        name = name.replaceAll("（", "(").replaceAll("）", ")");
        // 转换大括号
        name = name.replace("｛", "{").replace("｝", "}");
        // 转换尖括号
        name = name.replace("＜", "<").replace("＞", ">");
        // 转换中文尖括号
        name = name.replace("《", "<").replace("》", ">");
        // 转换中文尖括号
        name = name.replace("<<", "《").replace(">>", "》");
        return name;
    }


    */
/**
     * 2-1.半角转全角
     * @param name String.
     * @return 英文全角字符串.
     *//*

    public static String ToSBC(String name) {
        char c[] = name.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

    */
/**
     * 2-2.全角转半角
     * @param name String.
     * @return 英文半角字符串
     *//*

    public static String ToDBC(String name) {
        char c[] = name.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    */
/**
     *  过滤特殊符号
     * @param name
     * @return 去掉特殊符号的name
     *//*

    public static String special_Symbols(String name){
        // 去除开头或者结尾的 任何空白字符或者其他特殊符号
        name.replaceAll("(^(\\s|\\*|\\.|\\d|\\$|&|\\+|-|_|#|!|！|@|、|\")+)|((\\s|\\*|\\.|\\d|\\$|&|\\+|-|_|#|!|！|@|、|\")+$)", "");
        return name;
    }

    */
/**
     *  过滤字母和数字
     * @param name
     * @return
     *//*

    public static String letter_Digital(String name){
        int i = 0;
        int j = 0;
        char[] strArr = name.toCharArray();
        for (char string : strArr) {
            // 判断是否为字母
            if ((string+"").matches("[a-z]") || (string+"").matches("[A-Z]")){
                i++;
            }
            // 判断是否为数字
            if ((string+"").matches("[0-9]")){
                j++;
            }
        }

        if(i==1){
            name = name.substring(i).trim();
        }else if(i>1){
            name = name.trim();
        }else if(j>0){
            name = name.substring(j).trim();
        }
        return name;
    }

    */
/**
     *  判断字符是否是中文(判断乱码中用到的方法)
     *
     * @param c 字符
     * @return 是否是中文
     *//*

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    */
/**
     *  判断字符串是否是乱码
     * @param strName 字符串
     * @return 是否是乱码
     *//*

    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
    }

    //============================【 规则二   过滤注册号（reg_number）】================================
    */
/**
     * 过滤注册号
     * @param reg_number
     * @return
     *//*

    public static String filter_Reg_Number(String reg_number){
        if(StringUtils.isBlank(reg_number)){
            return "";
        }
        //全角数字转半角
        reg_number = ToDBC(reg_number);
        //字段全为汉字则置为空值，存在 (1999) 或  * 或  - 或 ? 或全是0的置空
        if (isAllChinese(reg_number) || reg_number.contains("(1999)") || reg_number.contains("*") || reg_number.contains("-") || reg_number.contains("?") || isAllZero(reg_number)){
            reg_number = "";
        }
        return reg_number;
    }

    */
/**
     * 判断字符串是否全为中文
     * @param str
     * @return
     *//*

    public static boolean isAllChinese(String str){
        if(str!=null && !"".equals(str)){
            char[] ch = str.trim().toCharArray();
            for (int i = 0; i < ch.length; i++) {
                char c = ch[i];
                if (!isChinese(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    */
/**
     * 判断字符串全部为0
     * @param str
     * @return
     *//*

    public static boolean isAllZero(String str){
        boolean bl = false;
        char[] ch = str.trim().toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (c == '0') {
                bl = true;
                continue;
            }else{
                bl = false;
                break;
            }
        }
        return bl;
    }

    //============================【 规则三   过滤法定代表人姓名（legal_person_name）】================================
    */
/**
     * 过滤法定代表人姓名
     * @param str
     * @return
     *//*

    public static String filter_Legal_Person_Name(String str){
        if( StringUtils.isBlank(str) ){
            return "";
        }
        //英文字母统一为英文半角
        str = ToDBC(str);
        //过滤前后两端特殊字符
        str = special_String_Filter(str);
        return str;
    }

    */
/**
     * 过滤字符串前后两端特殊字符（特殊字符库为自己定义，需确认是否正确）
     * @param str
     * @return
     *//*

    public static String special_String_Filter(String str) {
        // 所有特殊字符(自己定的特殊字符)
        */
/*String[] regex = {"·", "~", "！", "@", "#", "￥", "%", "……", "&", "*", "（", "）", "-", "——", "=", "+", "【", "{", "】", "}", "、", "|", "；", "：",
                "’", "“", "”", "，", "《", "。", "》", "、", "？", "!", "~", "$", "%", "^", "&", "(", ")", "_", "[", "]", ":", ";", "'", "\"", "<", ",", ".", ">", "/", "?", "/"};*//*

        String regex = "·~！@#￥%……&*（）-——=+【{】}、|；：\",’“”，《。》、？!~$%^&()_[]:;'<,.>/?/";
        char[] ch = str.trim().toCharArray();
        int begin = 0;
        int end = 0;
        StringBuffer sb = null;
        outerloop1:
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if(!regex.toString().contains(c+"")){
                begin = i;
                break ;
            }
        }
        sb = new StringBuffer(str.substring(begin)).reverse();
        char[] ch1 = sb.toString().toCharArray();
        for (int i = 0; i < ch1.length; i++) {
            char c = ch1[i];
            if(!regex.toString().contains(c+"")){
                end = i;
                break ;
            }
        }
        sb = new StringBuffer(sb.substring(end)).reverse();
        return sb.toString().trim();
    }

    //============================【 规则四   过滤企业性质（company_org_type）】================================

    */
/**
     * 过滤企业性质 （文字转数字）
     *  1》 包含“个体”的返回个体工商户对应的code值
     *  2》 可以从子表中查到信息的则返回
     *  3》 没有匹配信息则输出原信息
     * @param str
     * @return
     *//*

    public static String filter_Company_Org_Type(String str,Map map){
        if( str == null || "".equals(str) || "null".equalsIgnoreCase(str)){
            return "";
        }
        //包含“个体”的返回个体工商户对应的code值
        if(str.contains("个体")){
            str = map.get("个体工商户").toString().trim();
        }
        //可以从子表中查到信息的则返回对应的code值
        if(map.get(str.trim().replace("（","(").replace("）",")"))!=null){
            str = map.get(str.trim().replace("（","(").replace("）",")")).toString().trim();
        }
        return str;
    }

    */
/**
     * 【判断】：在子表中关联不到信息，则输出新的目录
     * @param str
     * @param map
     * @return
     *//*

    public static boolean filter_Company_Org_Type_NoSearch(String str,Map map){
        boolean bl = false;
        //字段不为空，且在子表中查不到信息，则输出到新目录
        if (null != str && !"".equals(str) && !"null".equals(str) && !"Null".equals(str) && 0!=str.length() && map.get(str.trim())==null ){
            bl = true;
        }
        return bl;
    }

    //============================【 规则五   过滤登记机关（reg_institute）】================================
    */
/**
     * 过滤登记机关 （数字转文字）
     *  1》 可以从子表中查到信息的则返回
     *  2》 没有匹配信息则输出原信息到新目录
     * @param str
     * @return
     *//*

    public static String filter_Reg_Institute(String str,Map map){
        if(StringUtils.isBlank(str)){
            return "";
        }
        String reg = "[0-9]*";
        //字符串不为空，并且是纯数字
        if(str!=null && !"".equals(str) && str.matches(reg)){
            //可以从子表中查到信息的则返回
            str = map.get(str.trim()) != null ? map.get(str).toString().trim():str;
        }
        return str;
    }

    */
/**
     * 【判断】：在子表中关联不到信息，则输出新目录
     * @param str
     * @return
     *//*

    public static boolean filter_Reg_Institute_NoSearch(String str,Map map){
        boolean bl = false;
        //字段不为空，并且是纯数字，在子表中查找不到，则输出新目录
        String reg = "[0-9]*";
        if(null != str && !"".equals(str) && !"null".equals(str) && !"Null".equals(str) && 0!=str.length() && str.matches(reg) && map.get(str.trim())==null){
           bl = true;
        }
        return bl;
    }


    //============================【 规则六   过滤企业状态（reg_status）】================================
    */
/**
     * 过滤企业状态 （文字转数字）
     *  1》 可以从子表中查到信息的则返回
     *  2》 没有匹配信息则输出原信息
     * @param str
     * @return
     *//*

    public static String filter_Reg_Status(String str,Map map){
        if( StringUtils.isBlank(str)){
            return "";
        }
        //处理字段【注销 （注销日期 2017年8月11日）】,【注销(简易)  500000.0000】,【注销（注销日期2000年7月4日）】包含这种格式的数据归为“注销”
        if(str.contains("注销 （注销日期") || str.contains("注销(简易)") || str.contains("注销（注销日期") || str.contains("注销企业")){
            return map.get("注销").toString().trim();
        }

        //可以从子表中查到信息的则返回
        if(map.get(str)!=null){
            str = map.get(str.trim()).toString().trim();
        }

        return str;
    }

    */
/**
     * 【判断】：在子表中关联不到信息，则输出新目录
     * @param str
     * @return
     *//*

    public static boolean filter_Reg_Status_NoSearch(String str,Map map){
        boolean bl = false;
        //不可以从子表中查到信息的则输出新目录
        if(null != str && !"".equals(str) && !"null".equals(str) && !"Null".equals(str) && 0!=str.length() &&  map.get(str)==null){
            bl = true;
        }
        return bl;
    }


    //============================【 规则七   过滤注册资金（reg_capital）】================================

    */
/**
     * 过滤注册资金
     *  1》拆分为数字+币种，如果是万元转换为元（数字乘10000）并且币种需要与字典表对应
     *  2》没有币种的，只要企业性质不是外资及港澳台投资企业，默认为人民币
     *
     * @param str
     * @return
     *//*

    // 过滤注册资金
    public static String filter_Reg_Capital(String str,List<String> list,Map<String,String> map,Company company){
        //滤空
        if(StringUtils.isBlank(str) || "null".equalsIgnoreCase(str) ){
            return "";
        }
        // “万元”为单位
        if(str.contains("万")) {
            //TODO:单独判断带两个小数点的字符串，后续再修改（已经修改）
//            str = "5.0000(.万元)".equals(str) ? "5.0000(万元)" : str.trim();
            BigDecimal bd_10000 = new BigDecimal("10000");
            BigDecimal bd_final = null;
            for (int i = 0; i < list.size(); i++) {
                //可以关联到币种（币种不为空），输出关联的币种
                if (str.contains(list.get(i))) {
//                    double money = extracting_Amount(str);//不能处理数据中包含两个小数点的数据，例如：5.0000(.万元)
                    String money = extracting_Amount2(str);
                    BigDecimal bd = new BigDecimal(money);//格式化金额
                    bd_final = bd.multiply(bd_10000);//金额 * 10000
                    String code = map.get(list.get(i));//获取币种code值
                    if (("0").equals(bd_final.toString()) || ("0.0").equals(bd_final.toString())) {
                        return "";
                    } else {
                        str = bd_final.setScale(4, BigDecimal.ROUND_HALF_UP).toPlainString() + "\t" + code;
                        return str;
                    }
                }
            }
            for (int i = 0; i < list.size(); i++) {
                //不可以关联到币种（币种为空），且企业性质不是外资及港澳台投资，默认为人民币
                if (!str.contains(list.get(i)) && !isStartWithNumber("1152")) {
//                    double money = extracting_Amount(str);//不能处理数据中包含两个小数点的数据，例如：5.0000(.万元)
                    String money = extracting_Amount2(str);
                    BigDecimal bd = new BigDecimal(money);//格式化金额
                    bd_final = bd.multiply(bd_10000);//金额 * 10000
                    if(!("0.0").equals(bd_final.toString()) && !("0").equals(bd_final.toString())){
                        str = bd_final.setScale(4, BigDecimal.ROUND_HALF_UP).toPlainString() + "\t" + "CNY";
                        return str;
                    }else{
                        return "";
                    }
                }
            }
        }

        // “元”为单位
        if(str!=null && !str.equals("") && !str.contains("万")) {
            for (int i = 0; i < list.size(); i++) {
                //可以关联到币种（币种不为空），输出关联的币种
                if (str.contains(list.get(i))) {
//                    double money = extracting_Amount(str);//不能处理数据中包含两个小数点的数据，例如：5.0000(.万元)
                    String money = extracting_Amount2(str);
                    BigDecimal bd = new BigDecimal(money);//格式化金额
                    String code = map.get(list.get(i));//获取币种code值
                    if (("0").equals(bd.toString()) || ("0.0").equals(bd.toString())) {
                        return "";
                    } else {
                        str = bd.setScale(4, BigDecimal.ROUND_HALF_UP).toPlainString() + "\t" + code;
                        return str;
                    }
                }
            }
            for (int i = 0; i < list.size(); i++) {
                //不可以关联到币种（币种为空），且企业性质不是外资及港澳台投资，默认为人民币
                if (!str.contains(list.get(i)) && !isStartWithNumber("1152")) {
//                    double money = extracting_Amount(str);//不能处理数据中包含两个小数点的数据，例如：5.0000(.万元)
                    String money = extracting_Amount2(str);
                    BigDecimal bd = new BigDecimal(money);
                    if (!("0.0").equals(bd.toString()) && ("0").equals(bd.toString())) {
                        str = bd.setScale(4, BigDecimal.ROUND_HALF_UP).toPlainString() + "\t" + "CNY";
                        return str;
                    } else {
                        return "";
                    }
                }
            }
        }
        return str;
    }

    //判断字符串是不是以5或者6数字开头
    public static boolean isStartWithNumber(String str) {
        Pattern pattern = Pattern.compile("[5|6]*");
        if(str!=null && !str.equals("")){
            Matcher isNum = pattern.matcher(str.charAt(0)+"");
            if (!isNum.matches()) {
                return false;
            }
        }
        return true;
    }

    //提取字符串中的金额1
    public static double extracting_Amount(String str){
        StringBuffer sb = new StringBuffer();
        char[] lineStr = str.toCharArray();
        for (int i = 0; i < lineStr.length; i++)
        {
            if (("0123456789.").indexOf(lineStr[i] + "") != -1)
            {
                sb.append(lineStr[i]);
            }
        }
        return Double.parseDouble("".equals(sb.toString())?"0.0000":sb.toString());
    }

    //提取字符串中的金额2（因为数据中包含两个小数点，所以单独写出一个方法处理，数据示例：5.0000(.万元)）
    public static String extracting_Amount2(String str){
        char[] lineStr = str.toCharArray();
        int start = 8888;
        int end = 9999;
        for (int i = 0; i < lineStr.length; i++) {
            if (("0123456789.").indexOf(lineStr[i] + "") != -1) {
                start = i;
                break;
            }
        }
        if(start != 8888) {
            for (int i = start; i < lineStr.length; i++) {
                if (("0123456789.").indexOf(lineStr[i] + "") == -1) {
                    end = i;
                    break;
                }
            }
            if(end == 9999){
                end = str.length();
            }
        }
        if(start!=8888 && end!=9999){
            str = str.substring(start,end);
            if(".".equals(str)){
                return "0.0";
            }
        }else{
            return "0.0";
        }
        return StringUtils.isBlank(str)?"0.0":str;
    }


    //============================【 规则九   过滤组织机构代码（org_number）】================================

    */
/**
     * 过滤组织机构代码
     *  1》纯数字，长度为9，包含“-”则替换后输出，如果替换后长度不等于9则将原数据输出
     * @param str
     * @return
     *//*

    public static String filter_Org_Number(String str){
        if(StringUtils.isBlank(str)){
            return "";
        }
        String strOld = str;//记录修改之前的字段
        String strNew = str.replaceAll("-","");
        String reg = "[0-9]*";
        //判断替换“-”后长度为9并且为全数字则输出替换“-”的字符串，否则输出替换前的数据
        if(strNew.length()==9 && strNew.matches(reg)){
            return strNew;
        }else{
            return strOld;
        }
    }

    */
/**
     * 【判断】不符合过滤条件，则输出新目录
     *  过滤条件：去掉“-”长度不等于9的非纯数字输出新目录
     * @param str
     * @return
     *//*

    public static boolean filter_Org_Number_NoSearch(String str){
        boolean bl = false;

        if(null != str && !"".equals(str) && !"null".equals(str) && !"Null".equals(str) && 0!=str.length() && str.contains("-")){
            String strNew = str.replaceAll("-","");
            String reg = "[0-9]*";
            if(strNew.length()!=9 || !strNew.matches(reg)){
                bl = true;
            }
        }
        return bl;
    }


    //============================【 规则十   过滤组织机构批准单位（org_approved_institute）】================================

    */
/**
     * TODO:这个字段没有特别的处理，如果按照静姐的文档需求，则要核实后再做修改，在本程序中没有对这个操作
     * 过滤组织机构批准单位
     *  1》纯汉字，如果出现有数字串或者不是纯汉字的，则输出新目录，否则正常输出
     * @param str
     * @return
     *//*

    public static boolean filter_Org_Approved_Institute_NoSearch(String str){
        boolean bl = false;
        if(StringUtils.isNotBlank(str) && !"null".equalsIgnoreCase(str) && !isAllChinese(str.trim())){
            bl = true;
        }
        return bl;
    }

    //============================【 规则十一   过滤社会信用代码（property1）】================================


    */
/**
     * 过滤社会信用代码
     *  1》数字+字母/数字/字母，并且固定长度18  否则置空
     * @param str
     * @return
     *//*

    public static String filter_Property1(String str){
        if(StringUtils.isBlank(str)){
            return "";
        }
        String reg = "^[0-9A-Za-z]{18}$";
        //数字+字母并且固定长度18  否则置空
        if(str!=null && !"".equals(str) && str.matches(reg) && str.length()==18){
            return str;
        }else{
            return "";
        }
    }

    //============================【 规则十二   过滤税号（property4）】================================

    */
/**
     * 过滤税号
     *  1》数字+字母并且固定长度15  否则置空
     * @param str
     * @return
     *//*

    public static String filter_Property4(String str){
        if(StringUtils.isBlank(str)){
            return "";
        }
        String reg = "[0-9]*";
        if(str.matches(reg) && str.length()==15){
            return str;
        }else{
            return "";
        }
    }


    //============================【 规则十四   过滤上级机构ID（parent_id）】================================

    */
/**
     * 过滤上级机构ID
     *  1》如果字段出现0或者null则置空，否则返回正常输出
     * @param str
     * @return
     *//*

    public static String filter_Parent_Id(String str){
        if(null == str || "0".equals(str)){
            return "";
        }else{
            return str;
        }
    }


}
*/
