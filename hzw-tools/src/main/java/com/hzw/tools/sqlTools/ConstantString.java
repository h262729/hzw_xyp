/**
 * ConstantString.java--May 20, 2008
 * Author: Hao Jinlong
 */
package com.hzw.tools.sqlTools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * 
 */
public class ConstantString {
	public static final String EOF_LINE = System.getProperty("line.separator");
	static Log log = LogFactory.getLog(ConstantString.class);

	public static String getHeaderWithRS() {
		StringBuffer sb = new StringBuffer();
		sb.append("Connection con = null;" + EOF_LINE);
		sb.append("PreparedStatement pstmt = null;" + EOF_LINE);
		sb.append("ResultSet rs = null;" + EOF_LINE);
		sb.append("String sql = \"\"; " + EOF_LINE);
		sb.append("try{" + EOF_LINE);
		sb.append("con = null;" + EOF_LINE);
		sb.append("pstmt = con.prepareStatement(sql);" + EOF_LINE);
		sb.append("rs = pstmt.executeQuery();" + EOF_LINE);
		return sb.toString();
	}

	public static String getHeaderWithOutRS() {
		StringBuffer sb = new StringBuffer();
		sb.append("Connection con = null;" + EOF_LINE);
		sb.append("PreparedStatement pstmt = null;" + EOF_LINE);
		sb.append("String sql = \"\"; " + EOF_LINE);
		sb.append("try{" + EOF_LINE);
		sb.append("con = null;" + EOF_LINE);
		sb.append("pstmt = con.prepareStatement(sql);" + EOF_LINE);
		sb.append("pstmt.execute();" + EOF_LINE);
		return sb.toString();
	}

	public static String getFooterWithRS() {
		StringBuffer sb = new StringBuffer();
		sb.append("}" + EOF_LINE);
		sb.append("catch (SQLException e){" + EOF_LINE);
		sb.append("if (log.isErrorEnabled()) {" + EOF_LINE);
		sb
				.append("log.error(\"error when query data. sql:\" + sql + \". message:\"	+ e.getMessage());"
						+ EOF_LINE);
		sb.append("}" + EOF_LINE);
		sb.append("} finally {" + EOF_LINE);
		sb.append("close(con, pstmt, null, rs);" + EOF_LINE);
		sb.append("}" + EOF_LINE);
		return sb.toString();
	}

	public static String getFooterWithOutRS() {
		StringBuffer sb = new StringBuffer();
		sb.append("}" + EOF_LINE);
		sb.append("catch (SQLException e){" + EOF_LINE);
		sb.append("if (log.isErrorEnabled()) {" + EOF_LINE);
		sb
				.append("log.error(\"error when query data. sql:\" + sql + \". message:\"	+ e.getMessage());"
						+ EOF_LINE);
		sb.append("}" + EOF_LINE);
		sb.append("} finally {" + EOF_LINE);
		sb.append("close(con, pstmt, null, null);" + EOF_LINE);
		sb.append("}" + EOF_LINE);
		return sb.toString();
	}
}
