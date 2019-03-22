/**
 * BeanCreator.java--May 20, 2008
 * Author: Hao Jinlong
 */
package com.hzw.tools.sqlTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 实体类生成
 */
public class BeanCreator {
	//表名
	public static String tableName = "t_user";

	public static String idb = "hzw_xyp";
	public static String url = "jdbc:mysql://120.78.184.188:3306/";
	public static String dbUser = "";
	public static String password = "";
	
	static {
		url = url + idb + "?useSSL=false";
	}

	private static String getName(String colName) {
		String[] str = colName.split("_");
		if (str.length < 2) {
			return colName.toLowerCase();
		}
		StringBuffer buffer = new StringBuffer(str[0]);
		for (int i = 1; i < str.length; i++) {
			buffer.append((str[i].charAt(0) + "").toUpperCase()
					+ str[i].substring(1).toLowerCase());
		}
		return buffer.toString();
	}

	static Log log = LogFactory.getLog(BeanCreator.class);

	private void createBean(String tableName) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from " + tableName + " where 1=0");
			ResultSetMetaData rsmd = rs.getMetaData();
			StringBuffer fieldBuffer = new StringBuffer();
			StringBuffer gettingBuffer = new StringBuffer();
			StringBuffer settingBuffer = new StringBuffer();
			/*System.out.println("import java.io.Serializable;\n");
			System.out.println("import java.util.Date;\n");
			System.out.println("import javax.persistence.Column;");
			System.out.println("import javax.persistence.Entity;");
			System.out.println("import javax.persistence.GeneratedValue;");
			System.out.println("import javax.persistence.GenerationType;");
			System.out.println("import javax.persistence.Id;");
			System.out.println("import javax.persistence.Table;\n");*/
			
			
			/*System.out.println("@Entity");
			System.out.println("@Table(name=\"" + tableName	+ "\")");*/
			//System.out.println("// " + getTableComment(tableName, con));
			/*System.out.println("public class " + getClassName(tableName)
					+ " implements Serializable{");
			System.out.println("");*/
			//System.out.println("public static String TABLE_NAME = \""+tableName+"\";\n");
			int count = rsmd.getColumnCount();
			for (int i = 1; i <= count; i++) {
				String fieldType = "String";
				String colName = rsmd.getColumnName(i).toLowerCase();
				int type = rsmd.getColumnType(i);
				int precision = rsmd.getScale(i);

				// the "@" annotation
				if (colName.toUpperCase().equals("ID")) { // Id
					gettingBuffer.append("@Id" + "\n");
					gettingBuffer.append("@GeneratedValue(strategy = GenerationType.IDENTITY)\n");									
				} else if (colName.toUpperCase().equals("CREATE_TIME")
						|| colName.toUpperCase().equals("UPDATE_TIME")) { // create_time

					gettingBuffer.append("@Column(name=\""+ rsmd.getColumnLabel(i) + "\")"+ "\n");					
				}else {
					gettingBuffer.append("@Column(name=\""+ rsmd.getColumnLabel(i) + "\")" + "\n");
				}
				//System.out.println("type="+type+" ="+getName(colName));
				// properties
				String comment = getColComment(tableName, colName, con);
				//System.out.println("comment="+comment);				
				fieldBuffer.append("//"+comment+"\n");
				if (type == 12 || type == Types.CLOB) { // String
					fieldType = "String";
					
				} else if (type == Types.INTEGER || type == Types.DOUBLE) {
					if (precision > 0) { // Double
						fieldType = "Double";
						
					} else { // Integer		
						fieldType = "Integer";				
						
					}
				} else if (type == Types.DATE || type == 93) { // Date
					fieldType = "Date";

				} else if (type == Types.BLOB) { // byte[]
					fieldType = "byte[]";
					
				} else if (type == Types.BIGINT) { // long
					fieldType = "long";
					
				}
				
				// 拼接
				String str1 = "private " + fieldType + " " + getName(colName)
					+ getDefaultValue(tableName, colName) + ";" + "\n";
				fieldBuffer.append(str1);
		
				String str2 = "public " + fieldType + " get"
						+ getName(colName).substring(0, 1).toUpperCase()
						+ getName(colName).substring(1) + "(){return "
						+ getName(colName) + ";}" + "\n";
				gettingBuffer.append(str2);
				
				String str3 = "public void set"
						+ getName(colName).substring(0, 1).toUpperCase()
						+ getName(colName).substring(1) 
						+ "(" + fieldType + " " + getName(colName) + "){"
						+ "this." + getName(colName) + " = "
						+ getName(colName) + ";}" + "\n";
				settingBuffer.append(str3);
				
			}

