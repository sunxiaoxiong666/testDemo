package qingxi;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class DictionaryQuery {

    //查询dic_currency
    public static Map<String,Object> QueryDicCurrency(Map<String,Object> param){

        return new HashMap<>();
    }

    /**
     * 过滤登记机关 （数字转文字）
     *  1》 可以从子表中查到信息的则返回
     *  2》 没有匹配信息则输出原信息
     * @param str
     * @return
     */
    public static String filter_Reg_Institute(String str){
        if(StringUtils.isBlank(str)){
            return "";
        }
        String reg = "[0-9]*";
        //字符串不为空，并且是纯数字
        if(str!=null && !"".equals(str) && str.matches(reg)){
            //查询字典表
            str = "通过str查询dic_zcjgZs字典表,得到中文名";
        }
        return str;
    }

    //清洗企业状态
    public static String filter_Reg_Status(String str){
        if( StringUtils.isBlank(str)){
            return "";
        }
       String code="";
        //处理字段【注销 （注销日期 2017年8月11日）】,【注销(简易)  500000.0000】,【注销（注销日期2000年7月4日）】包含这种格式的数据归为“注销”
        if(str.contains("注销 （注销日期") || str.contains("注销(简易)") || str.contains("注销（注销日期") || str.contains("注销企业")){
            str="注销";
            //通过str值，查询dic_ent_status字典表，得到code
            code="通过str值，查询dic_ent_status字典表，得到code";
            return code.trim();
        }else{
            //如果通过str值，查询dic_ent_status字典表，能得到code值，则返回code值，查不到则返回原来的str
            return "如果通过str值，查询dic_ent_status字典表，能得到code值，则返回code值，查不到则返回原来的str";
        }
    }

    /**
     * 过滤企业性质 （文字转数字）
     *  1》 包含“个体”的返回个体工商户对应的code值
     *  2》 可以从字典表表中查到信息的则返回
     *  3》 没有匹配信息则输出原信息
     * @param str
     * @return
     */
    public static String filter_Company_Org_Type(String str) {
        if (str == null || "".equals(str) || "null".equalsIgnoreCase(str)) {
            return "";
        }
        String code = "";
        //包含“个体”的返回个体工商户对应的code值
        if (str.contains("个体")) {
            str = "个体工商户";
            //通过str值，查询企业性质字典表dic_ent_property得到code值
            code = "通过str值，查询企业性质字典表dic_ent_property得到code值";
            return code.trim();
        } else {
            str = str.trim().replace("（", "(").replace("）", ")");
            //如果通过str值，查询dic_ent_property字典表，能得到code值，则返回code值，查不到则返回原来的str
            return "如果通过str值，查询dic_ent_property字典表，能得到code值，则返回code值，查不到则返回原来的str";
        }
    }
}
