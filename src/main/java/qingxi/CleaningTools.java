package qingxi;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by johnny on 2018/12/28
 * 清洗规则
 */
public class CleaningTools {
    private static Pattern NUMBER_PATTERN = Pattern.compile("[1-9]");
    private static final Logger logger = LoggerFactory.getLogger(CleaningTools.class);

    public void zhixinginfo(List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Iterator<Map<String, Object>> iterator = list.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> map = iterator.next();
            if (map != null && !map.isEmpty()) {
                String execMoney = Utils.toString(map.get("execMoney"));
                if (!Utils.isBlankEmpty(execMoney) && execMoney.equalsIgnoreCase("——")) {
                    map.remove("execMoney");
                }
            }
        }
    }

    public void companyAbnormalInfo(List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Iterator<Map<String, Object>> iterator = list.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> map = iterator.next();
            if (map != null && !map.isEmpty()) {
                String key = "put_reason";
                String value = Utils.toString(map.get(key));
                if (Utils.isRegex(value, "^(—|-|\\s)*$")) {
                    map.put(key, "");
                }
                key = "remove_reason";
                value = Utils.toString(map.get(key));
                if (Utils.isRegex(value, "^(—|-|\\s)*$")) {
                    map.put(key, "");
                }
                key = "put_department";
                value = Utils.toString(map.get(key));
                if (Utils.isRegex(value, "^(—|-|\\s)*$")) {
                    map.put(key, "");
                }
                key = "remove_department";
                value = Utils.toString(map.get(key));
                if (Utils.isRegex(value, "^(—|-|\\s)*$")) {
                    map.put(key, "");
                }
            }
        }
    }

    public void entGuaranteeInfo(List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Iterator<Map<String, Object>> iterator = list.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> map = iterator.next();
            if (map != null && !map.isEmpty()) {
                String execMoney = Utils.toString(map.get("credito_amount"));
                if (!Utils.isBlankEmpty(execMoney)) {
                    String sCreditoAmount1 = execMoney.replace("万元", "");
                    BigDecimal bigDecimal = new BigDecimal(sCreditoAmount1);
                    BigDecimal bigDecimal1 = bigDecimal.multiply(new BigDecimal("10000"));
                    map.put("credito_amount", bigDecimal1);
                }
            }
        }
    }

    //性质处罚
    public void administrativePunishment(List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Iterator<Map<String, Object>> iterator = list.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> map = iterator.next();
            if (map != null && !map.isEmpty()) {
                //将s_type、s_content字段数据,<>的内容清洗掉，保留<>之外的内容,去出— —等
                //获取字段sType的值
                String sType = Utils.toString(map.get("sType"));
                if (sType.equals("— —") || sType.equals("/") || sType.equals("空") || sType.equals("null") || sType.equals("未获得") || sType.equals("未获知") || Utils.isNumeric(sType)) {
                    map.put("sType", "");
                }
                //获取字段sContent的值
                String sContent = Utils.toString(map.get("sContent"));
                if (sContent.equals("— —") || sContent.equals("/")) {
                    map.put("sContent", "");
                } else if (sContent.contains("&nbsp;")) {//将"&nbsp;"替换为""
                    String sContent2 = sContent.replaceAll("&nbsp;", "");
                    //使用正则表达式将<>及其中的内容替换为空
                    String newSContent = sContent2.replaceAll("(<[^>]*>)", "");
                    map.put("sContent", newSContent);
                } else if (sContent.contains("<") && !sContent.contains("&nbsp;")) {
                    String newSContent = sContent.replaceAll("(<[^>]*>)", "");
                    map.put("sContent", newSContent);
                }
            }
        }
    }

    /**
     * 过滤上级机构ID
     * 1》如果字段出现0或者null则置空，否则返回正常输出
     *
     * @param str
     * @return
     */
    public static String filter_Parent_Id(String str) {
        if (null == str || "0".equals(str)) {
            return "";
        } else {
            return str;
        }
    }

    //财务以及相关清洗
    public String annualReport(List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        // TODO: 天眼查年报
        // phone_number 企业联系电话 （仅处理最新一年的年报数据）

        // postcode 邮政编码 这个是用我们字典表的吗？还是用清洗后的？（仅处理最新一年的年报数据）
        //    1、	值为000000—清空

        // postal_address 企业通信地址（仅处理最新一年的年报数据），如果地址=名称，清空；
        // 去掉开头的 " ' (分公司) * + , ----------- . com . / 0 N/A 。 无  以及其它由数字、字母组成的字符串、日期、邮箱、网址等

        // email 电子邮箱（仅处理最新一年的年报数据） 清空以下：无   @之前的内容全部是0   @之前的内容为“无”


        // employee_num 从业人数（需要处理历年的年报数据）把值为0的数据删除


        // 财务数据（需要处理历年的年报数据）

        for (Map<String, Object> map : list) {
            handleRegCapital(map, "total_assets", "annual_report");
            handleRegCapital(map, "total_equity", "annual_report");///tianyancha1.annual_report.total_equity股东权益合计
            handleRegCapital(map, "total_sales", "annual_report");
            handleRegCapital(map, "total_profit", "annual_report");
            handleRegCapital(map, "prime_bus_profit", "annual_report");
            handleRegCapital(map, "retained_profit", "annual_report");
            handleRegCapital(map, "total_tax", "annual_report");
            handleRegCapital(map, "total_liability", "annual_report");
            handleRegCapital(map, "employee_num", "annual_report");
        }

        if (list.size() > 0) {
            //将list集合中的数据按report_year年份降序排列
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    String year1 = Utils.toString(o1.get("report_year"));
                    String year2 = Utils.toString(o2.get("report_year"));
                    return year2.compareTo(year1);
                }
            });
            return Utils.toString(list.get(0).get("id"));
        }
        return null;
    }

    /*reg_capital 注册资金
    1、 把值拆分为数字+币种，如果单位为万，数字要乘10000，币种要与我们的币种字典表对应
    2、 没有币种的，只要企业性质不是外资及港澳台投资企业，默认为人民币*/
    private void handleRegCapital(Map<String, Object> map, String keyName, String tableName) {
        if ("company".equalsIgnoreCase(tableName)) {
            handleCompanyRegInfo(map, keyName, tableName);
        }
        String value = Utils.toString(map.get(keyName));
        if (Utils.isBlankEmpty(value)) {
            return;
        }
        String multiply = "1";
        String newValue = value;
        newValue = Utils.full2Half(newValue);
        newValue = newValue.replaceAll("(\\s)+", ""); // 去除空白字符
        if (newValue.contains("不公示")) {
            map.put(keyName, null);
            return;
        }
        if (newValue.contains("人")) {
            map.put(keyName, newValue.replaceAll("人", ""));
            return;
        }
        if (newValue.contains("万")) {
            multiply = "10000";
        }
        newValue = newValue.replaceAll("万元", "").replaceAll("万", "");
        // 1、	把值拆分为数字+币种，如果单位为万，数字要乘10000，币种要与我们的币种字典表对应
        // 2、	没有币种的，只要企业性质不是外资及港澳台投资企业，默认为人民币
        String valueMoney = Utils.extractStr(newValue, "(\\d+\\.\\d+)|(\\d+)"); // 获取数字
        valueMoney = Utils.multiply(valueMoney, multiply);
        map.put(keyName, valueMoney);
        // 拆分币种  _capcur
        String valueCapcur = Utils.extractStr(newValue, "([\\u4e00-\\u9fa5]+)"); // 获取币种
        if (Utils.isBlankEmpty(valueCapcur)) {
            // TODO: 没有币种的，只要企业性质不是外资及港澳台投资企业 5和6开头吗？，默认为人民币
            valueCapcur = "人民币";
        }
        if ("人民币元".equalsIgnoreCase(valueCapcur)) {
            valueCapcur = "人民币";
        }
        if ("元人民币".equalsIgnoreCase(valueCapcur)) {
            valueCapcur = "人民币";
        }
        Map<String, Object> param = new HashMap<>();
        param.put("name_zh", valueCapcur);
        Map<String, Object> dicCurrency = DictionaryQuery.QueryDicCurrency(param);
        if (dicCurrency != null) {
            valueCapcur = Utils.toString(dicCurrency.get("code"));
        } else {
            logger.warn("字典库问题，源数据表[{}]列[{}]值[{}]数据id[{}]，企业币种表[{}]为[{}] 的记录没找到", tableName, keyName, value, getDataId(map), "DicCurrency", valueCapcur);
            valueCapcur = "";
        }
        map.put(keyName + "_capcur", valueCapcur);
    }

    /**
     * 公司注册信息清洗
     *
     * @author johnny
     */
    private void handleCompanyRegInfo(Map<String, Object> map, String keyName, String tableName) {
        String value = Utils.toString(map.get(keyName));
        if (Utils.isBlankEmpty(value)) {
            return;
        }
        String newValueT = null;
        String newValue = value;
        if (!Utils.isBlankEmpty(newValue)) {
            newValue = getWashValue(newValue);
            if ("reg_capital".equalsIgnoreCase(keyName) && "company".equalsIgnoreCase(tableName)) {
                if (newValue == null) {
                    map.put("reg_capital_capcur", null);
                } else {
                    newValueT = newValue;
                }
            }
            map.put(keyName, newValueT);
        }
    }

    private String getWashValue(Object str) {
        String newvalue = Utils.full2Half(Utils.toString(str).trim());
        if (newvalue.contains("，")) {
            newvalue = newvalue.replace("，", "");
        }
        if (newvalue.contains(",")) {
            newvalue = newvalue.replace(",", "");
        }
        Matcher m = NUMBER_PATTERN.matcher(newvalue);
        if (!m.find()) {
            return null;
        }
        return newvalue;
    }

    private String getDataId(Map<String, Object> dataMap) {
        if (dataMap != null && !dataMap.isEmpty()) {
            String id = Utils.toString(dataMap.get("id"));
            if (Utils.isBlankEmpty(id)) {
                id = Utils.toString(dataMap.get("company_id"));
            }
            if (Utils.isBlankEmpty(id)) {
                id = Utils.toString(dataMap.get("annual_report_id"));
            }
            if (Utils.isBlankEmpty(id)) {
                id = Utils.toString(dataMap.get("annualreport_id"));
            }
            return id;
        }
        return null;
    }

    //清洗企业名字
    public String handleEntName(String name) {

        // 去除开头或者结尾的 任何空白字符或者其他特殊符号
        String newName = name.replaceAll("(^(\\s|\\*|\\.|\\d|\\$|&|\\+|-|_|#|!|！|@|、|\")+)|((\\s|\\*|\\.|\\d|\\$|&|\\+|-|_|#|!|！|@|、|\")+$)", "");
        // 去除前后空格
        newName = newName.trim();
        // 全角转半角
        newName = Utils.full2Half(newName);

        newName = newName.replaceAll("（", "(").replaceAll("）", ")");
        newName = newName.replace("<<", "《").replace(">>", "》");
        newName = newName.replaceAll("<", "").replaceAll(">", "");

        // (已出质至2010.11.5）、(冻结至2008.6.17)、(具体股东请查批文深府办复[1992]120号)、(原1312号)
        newName = newName.replaceAll("(\\(冻结至(\\d|\\.|\\[|\\]|[\\u4e00-\\u9fa5])+\\))|(\\(已出质至(\\d|\\.|\\[|\\]|[\\u4e00-\\u9fa5])+\\))|(\\(具体股东请查(\\d|\\.|\\[|\\]|[\\u4e00-\\u9fa5])+\\))|(\\(原(\\d|\\.|\\[|\\]|[\\u4e00-\\u9fa5])+\\))", "");
        // 不是英文字母之间的空格
        newName = newName.replaceAll("(?<![a-zA-Z\\s])((\\s)+)(?![a-zA-Z\\s])", "");
        return newName;
    }

    //清洗注册号
    public String handleRegNumber(String regNumber) {
        String newValue = regNumber;
        String regex = "(.*(\\(1999\\)|\\*|\\+|-|_|\\?).*)|([0]+)";
        // 1、全角的数字转为半角，如５３２７００４０００００２９２
        newValue = Utils.full2Half(newValue);
        // 2、纯汉字的置空
        if (Utils.isCN(newValue)) {
            return "";
        } else if ("null".equalsIgnoreCase(newValue)) {
            return "";
        } else if (Utils.isRegex(newValue, regex)) {
            // 3、	存在 (1999) 或  * 或  - 或 ? 或  全是0   的置空
            return "";
        } else {
            return newValue;
        }
    }


    //清洗法定代表人
    private String handleLegalPersonName(String value) {

        String newValue = value;
        // 1、	英文字母统一为英文半角
        newValue = Utils.full2Half(newValue);
        // 去除开头或者结尾的 任何空白字符或者其他特殊符号
        newValue = newValue.replaceAll("(^(\\s|\\*|\\.|\\d|\\$|&|\\+|-|_|#|!|！|@|、|\")+)|((\\s|\\*|\\.|\\d|\\$|&|\\+|-|_|#|!|！|@|、|\")+$)", "");
        newValue = newValue.trim();
        return newValue;
    }

    //清洗capital 认缴金额
    private void handle_capital(Map<String, Object> map, String prop) {
        try {
            String s = Utils.toString(map.get(prop));
            if (!Utils.isBlankEmpty(s)) {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                if (jsonObject.has("amomon")) {
                    String value = Utils.toString(jsonObject.get("amomon"));
                    value = Utils.multiply(value, "10000");
                    map.put(prop, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //1、	要注意变更日期、变更项目任意一项是空的，删除该记录；
    //2、	当变更前、变更后任意一项为空，但其他三项不为空，则保留；反之删除。
    //3、	当变更前、变更后任意一项为空，但不为空的一项值为***，则整条记录删除。
    //4、	当变更前、变更后两项值为***，则整条记录删除。
    //5、	当变更前、变更后值=“,;”，清空
    private void handle_company_change_info(List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Iterator<Map<String, Object>> iterator = list.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> map = iterator.next();
            if (map != null && !map.isEmpty()) {
                String change_item = Utils.toString(map.get("change_item"));
                String content_before = Utils.toString(map.get("content_before"));
                String content_after = Utils.toString(map.get("content_after"));
                String change_time = Utils.toString(map.get("change_time"));
                if (Utils.isBlankEmpty(change_item) || Utils.isBlankEmpty(change_time)
                        || (Utils.isBlankEmpty(content_after) && Utils.isBlankEmpty(content_before))) {
                    iterator.remove();
                    continue;
                }
                String regex = "[\\*|\\s]+";  // 值只有 *
                if ((Utils.isBlankEmpty(content_after) && Utils.isRegex(content_before, regex))
                        || (Utils.isBlankEmpty(content_before) && Utils.isRegex(content_after, regex))) {
                    iterator.remove();
                    continue;
                }
                if (Utils.isRegex(content_before, regex) && Utils.isRegex(content_after, regex)) {
                    iterator.remove();
                    continue;
                }
                change_item = Utils.full2Half(change_item).trim();
                map.put("change_item", change_item);
            }
        }
    }

    //清洗股东出资比例ownership_stake
    public String handleSOwnershipStake(String sOwnershipStake, String dAmount, Double dRegCap) {
        if (!Utils.isBlankEmpty(sOwnershipStake)) {
            sOwnershipStake = sOwnershipStake.replace("%", "").replace("〈", "");
        }
        if (sOwnershipStake != null) {
            if (Utils.toDouble(sOwnershipStake) > 100 || Utils.toDouble(sOwnershipStake) <= 0.01) {
                sOwnershipStake = null;
            }
        }

        if (Utils.isBlankEmpty(sOwnershipStake) && !Utils.isBlankEmpty(dAmount) && dRegCap != null) {
            Double start = Double.parseDouble(dAmount) / dRegCap * 100;
            sOwnershipStake = String.valueOf(start);
            if (!Utils.isBlankEmpty(sOwnershipStake)) {
                BigDecimal bigDecimal = new BigDecimal(sOwnershipStake);
                sOwnershipStake = String.valueOf(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            }
        }
        if (StringUtils.isNotBlank(sOwnershipStake) && Utils.toDouble(sOwnershipStake) <= 0.01) {
            sOwnershipStake = null;
        }
        return sOwnershipStake;
    }

    /**
     * 过滤组织机构代码
     * 1》纯数字，长度为9，包含“-”则替换后输出，如果替换后长度不等于9则将原数据输出
     *
     * @param str
     * @return
     */
    public static String filter_Org_Number(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        String strOld = str;//记录修改之前的字段
        String strNew = str.replaceAll("-", "");
        String reg = "[0-9]*";
        //判断替换“-”后长度为9并且为全数字则输出替换“-”的字符串，否则输出替换前的数据
        if (strNew.length() == 9 && strNew.matches(reg)) {
            return strNew;
        } else {
            return strOld;
        }
    }

    /**
     * 过滤社会信用代码
     * 1》数字+字母/数字/字母，并且固定长度18  否则置空
     *
     * @param str
     * @return
     */
    public static String filter_Property1(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        String reg = "^[0-9A-Za-z]{18}$";
        //数字+字母并且固定长度18  否则置空
        if (str != null && !"".equals(str) && str.matches(reg) && str.length() == 18) {
            return str;
        } else {
            return "";
        }
    }

    /**
     * 过滤税号
     * 1》数字+字母并且固定长度15  否则置空
     *
     * @param str
     * @return
     */
    public static String filter_Property4(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        String reg = "[0-9]*";
        if (str.matches(reg) && str.length() == 15) {
            return str;
        } else {
            return "";
        }
    }

    //category_code 行业代码——第四位补零，做到与我们行业代码字典表对应
    public static String category_code(String str) {
        if (!Utils.isBlankEmpty(str)) {
            str = Utils.convertStrLength(str, 4, "0", false);
        }
        return str;
    }

    //从源数据company_bid表中的content字段中截取中标人/中标单位的方法
    public String getWinningBidder(String value) {
        String winningBidder = null;
        if (value.toString().contains("中标人：") && value.toString().contains("中标人地址：")) {
            String value1 = null;
            String value2 = null;
            String value3 = null;
            if (value.contains("<")) {//将value中<>中的内容清空
                value1 = value.replaceAll("(<[^>]*>)", "");
            } else {
                value1 = value;
            }
            if (value1.contains("　　")) {
                value2 = value1.replace("　　", "");
            } else {
                value2 = value1;
            }
            if (value2.contains("\n")) {//&nbsp
                value3 = value2.replace("\n", "");
            } else {
                value3 = value2;
            }
            //先将value清洗一下，在进行截取“中标人”后的中标单位
            if (value3.contains("中标人：") && value3.contains("中标人地址：")) {
                int indexOf1 = value3.indexOf("中标人：");
                int indexOf2 = value3.indexOf("中标人地址：");
                winningBidder = value.substring(indexOf1 + 4, indexOf2);
            } /*else if (value3.contains("中标人：") && value3.contains("中标价：")) {
            int indexOf1 = value3.indexOf("中标人：");
            int indexOf2 = value3.indexOf("中标价：");
            winningBidder = value3.substring(indexOf1 + 4, indexOf2);
        } else if (value3.contains("中标单位：") && value3.contains("中标单位地址：")) {
            int indexOf1 = value3.indexOf("中标单位：");
            int indexOf2 = value3.indexOf("中标单位地址：");
            winningBidder = value3.substring(indexOf1 + 5, indexOf2);
        } else if (value3.contains("中标人：") && value3.contains("中标报价：")) {
            int indexOf1 = value3.indexOf("中标人：");
            int indexOf2 = value3.indexOf("中标报价：");
            winningBidder = value3.substring(indexOf1 + 4, indexOf2);
        } else if (value3.contains("中标商：") && value3.contains("中标商地址：")) {
            int indexOf1 = value3.indexOf("中标商：");
            int indexOf2 = value3.indexOf("中标商地址：");
            winningBidder = value3.substring(indexOf1 + 4, indexOf2);
        }*/
            winningBidder = winningBidder.trim();
        }
        return winningBidder;
    }

    //清洗税号带有*号和引号的数据
    public static String getsTaxNo(String str) {
        if (str.startsWith("\"") && str.endsWith("\"")) {
            str = str.replaceAll("\"", "");
        }
        if (str.startsWith("“") && str.endsWith("”")) {
            str = str.replaceAll("“", "");
            str = str.replaceAll("”", "");
        }
        if (str.contains("*")) {
            str = "";
        }
        return str;
    }

    /*清洗index_data的country字段
     * 清洗规则：company（过滤一个字段）
     *  1》字段值等于“http://qyxy.baic.gov.cn/”  返回“CN” => 中国
     *  2》字段值以“hk_”开头，返回“HK” => 中国香港
     *  3》字段值以“tw_”开头，返回“TW” => 中国台湾*/
    public String country(String source_flag) {
        if (StringUtils.isBlank(source_flag)) {
            return "";
        }
        if (source_flag.equals("http://qyxy.baic.gov.cn/")) {
            source_flag = "CN";
        } else if (source_flag.startsWith("hk_")) {
            source_flag = "HK";
        } else if (source_flag.startsWith("tw_")) {
            source_flag = "TW";
        }
        return source_flag;
    }

    //staff_type_name 任职类型名称——与我们的职务字典表对应
    public Integer evaluate(String position, String source) {
        Integer code = null;
        try {
            if (Utils.isBlankEmpty(position)) {
                return null;
            }
            position = position.trim();
            code = (Integer) positionMap.get(position);
            if (code == null) {
                switch (source) {
                    case "gsinfo_company":
                        code = (Integer) positionZsMap.get(position);
                        break;
                    case "juyuan":
                        code = (Integer) positionHsMap.get(position);
                        break;
                    case "ashare":
                    case "cbond":
                    case "hk":
                    case "neeqs":
                    case "unlisted":
                        code = (Integer) positionWindMap.get(position);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }


    public static final Map<String, Integer> positionMap = new ConcurrentHashMap();
    public static final Map<String, Integer> positionHsMap = new ConcurrentHashMap();
    public static final Map<String, Integer> positionWindMap = new ConcurrentHashMap();
    public static final Map<String, Integer> positionZsMap = new ConcurrentHashMap();

    static {
        positionMap.put("法定代表人", Integer.valueOf(1));
        positionMap.put("董事长", Integer.valueOf(2));
        positionMap.put("副董事长", Integer.valueOf(4));
        positionMap.put("总裁", Integer.valueOf(5));
        positionMap.put("副总裁", Integer.valueOf(6));
        positionMap.put("总经理", Integer.valueOf(7));
        positionMap.put("副总经理", Integer.valueOf(9));
        positionMap.put("董事长兼总经理", Integer.valueOf(10));
        positionMap.put("副董事长兼总经理", Integer.valueOf(11));
        positionMap.put("董事兼总经理", Integer.valueOf(12));
        positionMap.put("执行董事", Integer.valueOf(13));
        positionMap.put("执行董事兼总经理", Integer.valueOf(15));
        positionMap.put("执行董事兼经理", Integer.valueOf(17));
        positionMap.put("董事会秘书", Integer.valueOf(19));
        positionMap.put("党委书记", Integer.valueOf(21));
        positionMap.put("总工程师", Integer.valueOf(23));
        positionMap.put("总会计师", Integer.valueOf(27));
        positionMap.put("财务总监", Integer.valueOf(29));
        positionMap.put("负责人", Integer.valueOf(32));
        positionMap.put("厂长", Integer.valueOf(33));
        positionMap.put("局长", Integer.valueOf(34));
        positionMap.put("院长", Integer.valueOf(35));
        positionMap.put("行长", Integer.valueOf(36));
        positionMap.put("所长", Integer.valueOf(37));
        positionMap.put("台长", Integer.valueOf(38));
        positionMap.put("园长", Integer.valueOf(39));
        positionMap.put("校长", Integer.valueOf(40));
        positionMap.put("站长", Integer.valueOf(41));
        positionMap.put("其他", Integer.valueOf(42));
        positionMap.put("经理", Integer.valueOf(43));
        positionMap.put("首席代表", Integer.valueOf(44));
        positionMap.put("一般代表", Integer.valueOf(45));
        positionMap.put("董事", Integer.valueOf(3));
        positionMap.put("独立董事", Integer.valueOf(8));
        positionMap.put("董事会顾问", Integer.valueOf(14));
        positionMap.put("执行董事长", Integer.valueOf(16));
        positionMap.put("非执行董事", Integer.valueOf(18));
        positionMap.put("代理董事长", Integer.valueOf(20));
        positionMap.put("监事会主席", Integer.valueOf(22));
        positionMap.put("监事", Integer.valueOf(24));
        positionMap.put("独立监事", Integer.valueOf(25));
        positionMap.put("职工监事", Integer.valueOf(26));
        positionMap.put("监事召集人", Integer.valueOf(28));
        positionMap.put("监事副主席", Integer.valueOf(30));
        positionMap.put("监事会顾问", Integer.valueOf(31));

        positionHsMap.put("董事长", Integer.valueOf(2));
        positionHsMap.put("董事", Integer.valueOf(3));
        positionHsMap.put("副董事长", Integer.valueOf(4));
        positionHsMap.put("常务副董事长", Integer.valueOf(4));
        positionHsMap.put("总裁", Integer.valueOf(5));
        positionHsMap.put("首席执行官CEO", Integer.valueOf(5));
        positionHsMap.put("副总裁", Integer.valueOf(6));
        positionHsMap.put("常务副总裁", Integer.valueOf(6));
        positionHsMap.put("总经理", Integer.valueOf(7));
        positionHsMap.put("独立董事", Integer.valueOf(8));
        positionHsMap.put("常务副总", Integer.valueOf(9));
        positionHsMap.put("副总经理", Integer.valueOf(9));
        positionHsMap.put("执行董事长", Integer.valueOf(16));
        positionHsMap.put("非执行董事", Integer.valueOf(18));
        positionHsMap.put("董事会秘书", Integer.valueOf(19));
        positionHsMap.put("党委书记", Integer.valueOf(21));
        positionHsMap.put("监事会主席", Integer.valueOf(22));
        positionHsMap.put("总工程师", Integer.valueOf(23));
        positionHsMap.put("监事", Integer.valueOf(24));
        positionHsMap.put("独立监事", Integer.valueOf(25));
        positionHsMap.put("总会计师", Integer.valueOf(27));
        positionHsMap.put("财务总监", Integer.valueOf(29));
        positionHsMap.put("监事副主席", Integer.valueOf(30));
        positionHsMap.put("行长", Integer.valueOf(36));
        positionHsMap.put("其他", Integer.valueOf(42));

        positionWindMap.put("法定代表人", Integer.valueOf(1));
        positionWindMap.put("董事长", Integer.valueOf(2));
        positionWindMap.put("董事", Integer.valueOf(3));
        positionWindMap.put("副董事长", Integer.valueOf(4));
        positionWindMap.put("首席执行官", Integer.valueOf(5));
        positionWindMap.put("副首席执行官", Integer.valueOf(6));
        positionWindMap.put("总经理", Integer.valueOf(7));
        positionWindMap.put("独立董事", Integer.valueOf(8));
        positionWindMap.put("副总经理", Integer.valueOf(9));
        positionWindMap.put("董事会顾问", Integer.valueOf(14));
        positionWindMap.put("董事会秘书", Integer.valueOf(19));
        positionWindMap.put("代董事长", Integer.valueOf(20));
        positionWindMap.put("党委书记", Integer.valueOf(21));
        positionWindMap.put("监事会主席", Integer.valueOf(22));
        positionWindMap.put("监事长", Integer.valueOf(22));
        positionWindMap.put("总工程师", Integer.valueOf(23));
        positionWindMap.put("监事", Integer.valueOf(24));
        positionWindMap.put("独立监事", Integer.valueOf(25));
        positionWindMap.put("职工监事", Integer.valueOf(26));
        positionWindMap.put("总会计师", Integer.valueOf(27));
        positionWindMap.put("监事会召集人", Integer.valueOf(28));
        positionWindMap.put("首席财务官", Integer.valueOf(29));
        positionWindMap.put("首席财务总监", Integer.valueOf(29));

        positionZsMap.put("董事长", Integer.valueOf(2));
        positionZsMap.put("董事", Integer.valueOf(3));
        positionZsMap.put("副董事长", Integer.valueOf(4));
        positionZsMap.put("总经理", Integer.valueOf(7));
        positionZsMap.put("经理", Integer.valueOf(43));
        positionZsMap.put("副总经理", Integer.valueOf(9));
        positionZsMap.put("董事长兼总经理", Integer.valueOf(10));
        positionZsMap.put("董事兼总经理", Integer.valueOf(12));
        positionZsMap.put("执行董事", Integer.valueOf(13));
        positionZsMap.put("执行董事兼总经理", Integer.valueOf(15));
        positionZsMap.put("监事", Integer.valueOf(24));
        positionZsMap.put("首席代表", Integer.valueOf(44));
        positionZsMap.put("一般代表", Integer.valueOf(45));
        positionZsMap.put("负责人", Integer.valueOf(32));
        positionZsMap.put("其他人员", Integer.valueOf(42));
        positionZsMap.put("监事会主席", Integer.valueOf(22));
    }
}
