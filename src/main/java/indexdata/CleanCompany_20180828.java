/*
package indexdata;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.MapredContext;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

*/
/**
 *  清洗数据库：天眼查工商（tianyancha_gongshang_new）
 *  清洗数据表：company
 *  项目描述：根据清洗company表规则将清洗后的字段插入到两张新的表
 *  生成表1 【ease_meta数据库index_data表】两个表字段对应关系，没有写的index_data表字段为空
 *      company表字段：id                 ==》  index_data表字段：company_id
 *      company表字段：name               ==》  index_data表字段：ent_name_zh
 *      company表字段：reg_number         ==》  index_data表字段：reg_no
 *      company表字段：org_number         ==》  index_data表字段：org_no
 *      company表字段：source_flag        ==》  index_data表字段：country
 *      company表字段：property1          ==》  index_data表字段：social_credit_no
 *      company表字段：property2          ==》  index_data表字段：key_names
 *      company表字段：property3          ==》  index_data表字段：ent_name_en
 *
 *  生成表2 【ease_meta数据库data_reg_info表】两个表字段对应关系，没有写的data_reg_info表字段为空
 *      company表字段：id                       ==》  data_reg_info表字段：company_id
 *      company表字段：legal_person_id          ==》  data_reg_info表字段：legal_person_id
 *      company表字段：legal_person_name        ==》  data_reg_info表字段：s_legal_person_name
 *      company表字段：legal_person_type        ==》  data_reg_info表字段：s_legal_person_type
 *      company表字段：company_org_type         ==》  data_reg_info表字段：s_company_org_type
 *      company表字段：reg_location             ==》  data_reg_info表字段：s_reg_location
 *      company表字段：estiblish_time           ==》  data_reg_info表字段：da_estiblish_time
 *      company表字段：from_time                ==》  data_reg_info表字段：da_from_time
 *      company表字段：to_time                  ==》  data_reg_info表字段：da_to_time
 *      company表字段：business_scope           ==》  data_reg_info表字段：s_business_scope
 *      company表字段：reg_institute            ==》  data_reg_info表字段：s_reg_org_zh
 *      company表字段：approved_time            ==》  data_reg_info表字段：da_approved_time
 *      company表字段：reg_status               ==》  data_reg_info表字段：s_ent_status
 *      company表字段：reg_capital              ==》  data_reg_info表字段：d_reg_cap
 *      company表字段：actual_capital           ==》  data_reg_info表字段：d_actual_capital
 *      company表字段：org_approved_institute   ==》  data_reg_info表字段：s_org_approved_institute
 *      company表字段：flag                     ==》  data_reg_info表字段：s_flag
 *      company表字段：parent_id                ==》  data_reg_info表字段：s_parent_id
 * Created by fss on 2018/8/28.
 *//*

public class CleanCompany_20180828 {


    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2) {
            System.err.println("Usage: wordcount <in> <out>");
            System.exit(2);
        }
        Job job = new Job(conf, "word count");
        job.setJarByClass(CleanCompany_20180828.class);
        job.setMapperClass(MyMapper.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);



        //集群模式yarn模式
        conf.set("mapreduce.framework.name", "yarn");

        // 将hdfs上的文件加入分布式缓存
        job.addCacheFile(new URI("hdfs://bdpha/fei_test/filter_template/dic_ent_property.txt#dic_ent_property.txt"));//企业性质  （2018-12-06更新完成）
        job.addCacheFile(new URI("hdfs://bdpha/fei_test/filter_template/dic_zcjg_zs.txt#dic_zcjg_zs.txt"));//登记机关
        job.addCacheFile(new URI("hdfs://bdpha/fei_test/filter_template/dic_enterprise_state.txt#dic_enterprise_state.txt"));//企业状态  （2018-12-06更新完成）
        job.addCacheFile(new URI("hdfs://bdpha/fei_test/filter_template/dic_currency_del.txt#dic_currency_del.txt"));//币种信息

        //错误数据输出
        MultipleOutputs.addNamedOutput(job, "Error", TextOutputFormat.class, Text.class, Text.class);
        //企业性质包含“个人”数据输出
        MultipleOutputs.addNamedOutput(job, "Delete", TextOutputFormat.class, Text.class, Text.class);
        //所有数据输出
        MultipleOutputs.addNamedOutput(job, "All", TextOutputFormat.class, Text.class, Text.class);
        //数据输出【index_data】表
        MultipleOutputs.addNamedOutput(job, "IndexData", TextOutputFormat.class, Text.class, Text.class);
        //数据输出【data_reg_info】表
        MultipleOutputs.addNamedOutput(job, "DataRegInfo", TextOutputFormat.class, Text.class, Text.class);

        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static class MyMapper extends Mapper<Object, Text, Text, Text> {
        private final static Text valueNull = new Text("");
        DataClean dc = new DataClean();
        Company company = new Company();
        Text finalData = new Text();
        String num;//自定义32位UUID
        */
