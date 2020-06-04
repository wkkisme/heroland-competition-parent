package com.heroland.competition.common.constant;

/**
 * 数据质控平台统一术语类
 * @author chenli
 *
 */
public class DataQualityConstants {
	/**
	 * 新建odps表名称
	 */
	public static final String DEFAULT_RECORD_TABLE = "data_quality_platform_check_result";
	/**
	 * odps中分区字段名称
	 */
	public static final String DEFAULT_PARTITION_FIELD = "ds";
	/**
	 * odps 为yyyy-mm-dd HH:mi:ss
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";


	public static final String VALIDATION_FIELD_KEY = "field_key";
	public static final String VALIDATION_FIELD_TABLE_NAME = "field_table_name";
	public static final String VALIDATION_FIELD_DS = "field_ds";

	//校验结果
	public static final String VALIDATE_SUCCESS_MSG = "校验成功！";

	public static final String VALIDATE_FAILURE_MSG = "校验失败！";

	public static final String DATA_PARSE_ERROR_MSG = "数据转换出错！";

	public static final int VALIDATE_SUCCESS_CODE = 0;

	public static final int VALIDATE_FAILURE_CODE = -1;

	public static final int DATA_PARSE_ERROR_CODE = -2;

	public static final String DEFAULT_CHARSET = "utf-8";

	//系统结果以及返回信息
	public static final String ERROR_SYSTEM = "系统错误";

	public static final String DELETE_SUCCESS = "删除成功";

	public static final String DATA_EXISTS = "数据已存在，请勿重复新增";

	public static final String DB_EXISTS = "数据库已存在，请勿重复新增";

	//常用静态值
	public static final String SIGN_COMMA = ",";

	public static final String EXCEL_XLS = "xls";

	public static final String EXCEL_XLSX = "xlsx";

	public static final String DEFAULT_FILE_NAME = "扁鹊数据.xlsx";

	public static final String DB_ERROR = "该数据库类型暂不支持";

	public static final int ZERO = 0;

	public static final int SUCCESS_STATE = 0;

	public static final String FONT_NAME = "华文行楷";

	public static final int COLUMN_WIDTH_20PX = 5120;

	public static final int COLUMN_WIDTH_30PX = 7680;

	public static final int COLUMN_WIDTH_40PX = 10240;

	public static final int COLUMN_WIDTH_50PX = 12800;

	public static final int SESSION_TOMEOUT_CODE = -100;

	public static final int ONE = 1;

	public static final int TWO = 2;

	public static final int THREE = 3;

	public static final int FOUR = 4;

	public static final int FIVE = 5;

	public static final int SIX = 6;

	public static final int SEVEN = 7;

	public static final int EIGTH = 8;

	public static final int NINE = 9;

	public static final int TEN = 10;

	public static final String SIGN_EQUAL = "=";

	public static final String SIGN_SEMICILON = ";";

	public static final String SIGN_COLON = ":";

	public static final String SIGN_SLASH = "/";

	public static final int NRGATIVE_ONE = -1;


	public static final String METHOD_TYPE = "save";

	//数据源参数
	public static final String MYSQL_URL_PREFIX = "jdbc:mysql://";

	public static final String SQL_SERVER_URL_PREFIX = "jdbc:sqlserver://";

	public static final String ORACLE_URL_PREFIX = "jdbc:oracle:thin:@";

	public static final String DB2_URL_PREFIX = "jdbc:db2://";

	public static final String POSTGRE_SQL_URL_PREFIX = "jdbc:postgresql://";

	public static final String MYSQL = "mysql";

	public static final String ORACLE = "oracle";

	public static final String DB2 = "db2";

	public static final String SQL_SERVER = "sqlserver";

	public static final String POSTGRE_SQL = "postgresql";

	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

	public static final String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";

	public static final String SQL_SERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	public static final String DB2_DRIVER = "com.ibm.db2.jcc.DB2Driver";

	public static final String POSTGRE_SQL_DRIVER = "org.postgresql.Driver";

	public static final String MYSQL_SUFFIX = "?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8";

	public static final String DRIVER = "driver";

	public static final String URL = "url";
	/**
	 * 请勿更改该key 必须16位
	 */
	public static final String AES_ENCODE_RULES = "BQSJ-DATAQuality";

	public static final String CONNECT_SUCCESS = "Connected DataSource";

	public static final String CONNECT_FAILED = "连接数据库失败!!! 失败原因 ";


	/*默认过期时间,单位/秒, 半小时*/
	public static final int DEFAULT_EXPIRE_TIME = 1800;

	public static final String ACCESS_TOKEN = "accessToken";

	public static final String NO_AUTH = "无权限访问，请登陆";

	public static final String LOGIN_FAILED = "用户名或密码错误";

	public static final int ELEVEN =  11;

	public static final String HIDE_INFO = "******";

	public static final String LOG_OUT = "已退出，请勿重复操作";

	public static final String LOGOUT_SUCCESS = "登出成功";

	public static final String LOGIN_SUCCESS_STATUS = "ok";

	public static final String DICDATA = "dictionary_data";

	public static final String STANDARD_UNION_ELEMENT = "standard_union_element";

	public static final int DATA_DUPLICATION = 2;
}