			System.out.println(fieldBuffer.toString());
			System.out.println(gettingBuffer.toString());
			System.out.println(settingBuffer.toString());
			//System.out.println("}\n");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.getInstance().close(con, stmt, rs);
		}
	}

	private String getDefaultValue(String tableName, String colName) {
		String result = null;
		/*Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			String sql = "select data_default from user_tab_cols c where c.table_name=? and c.column_name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tableName.toUpperCase());
			pstmt.setString(2, colName.toUpperCase());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
				if (result != null) {
					if (result.equals("sysdate")) {
						result = "new Date()";
					}
					if (result.startsWith("'")) {
						result = "\""
								+ result.substring(1, result.length() - 1)
								+ "\"";
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.getInstance().close(con, pstmt, rs);
		}*/
		if (result != null) {
			result = "=" + result;
		} else {
			result = "";
		}
		return result;
	}

	private String getClassName(String tableName) {
		String[] str = tableName.toUpperCase().split("_");
		int i = 0;
		if (str[0].equals("T") || str[0].equals("M")) {
			i = 1;
		}		
		StringBuffer b = new StringBuffer();
		for (; i < str.length; i++) {
			b.append(str[i].charAt(0) + str[i].substring(1).toLowerCase());
		}
		return b.toString();
	}

	private String getTableComment(String tablename, Connection con)
			throws SQLException {
		PreparedStatement pst = con
				.prepareStatement("select comments from user_tab_comments t where t.table_name=?");
		pst.setString(1, tablename.toUpperCase());
		ResultSet rs = pst.executeQuery();
		rs.next();
		String result = rs.getString(1);
		rs.close();
		pst.close();
		return result;
	}

	private String getColComment(String tablename, String columnname,
			Connection con) throws SQLException {
		PreparedStatement pst = con
				.prepareStatement("SELECT column_comment FROM Information_schema.columns WHERE table_Name=? AND column_name=?");
		pst.setString(1, tablename.toUpperCase());
		pst.setString(2, columnname.toUpperCase());
		ResultSet rs = pst.executeQuery();
		String result = "";
		if(rs.next()) {
			result = rs.getString(1);
		}
		rs.close();
		pst.close();
		return result;
	}

	public void createInsert(String tableName) {
		System.out.println(ConstantString.getHeaderWithOutRS());
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from " + tableName + " where 1=0");
			ResultSetMetaData rsmd = rs.getMetaData();
			StringBuffer setvalue = new StringBuffer();
			StringBuffer cols = new StringBuffer();
			StringBuffer quest = new StringBuffer();
			int count = rsmd.getColumnCount();
			for (int i = 1; i <= count; i++) {
				String colName = rsmd.getColumnName(i).toLowerCase();
				cols.append(colName + ",");
				quest.append("?,");
				int type = rsmd.getColumnType(i);
				int precision = rsmd.getScale(i);
				if (type == 12) {
					setvalue.append("pstmt.setString(" + i + ", instance.get"
							+ colName.toUpperCase().charAt(0)
							+ colName.substring(1) + "());");
				} else if (type == 2) {
					if (precision > 0) {
						setvalue.append("pstmt.setDouble(" + i
								+ ", instance.get"
								+ colName.toUpperCase().charAt(0)
								+ colName.substring(1) + "());");
					} else {
						setvalue.append("pstmt.setLong(" + i + ", instance.get"
								+ colName.toUpperCase().charAt(0)
								+ colName.substring(1) + "());");
					}
				} else {
					setvalue.append("pstmt.setNoType(" + i + ", instance.get"
							+ colName.toUpperCase().charAt(0)
							+ colName.substring(1) + "());");
				}
				if (log.isDebugEnabled()) {
					log.debug("colname:" + colName + "; type:" + type
							+ ";precision:" + precision);
				}
			}
			String sql = "insert into " + tableName + "("
					+ cols.substring(0, cols.length() - 1) + ") values("
					+ quest.substring(0, quest.length() - 1) + ")";
			System.out.println(sql);
			System.out.println(setvalue);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.getInstance().close(con, stmt, rs);
		}
		System.out.println(ConstantString.getFooterWithOutRS());
	}

	public void createGet(String tableName) {
		System.out.println(ConstantString.getHeaderWithRS());
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from " + tableName + " where 1=0");
			ResultSetMetaData rsmd = rs.getMetaData();
			StringBuffer getvaliue = new StringBuffer();
			int count = rsmd.getColumnCount();
			for (int i = 1; i <= count; i++) {
				String colName = rsmd.getColumnName(i).toLowerCase();
				int type = rsmd.getColumnType(i);
				int precision = rsmd.getScale(i);
				if (type == 12) {
					getvaliue.append("instance.set"
							+ colName.toUpperCase().charAt(0)
							+ colName.substring(1) + "(rs.getString(\""
							+ colName + "\"));");
				} else if (type == 2) {
					if (precision > 0) {
						getvaliue.append("instance.set"
								+ colName.toUpperCase().charAt(0)
								+ colName.substring(1) + "(rs.getDouble(\""
								+ colName + "\"));");
					} else {
						getvaliue.append("instance.set"
								+ colName.toUpperCase().charAt(0)
								+ colName.substring(1) + "(rs.getLong(\""
								+ colName + "\"));");
					}
				} else {
					getvaliue.append("instance.set"
							+ colName.toUpperCase().charAt(0)
							+ colName.substring(1) + "(pstmt.getNoType(\""
							+ colName + "\"));");
				}
				if (log.isDebugEnabled()) {
					log.debug("colname:" + colName + "; type:" + type
							+ ";precision:" + precision);
				}
			}
			System.out.println(getvaliue);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.getInstance().close(con, stmt, rs);
		}
		System.out.println(ConstantString.getFooterWithRS());
	}

	public void createUpdate(String tableName) {
		System.out.println(ConstantString.getHeaderWithOutRS());
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from " + tableName + " where 1=0");
			ResultSetMetaData rsmd = rs.getMetaData();
			StringBuffer setvalue = new StringBuffer();
			StringBuffer sqlBf = new StringBuffer("update " + tableName
					+ " set ");
			int count = rsmd.getColumnCount();
			for (int i = 1; i <= count; i++) {
				String colName = rsmd.getColumnName(i).toLowerCase();
				sqlBf.append(colName + "=?,");
				int type = rsmd.getColumnType(i);
				int precision = rsmd.getScale(i);
				if (type == 12) {
					setvalue.append("pstmt.setString(" + i + ", instance.get"
							+ colName.toUpperCase().charAt(0)
							+ colName.substring(1) + "());");
				} else if (type == 2) {
					if (precision > 0) {
						setvalue.append("pstmt.setDouble(" + i
								+ ", instance.get"
								+ colName.toUpperCase().charAt(0)
								+ colName.substring(1) + "());");
					} else {
						setvalue.append("pstmt.setLong(" + i + ", instance.get"
								+ colName.toUpperCase().charAt(0)
								+ colName.substring(1) + "());");
					}
				} else {
					setvalue.append("pstmt.setNoType(" + i + ", instance.get"
							+ colName.toUpperCase().charAt(0)
							+ colName.substring(1) + "());");
				}
				if (log.isDebugEnabled()) {
					log.debug("colname:" + colName + "; type:" + type
							+ ";precision:" + precision);
				}
			}
			String sql = sqlBf.substring(0, sqlBf.length() - 1) + ") where ";
			System.out.println(sql);
			System.out.println(setvalue);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.getInstance().close(con, stmt, rs);
		}
		System.out.println(ConstantString.getFooterWithOutRS());
	}

	private void createdao(String tableName) {		
		System.out.println("import org.springframework.stereotype.Repository;");
		System.out.println("import pub.hibernate.HibernateDao;");
		//System.out.println("import com.sys.dao."+ getClassName(tableName) +"Dao;");
		//System.out.println("import com.hauxsoft.entity."+ getClassName(tableName) +";\n");
		
		System.out.println("@Repository");
		System.out.println("public class " + getClassName(tableName)
				+ "Dao extends HibernateDao<" + getClassName(tableName)
				+ ">  " + "{");
		System.out.println("public " + getClassName(tableName)
				+ "Dao() {super(" + getClassName(tableName) + ".class);}");
		System.out.println("}");
	}
	
	private void createService(String tableName) {
		System.out.println();
		System.out.println();

		System.out.println("@Service");
		System.out.println("@Transactional(readOnly = true)");
		System.out.println("public class " + getClassName(tableName)
				+ "Service{");
		System.out.println("");
		System.out.println("");
		System.out.println("@Autowired");
		String className = getClassName(tableName);
		String a = className.substring(0, 1);
		className = a.toLowerCase()+className.substring(1, className.length());
		System.out.println("private "+getClassName(tableName)+"Dao "+className+"Dao;");
		System.out.println("}");
	}

	public static void main(String[] args) {
		BeanCreator creator = new BeanCreator();
		System.out.println();	
		//System.out.println("package com.hauxsoft.entity;");
		creator.createBean(tableName);
		System.out.println();
		/*System.out.println("==================DAO=====================");
		System.out.println(); 
		creator.createdao(tableName);
		System.out.println(); 
		System.out.println("==================Service=====================");
		System.out.println(); 
		creator.createService(tableName);
		System.out.println();*/
	}
}