/*int num_AllData = 1;//总数据计数器
        int num_CompanyOrgType = 1;//企业性质计数器
        int num_RegInstitute = 1;//登记机关计数器
        int num_RegStatus = 1;//企业状态计数器
        int num_OrgNumber = 1;//组织机构代码计数器
        int num_RegOrgApprovedInstitute = 1;//组织机构批准单位计数器*//*


        */
/**
         * 保存缓存中的信息
         * map_dic_ent_property        企业性质（企业性质名称，code号）
         * map_dic_ent_property2        企业性质（code号，企业性质名称）
         * map_dic_zcjg_zs             登记机关
         * map_dic_enterprise_state    企业状态
         * list_dic_currency_del        币种代码
         *//*

        Map map_dic_ent_property = new HashMap();//企业性质（企业性质名称，code号）
        Map map_dic_ent_property2 = new HashMap();//企业性质（code号，企业性质名称）
        Map map_dic_zcjg_zs = new HashMap();//登记机关
        Map map_dic_enterprise_state = new HashMap();//企业状态（企业状态名称，code号）
        Map map_dic_enterprise_state2 = new HashMap();//企业状态（code号，企业状态名称）
        Map map_dic_currency_del = new HashMap();//币种代码（币种，code号）
        List list_dic_currency_del = new ArrayList<String>();//币种代码（币种）

        //设置多文件输出
        private MultipleOutputs<Text, Text> mos;


        @Override
        protected void setup(Context context) throws IOException, InterruptedException {

            super.setup(context);
            // 获取分布式缓存中传入的参数
            Path[] cacheFiles = context.getLocalCacheFiles();

            // ==================1.保存企业性质==================
            BufferedReader reader_dic_ent_property = new BufferedReader(new FileReader(cacheFiles[0].toUri().getPath()));
            String line_dic_ent_property = null;
            while (null != (line_dic_ent_property = reader_dic_ent_property.readLine())) {
                String[] str_dic_ent_property = line_dic_ent_property.split("\t");
                if (str_dic_ent_property.length == 2) {
                    map_dic_ent_property.put(str_dic_ent_property[0], str_dic_ent_property[1]);
                    map_dic_ent_property2.put(str_dic_ent_property[1], str_dic_ent_property[0]);
                }
            }
            IOUtils.closeStream(reader_dic_ent_property);

            // ==================2.保存登记机关==================
            BufferedReader reader_dic_zcjg_zs = new BufferedReader(new FileReader(cacheFiles[1].toUri().getPath()));
            String line_dic_zcjg_zs = null;
            while (null != (line_dic_zcjg_zs = reader_dic_zcjg_zs.readLine())) {
                String[] str_dic_zcjg_zs = line_dic_zcjg_zs.split("\t");
                if (str_dic_zcjg_zs.length == 2) {
                    map_dic_zcjg_zs.put(str_dic_zcjg_zs[0], str_dic_zcjg_zs[1]);
                }
            }
            IOUtils.closeStream(reader_dic_zcjg_zs);

            // ==================3.保存企业状态===================
            BufferedReader reader_dic_enterprise_state = new BufferedReader(new FileReader(cacheFiles[2].toUri().getPath()));
            String line_dic_enterprise_state = null;
            while (null != (line_dic_enterprise_state = reader_dic_enterprise_state.readLine())) {
                String[] fields = line_dic_enterprise_state.split("\t");
                if (fields.length == 2) {
                    map_dic_enterprise_state.put(fields[0], fields[1]);
                    map_dic_enterprise_state2.put(fields[1], fields[0]);
                }
            }
            IOUtils.closeStream(reader_dic_enterprise_state);

            // ==================4.保存币种信息===================
            BufferedReader reader_dic_currency_del = new BufferedReader(new FileReader(cacheFiles[3].toUri().getPath()));
            String line_dic_currency_del = null;
            while (null != (line_dic_currency_del = reader_dic_currency_del.readLine())) {
                String[] fields = line_dic_currency_del.split("\t");
                if (fields.length == 2) {
                    list_dic_currency_del.add(fields[0]);
                    map_dic_currency_del.put(fields[0], fields[1]);
                }
            }
            IOUtils.closeStream(reader_dic_currency_del);

            mos = new MultipleOutputs<Text, Text>(context);
        }

        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {

            String[] str = value.toString().split("\001");
            StringBuffer sb = new StringBuffer();//拼接错误数据
            //错误数据输出（脏数据，数据中包含“\001”分隔符等不规则数据）
            if (str.length != 34) {//切分后的字段数量和company表字段数量不匹配，则按错误数据处理
                for (int i = 0; i < str.length; i++) {
                    if (i == 0) {
                        sb.append(str[i]);
                    } else {
                        sb.append("\t" + str[i]);
                    }
                }
                finalData.set(sb.toString());
                mos.write("Error", finalData, valueNull);
                return;
            }
            for (int i = 0; i < str.length; i++) {
                company.setId(clean_Null(str[0]));
                company.setBase(clean_Null(str[1]));
                company.setName(dc.filter_Name(clean_Null(str[2])));
                company.setLegal_person_id(clean_Null(str[3]));
                company.setLegal_person_name(dc.filter_Legal_Person_Name(clean_Null(str[4])));
                company.setLegal_person_type(clean_Null(str[5]));
                company.setReg_number(dc.filter_Reg_Number(clean_Null(str[6])));
                company.setCompany_type(clean_Null(str[7]));
//                company.setCompany_org_type(dc.filter_Company_Org_Type(str[8]==null||"".equals(str[8])?"":str[8].trim(),map_dic_ent_property));//企业性质(原有逻辑处理)
                company.setCompany_org_type(clean_Null(str[8]));//企业性质不做处理
                company.setReg_location(clean_Null(str[9]));
                company.setEstiblish_time(clean_Null(str[10]));
                company.setFrom_time(clean_Null(str[11]));
                company.setTo_time(clean_Null(str[12]));
                company.setBusiness_scope(clean_Null(str[13]));
                company.setReg_institute(dc.filter_Reg_Institute(clean_Null(str[14]), map_dic_zcjg_zs));
                company.setApproved_time(clean_Null(str[15]));
                company.setReg_status(dc.filter_Reg_Status(clean_Null(str[16]), map_dic_enterprise_state));//企业状态
                company.setReg_capital(dc.filter_Reg_Capital(clean_Null(str[17]), list_dic_currency_del, map_dic_currency_del, company));//注册资金
                company.setActual_capital(clean_Null(str[18]));
                company.setOrg_number(dc.filter_Org_Number(clean_Null(str[19])));
                company.setOrg_approved_institute(str[20].trim());//组织机构批准单位（不做修改，下面进行判断）
                company.setFlag(clean_Null(str[21]));
                company.setParent_id(dc.filter_Parent_Id(clean_Null(str[22])));
                company.setUpdatetime(clean_Null(str[23]));
                company.setList_code(clean_Null(str[24]));
                company.setOwnership_stake(clean_Null(str[25]));
                company.setSource_flag(clean_Null(str[26]));
                company.setName_suffix(clean_Null(str[27]));
                company.setProperty1(dc.filter_Property1(clean_Null(str[28])));
                company.setProperty2(clean_Null(str[29]));
                company.setProperty3(clean_Null(str[30]));
                company.setProperty4(dc.filter_Property4(clean_Null(str[31])));
                company.setProperty5(clean_Null(str[32]));
                company.setCrawledtime(clean_Null(str[33]));
            }

//            boolean bl_1 = filterCompanyOrgType(company.getCompany_org_type(), company.getName(), map_dic_ent_property);
            boolean bl_1 = filterCompanyOrgType(company.getCompany_org_type(), company.getName(), map_dic_ent_property);
            boolean bl_2 = isAllNull(company);

            //过滤企业性质包含“个人、个体”的信息
            if (company.getCompany_org_type().contains("个人") || company.getCompany_org_type().contains("个体")) {
                */
/*String ss = company.getId()+"\t"+company.getBase()+"\t"+company.getName()+"\t"+company.getLegal_person_id()+"\t"+company.getLegal_person_name()+"\t"
                        +company.getLegal_person_type()+"\t"+company.getReg_number()+"\t"+company.getCompany_type()+"\t"+dc.filter_Company_Org_Type(company.getCompany_org_type(),map_dic_ent_property)+"\t"
                        +company.getReg_location()+"\t"+company.getEstiblish_time()+"\t"+company.getFrom_time()+"\t"+company.getTo_time()+"\t"+company.getBusiness_scope()+"\t"
                        +company.getReg_institute()+"\t"+company.getApproved_time()+"\t"+company.getReg_status()+"\t"+return_Money(company.getReg_capital())+"\t"+company.getActual_capital()+"\t"
                        +company.getOrg_number()+"\t"+company.getOrg_approved_institute()+"\t"+company.getFlag()+"\t"+company.getParent_id()+"\t"+company.getUpdatetime()+"\t"
                        +company.getList_code()+"\t"+company.getOwnership_stake()+"\t"+company.getSource_flag()+"\t"+company.getName_suffix()+"\t"+company.getProperty1()+"\t"
                        +company.getProperty2()+"\t"+company.getProperty3()+"\t"+company.getProperty4()+"\t"+company.getProperty5()+"\t"+company.getCrawledtime();*//*

                String ss = company.getId() + "\001" + company.getBase() + "\001" + company.getName() + "\001" + company.getLegal_person_id() + "\001" + company.getLegal_person_name() + "\001"
                        + company.getLegal_person_type() + "\001" + company.getReg_number() + "\001" + company.getCompany_type() + "\001" + dc.filter_Company_Org_Type(company.getCompany_org_type(), map_dic_ent_property) + "\001"
                        + company.getReg_location() + "\001" + company.getEstiblish_time() + "\001" + company.getFrom_time() + "\001" + company.getTo_time() + "\001" + company.getBusiness_scope() + "\001"
                        + company.getReg_institute() + "\001" + company.getApproved_time() + "\001" + company.getReg_status() + "\001" + return_Money(company.getReg_capital()) + "\001" + company.getActual_capital() + "\001"
                        + company.getOrg_number() + "\001" + company.getOrg_approved_institute() + "\001" + company.getFlag() + "\001" + company.getParent_id() + "\001" + company.getUpdatetime() + "\001"
                        + company.getList_code() + "\001" + company.getOwnership_stake() + "\001" + company.getSource_flag() + "\001" + company.getName_suffix() + "\001" + company.getProperty1() + "\001"
                        + company.getProperty2() + "\001" + company.getProperty3() + "\001" + company.getProperty4() + "\001" + company.getProperty5() + "\001" + company.getCrawledtime();
                finalData.set(ss);
//                mos.write("Delete", finalData,valueNull);
                return;
            }

            // 最终数据输出，数据格式拼接 ==》 “\t”
            if (bl_1 || bl_2) {
                */
/**
                 * ===============数据输出【company】表===============
                 *//*



                */
/*String ss = company.getId()+"\t"+company.getBase()+"\t"+company.getName()+"\t"+company.getLegal_person_id()+"\t"+company.getLegal_person_name()+"\t"
                        +company.getLegal_person_type()+"\t"+company.getReg_number()+"\t"+company.getCompany_type()+"\t"+dc.filter_Company_Org_Type(company.getCompany_org_type(),map_dic_ent_property)+"\t"
                        +company.getReg_location()+"\t"+company.getEstiblish_time()+"\t"+company.getFrom_time()+"\t"+company.getTo_time()+"\t"+company.getBusiness_scope()+"\t"
                        +company.getReg_institute()+"\t"+company.getApproved_time()+"\t"+company.getReg_status()+"\t"+return_Money(company.getReg_capital())+"\t"+company.getActual_capital()+"\t"
                        +company.getOrg_number()+"\t"+company.getOrg_approved_institute()+"\t"+company.getFlag()+"\t"+company.getParent_id()+"\t"+company.getUpdatetime()+"\t"
                        +company.getList_code()+"\t"+company.getOwnership_stake()+"\t"+company.getSource_flag()+"\t"+company.getName_suffix()+"\t"+company.getProperty1()+"\t"
                        +company.getProperty2()+"\t"+company.getProperty3()+"\t"+company.getProperty4()+"\t"+company.getProperty5()+"\t"+company.getCrawledtime();*//*

                String ss_all = company.getId() + "\001" + company.getBase() + "\001" + company.getName() + "\001" + company.getLegal_person_id() + "\001" + company.getLegal_person_name() + "\001"
                        + company.getLegal_person_type() + "\001" + company.getReg_number() + "\001" + company.getCompany_type() + "\001" + dc.filter_Company_Org_Type(company.getCompany_org_type(), map_dic_ent_property) + "\001"
                        + company.getReg_location() + "\001" + company.getEstiblish_time() + "\001" + company.getFrom_time() + "\001" + company.getTo_time() + "\001" + company.getBusiness_scope() + "\001"
                        + company.getReg_institute() + "\001" + company.getApproved_time() + "\001" + company.getReg_status() + "\001" + return_Money(company.getReg_capital()) + "\001" + company.getActual_capital() + "\001"
                        + company.getOrg_number() + "\001" + company.getOrg_approved_institute() + "\001" + company.getFlag() + "\001" + company.getParent_id() + "\001" + company.getUpdatetime() + "\001"
                        + company.getList_code() + "\001" + company.getOwnership_stake() + "\001" + company.getSource_flag() + "\001" + company.getName_suffix() + "\001" + company.getProperty1() + "\001"
                        + company.getProperty2() + "\001" + company.getProperty3() + "\001" + company.getProperty4() + "\001" + company.getProperty5() + "\001" + company.getCrawledtime();
                finalData.set(ss_all);
                mos.write("All", finalData, valueNull);

                */
/**
                 * ================数据输出【index_data】表=================
                 *//*

                //UUID赋值
                num = getUUID32();
                */
/*String ss = num + "\t" +  company.getId() + "\t" + "" + "\t" + company.getName() + "\t" + company.getProperty3() + "\t" + company.getReg_number() + "\t"
                        + company.getProperty1() + "\t" + company.getOrg_number() + "\t" + "" + "\t" + company.getSource_flag() + "\t" + company.getProperty2() + "\t"
                        + "" + "\t" + "" + "\t" + "" + "\t" + "" + "\t" + "";*//*

                */
/**
                 * 2018/11/05 增加税号的获取 company.getProperty4()
                 *//*

                String ss_indexdata = num + "\001" + company.getId() + "\001" + "" + "\001" + company.getName() + "\001" + company.getProperty3() + "\001" + company.getReg_number() + "\001"
                        + company.getProperty1() + "\001" + company.getOrg_number() + "\001" + company.getProperty4() + "\001" + company.getSource_flag() + "\001" + company.getProperty2() + "\001"
                        + "" + "\001" + "" + "\001" + "" + "\001" + "" + "\001" + "";
                finalData.set(ss_indexdata);
                mos.write("IndexData", finalData,valueNull);

                */
/**
                 * ===============数据输出【data_reg_info】表===============
                 *//*

                */
/*String ss = num + "\t" + num + "\t" + "" + "\t" + "" + "\t" + "" + "\t" + "" + "\t" + company.getId() + "\t" + company.getLegal_person_id() + "\t"
                        + company.getLegal_person_name() + "\t" + company.getLegal_person_type() + "\t" + dc.filter_Company_Org_Type(company.getCompany_org_type(),map_dic_ent_property) + "\t" + company.getReg_location() + "\t"
                        + "" + "\t" + company.getEstiblish_time() + "\t" + company.getFrom_time() + "\t" + company.getTo_time() + "\t" + company.getBusiness_scope() + "\t"
                        + "" + "\t" + "" + "\t" + company.getReg_institute() + "\t" + company.getApproved_time() + "\t" + company.getReg_status() + "\t" + "" +"\t" + "" + "\t" + "" + "\t"
                        + return_Money(company.getReg_capital()) + "\t" + return_Money_Code(company.getReg_capital()) + "\t" + company.getActual_capital() + "\t" + "" + "\t" + "" + "\t" + company.getOrg_approved_institute() + "\t" + company.getFlag() + "\t"
                        + company.getParent_id();*//*

                String ss_datareginfo = getUUID32() + "\001" + num + "\001" + "" + "\001" + "" + "\001" + "" + "\001" + "" + "\001" + company.getId() + "\001" + company.getLegal_person_id() + "\001"
                        + company.getLegal_person_name() + "\001" + company.getLegal_person_type() + "\001" + dc.filter_Company_Org_Type(company.getCompany_org_type(), map_dic_ent_property) + "\001" + company.getReg_location() + "\001"
                        + "" + "\001" + company.getEstiblish_time() + "\001" + company.getFrom_time() + "\001" + company.getTo_time() + "\001" + company.getBusiness_scope() + "\001"
                        + "" + "\001" + "" + "\001" + company.getReg_institute() + "\001" + company.getApproved_time() + "\001" + company.getReg_status() + "\001" + "" + "\001" + "" + "\001" + "" + "\001"
                        + return_Money(company.getReg_capital()) + "\001" + return_Money_Code(company.getReg_capital()) + "\001" + company.getActual_capital() + "\001" + "" + "\001" + "" + "\001" + company.getOrg_approved_institute() + "\001" + company.getFlag() + "\001"
                        + company.getParent_id();
                finalData.set(ss_datareginfo);
                mos.write("DataRegInfo", finalData, valueNull);


            }
//            num++;
        }


        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            //注意要调用close方法，否则会没有输出
            mos.close();
        }
    }

    */
/**
     * 过滤空字符串（包含“Null/null/NULL”字符串的一律返回空）
     *
     * @param str
     * @return
     *//*

    public static String clean_Null(String str) {
        if (null == str || StringUtils.isBlank(str) || "null".equalsIgnoreCase(str)) {
            return "";
        }
        return str.trim();
    }

    */
/**
     * 注册资金1，过滤空字符串（不为空的话,以“\t”切分，返回第一个“金额”）
     *
     * @param str
     * @return
     *//*

    public static String return_Money(String str) {
        if (null == str || StringUtils.isBlank(str) || "null".equalsIgnoreCase(str)) {
            return "";
        }
        return str.trim().split("\t")[0];
    }

    */
/**
     * 注册资金2，过滤空字符串（不为空的话,以“\t”切分，返回第二个“金额代码”）
     *
     * @param str
     * @return
     *//*

    public static String return_Money_Code(String str) {
        if (null == str || StringUtils.isBlank(str) || "null".equalsIgnoreCase(str)) {
            return "";
        }
        return str.trim().split("\t")[1];
    }

    */
/**
     * 过滤字段 company_org_type, name
     * 规则：
     * 1》 company_org_type字段为：个体、个体户、个体工商户、个体（内地），可直接删除不做清洗，跳到下条
     * 2》 company_org_type字段为空，同时企业名称为数字或者字母+数字的，可直接删除不做清洗，跳到下条
     *
     * @return
     *//*

    public static boolean filterCompanyOrgType(String company_org_type, String name, Map map) {
        //判断规则一（字段非空）
        if (null != company_org_type && StringUtils.isNotBlank(company_org_type) && !"null".equalsIgnoreCase(company_org_type) && (company_org_type.contains("个体") || company_org_type.contains("个人"))) {
            return false;
        }

        if (StringUtils.isBlank(name) || null == name || "null".equalsIgnoreCase(name)) {
            return false;
        }
        //判断规则二（字段为空）
        //表示过滤数字+字母
//        String regEx1 = "(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]";
        */
/*String regEx1 = "(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]";
        Pattern pat1 = Pattern.compile(regEx1);
        Matcher mat1 = pat1.matcher(name);
        boolean rs1 = mat1.find();*//*


        //表示过滤数字
       */
/* String regEx2 = "^\\d+$";
        Pattern pat2 = Pattern.compile(regEx2);
        Matcher mat2 = pat2.matcher(name);
        boolean rs2 = mat2.find();*//*


        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < name.length(); i++) { //循环遍历字符串
            if (Character.isDigit(name.charAt(i))) {     //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(name.charAt(i))) {   //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }

        //判断规则二
        if ((isDigit && isLetter) || isDigit) {
            return false;
        }
        return true;
    }

    */
/**
     * Company 规则1.6》
     * 1》判断是否为乱码
     * 2》判断company对象中的字段是否全为空
     *
     * @return
     *//*

    public static boolean isAllNull(Company company) {
        if (StringUtils.isBlank(company.getName()) || null == company.getName() || "null".equalsIgnoreCase(company.getName())) {
            return false;
        }
        DataClean dc = new DataClean();
        //判断乱码
        if (dc.isMessyCode(company.getName())) {
            //所有字段都为空，则返回false
            if (StringUtils.isBlank(company.getId()) && StringUtils.isBlank(company.getBase()) && StringUtils.isBlank(company.getLegal_person_id())
                    && StringUtils.isBlank(company.getLegal_person_name()) && StringUtils.isBlank(company.getLegal_person_type()) && StringUtils.isBlank(company.getReg_number())
                    && StringUtils.isBlank(company.getCompany_type()) && StringUtils.isBlank(company.getCompany_org_type()) && StringUtils.isBlank(company.getReg_location())
                    && StringUtils.isBlank(company.getEstiblish_time()) && StringUtils.isBlank(company.getFrom_time()) && StringUtils.isBlank(company.getTo_time())
                    && StringUtils.isBlank(company.getBusiness_scope()) && StringUtils.isBlank(company.getReg_institute()) && StringUtils.isBlank(company.getApproved_time())
                    && StringUtils.isBlank(company.getReg_status()) && StringUtils.isBlank(company.getReg_capital()) && StringUtils.isBlank(company.getActual_capital())
                    && StringUtils.isBlank(company.getOrg_number()) && StringUtils.isBlank(company.getOrg_approved_institute()) && StringUtils.isBlank(company.getFlag())
                    && StringUtils.isBlank(company.getParent_id()) && StringUtils.isBlank(company.getUpdatetime()) && StringUtils.isBlank(company.getList_code())
                    && StringUtils.isBlank(company.getOwnership_stake()) && StringUtils.isBlank(company.getSource_flag()) && StringUtils.isBlank(company.getName_suffix())
                    && StringUtils.isBlank(company.getProperty1()) && StringUtils.isBlank(company.getProperty2()) && StringUtils.isBlank(company.getProperty3())
                    && StringUtils.isBlank(company.getProperty4()) && StringUtils.isBlank(company.getProperty5()) && StringUtils.isBlank(company.getCrawledtime())) {
                return false;
            }
        }
        return true;
    }

    public static String getUUID32(){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }

    ////////////////////////////////////////自定义计数器//////////////////////////////////////////////
   */
/* public String getIndex(Mapper.Context context) {

        //context.getTaskAttemptID();
        //context.getConfiguration().get("mapred.map.tasks");

        this.configure(context);
        result.set(getValue());
        increment(this.increment);
        return result+"";
    }


    private static LongWritable result = new LongWritable();
    private static final char SEPARATOR = '_';
    private static final String ATTEMPT = "attempt";
    private long initID = 0L;
    private int increment = 0;

    public void configure(Mapper.Context context) {
        //this.increment = context.getJobConf().getNumMapTasks();
        this.increment = context.getConfiguration().getInt("mapred.map.tasks",1);
        if (this.increment == 0) {
            throw new IllegalArgumentException("mapred.map.tasks is zero");
        }
        //this.initID = getInitId(context.getJobConf().get("mapred.task.id"), this.increment);mvn
        this.initID = getInitId(context.getTaskAttemptID().toString(), this.increment);
        if (this.initID == 0L) {
            throw new IllegalArgumentException("mapred.task.id");
        }
        System.out.println("initID : " + this.initID + "  increment : " + this.increment);
    }

    public ObjectInspector initialize(ObjectInspector[] arguments)
            throws UDFArgumentException {
        return PrimitiveObjectInspectorFactory.writableLongObjectInspector;
    }

    public Object evaluate(GenericUDF.DeferredObject[] arguments)
            throws HiveException {
        result.set(getValue());
        increment(this.increment);
        return result;
    }

    public String getDisplayString(String[] children) {
        return "RowSeq-func()";
    }

    private synchronized void increment(int incr) {
        this.initID += incr;
    }

    private synchronized long getValue() {
        return this.initID;
    }

    private long getInitId(String taskAttemptIDstr, int numTasks)
            throws IllegalArgumentException
    {
        try
        {
            String[] parts = taskAttemptIDstr.split(Character.toString('_'));
            if ((parts.length == 6) &&
                    (parts[0].equals("attempt")))
            {
                if ((!parts[3].equals("m")) && (!parts[3].equals("r"))) {
                    throw new Exception();
                }
                long result = Long.parseLong(parts[4]);
                if (result >= numTasks) {
                    throw new Exception("TaskAttemptId string : " + taskAttemptIDstr +
                            "  parse ID [" + result + "] >= numTasks[" + numTasks + "] ..");
                }
                return result + 1L;
            }
            return -1;
        }
        catch (Exception localException)
        {
            throw new IllegalArgumentException("TaskAttemptId string : " + taskAttemptIDstr +
                    " is not properly formed");
        }
    }*//*




}
*/
