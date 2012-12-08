package util;

import java.net.UnknownHostException;
import java.sql.*;
import java.util.*;
import java.io.*;
import org.postgresql.Driver;

public class DBUtil {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		DBUtil db1 = new DBUtil();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = "";
		try {
			while ((s = br.readLine()) != null) {
				if (s.equals("#"))
					break;
				System.out.println(s);
				if (s.toLowerCase().startsWith("select")) {
					ResultSet rs = db1.executeQuerySQL(s);
					while (rs.next()) {
						System.out.println(rs.getString(1));
					}
				} else {
					int r = db1.executeUpdateSQL(s);
					System.out.println("rows affected:" + r);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		db1.rundown();
	}

	void createUserTable() {

	}

	void test() {

	}

	static Connection _conn = null;

	static PreparedStatement _stmt_insert_page = null;
	static PreparedStatement _stmt_insert_event = null;
	static PreparedStatement _stmt_insert_mouse = null;
	static PreparedStatement _stmt_insert_content = null;

	String _url = "";
	String _user = "";
	String _pwd = "";

	public void createConn() {
		try {
			if (_conn != null) {
				try {
					_conn.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			System.err.println("% creating connection...");
			Properties props = new Properties();
			props.setProperty("user", _user);
			props.setProperty("password", _pwd);
			// props.setProperty("ssl","true");
			_conn = DriverManager.getConnection(_url+_database, props);
			//_conn.setAutoCommit(false);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private static String _database = "emu";

	public static void setDatabase(String s) {
		_database = s;
	}

	public DBUtil() {		
		String postgresUrl = "jdbc:postgresql://localhost:5432/";		
		String postgresClassName = "org.postgresql.Driver";
		String database = "emu";
		String user = "postgres";
		String pwd = "P@ssw0rd";
		init(postgresUrl, database, postgresClassName, user, pwd);
	}
	
	public DBUtil(String url, String database, String driverClassName, String user, String pwd) {
		init(url, database, driverClassName, user, pwd);
	}
	
	public void init(String url, String database, String driverClassName, String user, String pwd) {
		_database = database;
		_url = url;
		String fullUrl = _url + _database;
		_user = user;
		_pwd = pwd;
		
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		// TODO: create a regular user!

		try {
			if (_conn == null || _conn.isClosed()) {
				System.err.println("%connecting to " + fullUrl + "...");
				createConn();
				_conn.setAutoCommit(false);
			}
			if (_stmt_insert_page == null || ((Connection) _stmt_insert_page).isClosed()) {
				System.err.println("%creating page preparedstatement...");
				_stmt_insert_page = _conn.prepareStatement(_sql_insert_page);
			}
			if (_stmt_insert_event == null || ((Connection) _stmt_insert_event).isClosed()) {
				System.err.println("%creating event preparedstatement...");
				_stmt_insert_event = _conn.prepareStatement(_sql_insert_event);
			}
			if (_stmt_insert_mouse == null || ((Connection) _stmt_insert_mouse).isClosed()) {
				System.err.println("%creating mouse preparedstatement...");
				_stmt_insert_mouse = _conn.prepareStatement(_sql_insert_mouse);
			}
			if (_stmt_insert_content == null || ((Connection) _stmt_insert_content).isClosed()) {
				System.err.println("%creating content preparedstatement...");
				_stmt_insert_content = _conn.prepareStatement(_sql_insert_content);
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void commit() {
		try {
			_conn.commit();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void rundown() {
		System.out.println("% database rundown.");
		try {
			_stmt_insert_page.close();
			_stmt_insert_event.close();
			_stmt_insert_content.close();
			_conn.commit();
			_conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public boolean execute(String sql) {
		validateConn();
		try {
			Statement stmt = _conn.createStatement();
			return stmt.execute(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public ResultSet executeQuerySQL(String sql) {
		validateConn();
		try {
			Statement stmt = _conn.createStatement();
			return stmt.executeQuery(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public int executeUpdateSQL(String sql) {
		validateConn();
		try {
			Statement stmt = _conn.createStatement();
			return stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	void validateConn() {
		try {
			if (_conn == null) {
				System.err.println("creating connection...");
				Properties props = new Properties();
				props.setProperty("user", _user);
				props.setProperty("password", _pwd);
				// props.setProperty("ssl","true");
				_conn = DriverManager.getConnection(_url, props);
				_conn.setAutoCommit(false);
			}

			if (_conn.isClosed()) {
				System.err.println("creating connection...");
				Properties props = new Properties();
				props.setProperty("user", _user);
				props.setProperty("password", _pwd);
				// props.setProperty("ssl","true");
				_conn = DriverManager.getConnection(_url, props);
				_conn.setAutoCommit(false);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	boolean checkIfPageExists(String page_sha1) {
		validateConn();
		ResultSet rs = executeQuerySQL("select * from emu_pages where page_sha1='"
				+ page_sha1 + "'");
		// System.out.println("select * from libx_pages where id='" + id + "'");
		// ResultSet rs = executeQuerySQL("select * from libx_pages");
		try {
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	String _sql_insert_page = 
		"insert into emu_pages (" 
		+ "page_sha1, " 
		+ "time, "
		+ "wid, "
		+ "tab, " 
		+ "type, " 
		+ "url, " 
		+ "ref, "		
		+ "ip, "
		+ "user_id, "
		+ "is_search, "
		+ "engine, "
		+ "vertical "
		+ ") values (?,?,?,?,?,?,?,?,?,?,?,?)";

	public boolean insertPage(String page_sha1, Long time, String wid, String tab,
			String type, String url, String ref, String ip, Integer userID, Boolean isSearch, String engine, String vertical) throws UnknownHostException {
		if (checkIfPageExists(page_sha1))
			return false;
		// System.out.println("inserting page..." + id);
		validateConn();
		boolean rc = true;
		try {
			_stmt_insert_page.setString(1, page_sha1);
			Timestamp ts = new Timestamp(time);
			_stmt_insert_page.setTimestamp(2, ts);
			_stmt_insert_page.setString(3, wid);
			_stmt_insert_page.setString(4, tab);
			_stmt_insert_page.setString(5, type);
			_stmt_insert_page.setString(6, url);
			_stmt_insert_page.setString(7, ref);			
			_stmt_insert_page.setString(8, ip);
			_stmt_insert_page.setInt(9, userID);
			_stmt_insert_page.setBoolean(10, isSearch);
			_stmt_insert_page.setString(11, engine);
			_stmt_insert_page.setString(12, vertical);
			_stmt_insert_page.execute();
		} catch (SQLException ex) {
			rc = false;
			ex.printStackTrace();
		}
		return rc;
	}

	String _sql_insert_event = 
		"insert into emu_events (" 
		+ "event_sha1, "
		+ "time, "
		+ "ev, "
		+ "url, "
		+ "ref, "
		+ "ip, "
		+ "user_id, " 
		+ "content_sha1, "
		+ "cx, "
		+ "cy, " 
		+ "x, " 
		+ "y, " 
		+ "scrlX, " 
		+ "scrlY, "
		+ "iw, " 
		+ "ih, " 
		+ "ow, " 
		+ "oh, " 
		+ "scrlW, "
		+ "scrlH, " 
		+ "dom_path, " 
		+ "targ_id, "
		+ "is_doc_area, " 
		+ "duration, " 
		+ "select_text, " 
		+ "button "
		+ ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	boolean checkIfEventExists(String event_sha1) {
		validateConn();
		ResultSet rs = executeQuerySQL("select * from emu_events where event_sha1='"
				+ event_sha1 + "'");
		// System.out.println("select * from libx_pages where id='" + id + "'");
		// ResultSet rs = executeQuerySQL("select * from libx_pages");
		try {
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	/**
	 * skip page_id, content_id, 
	 * 
	 * @param event_sha1
	 * @param time
	 * @param ev
	 * @param url
	 * @param ref
	 * @param ip
	 * @param content_sha1
	 * @param cx
	 * @param cy
	 * @param x
	 * @param y
	 * @param scrlX
	 * @param scrlY
	 * @param iw
	 * @param ih
	 * @param ow
	 * @param oh
	 * @param scrlW
	 * @param scrlH
	 * @param dom_path
	 * @param element_id
	 * @param is_doc_area
	 * @param duration
	 * @param select_text
	 * @param button
	 * @return
	 */
	public boolean insertEvent(
			String event_sha1, 
			Long time, 
			String ev,			
			String url, 
			String ref, 
			String ip,
			Integer userID,
			String content_sha1, 
			Integer cx,
			Integer cy, 
			Integer x, 
			Integer y, 
			Integer scrlX, 
			Integer scrlY,
			Integer iw, 
			Integer ih, 
			Integer ow, 
			Integer oh, 
			Integer scrlW,
			Integer scrlH, 
			String dom_path, 
			String targ_id,
			Boolean is_doc_area, 
			Integer duration, 
			String select_text, 
			String button) {
		if (checkIfEventExists(event_sha1))
			return false;
		// System.out.println("inserting page..." + id);
		validateConn();
		boolean rc = true;
		try {
			_stmt_insert_event.setString(1, event_sha1);
			Timestamp ts = new Timestamp(time);
			_stmt_insert_event.setTimestamp(2, ts);
			stmtSetString(_stmt_insert_event, 3, ev);
			stmtSetString(_stmt_insert_event, 4, url);
			stmtSetString(_stmt_insert_event, 5, ref);
			stmtSetString(_stmt_insert_event, 6, ip);
			stmtSetInt(_stmt_insert_event, 7, userID);
			stmtSetString(_stmt_insert_event, 8, content_sha1);
			stmtSetInt(_stmt_insert_event, 9, cx);
			stmtSetInt(_stmt_insert_event, 10, cy);
			stmtSetInt(_stmt_insert_event, 11, x);
			stmtSetInt(_stmt_insert_event, 12, y);
			stmtSetInt(_stmt_insert_event, 13, scrlX);
			stmtSetInt(_stmt_insert_event, 14, scrlY);
			stmtSetInt(_stmt_insert_event, 15, iw);
			stmtSetInt(_stmt_insert_event, 16, ih);
			stmtSetInt(_stmt_insert_event, 17, ow);
			stmtSetInt(_stmt_insert_event, 18, oh);
			stmtSetInt(_stmt_insert_event, 19, scrlW);
			stmtSetInt(_stmt_insert_event, 20, scrlH);
			stmtSetString(_stmt_insert_event, 21, targ_id);
			stmtSetString(_stmt_insert_event, 22, dom_path);
			stmtSetBoolean(_stmt_insert_event, 23, is_doc_area);
			stmtSetInt(_stmt_insert_event, 24, duration);			
			stmtSetString(_stmt_insert_event, 25, select_text);
			stmtSetString(_stmt_insert_event, 26, button);
			_stmt_insert_event.execute();
		} catch (SQLException ex) {
			rc = false;
			ex.printStackTrace();
		}
		return rc;
	}
	
	String _sql_insert_mouse = 
		"insert into emu_mouses (" 
		+ "event_id, "
		+ "time, "
		+ "ev, "
		+ "page_id, "
		+ "ip, "
		+ "user_id, " 
		+ "x, " 
		+ "y, " 
		+ "xdist, " 
		+ "ydist, "
		+ "is_serp, "
		+ "res_rand, "
		+ "task, "
		+ "founded, "
		+ "has_image, "
		+ "has_fixation "
		+ ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	boolean checkIfMouseExists(int event_id) {
		validateConn();
		ResultSet rs = executeQuerySQL("select * from emu_mouses where event_id='"
				+ event_id + "'");
		// System.out.println("select * from libx_pages where id='" + id + "'");
		// ResultSet rs = executeQuerySQL("select * from libx_pages");
		try {
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean insertMouse(
			Integer eventID,
			Long time,
			String ev,
			Integer pageID,
			String ip,
			Integer userID, 
			Integer x, 
			Integer y, 
			Integer xdist, 
			Integer ydist,
			Boolean isSERP,
			Boolean res_rand,
			String task,
			Boolean founded,
			Boolean has_image,
			Boolean has_fixation) {
		if (checkIfMouseExists(eventID))
			return false;
		// System.out.println("inserting page..." + id);
		validateConn();
		boolean rc = true;
		try {
			_stmt_insert_mouse.setInt(1, eventID);
			Timestamp ts = new Timestamp(time);
			_stmt_insert_mouse.setTimestamp(2, ts);
			stmtSetString(_stmt_insert_mouse, 3, ev);
			stmtSetInt(_stmt_insert_mouse, 4, pageID);
			stmtSetString(_stmt_insert_mouse, 5, ip);
			stmtSetInt(_stmt_insert_mouse, 6, userID);
			stmtSetInt(_stmt_insert_mouse, 7, x);
			stmtSetInt(_stmt_insert_mouse, 8, y);
			stmtSetInt(_stmt_insert_mouse, 9, xdist);
			stmtSetInt(_stmt_insert_mouse, 10, ydist);
			stmtSetBoolean(_stmt_insert_mouse, 11, isSERP);
			stmtSetBoolean(_stmt_insert_mouse, 12, res_rand);
			stmtSetString(_stmt_insert_mouse, 13, task);
			stmtSetBoolean(_stmt_insert_mouse, 14, founded);
			stmtSetBoolean(_stmt_insert_mouse, 15, has_image);
			stmtSetBoolean(_stmt_insert_mouse, 16, has_fixation);		
			
			_stmt_insert_mouse.execute();
		} catch (SQLException ex) {
			rc = false;
			ex.printStackTrace();
		}
		return rc;
	}
	
	public static void stmtSetInt(PreparedStatement stmt, int index, Integer val) throws SQLException {
		if (val != null) {
			stmt.setInt(index, val);
		} else {
			stmt.setNull(index, Types.INTEGER);
		}
	}
	
	public static void stmtSetBoolean(PreparedStatement stmt, int index, Boolean val) throws SQLException {
		if (val != null) {
			stmt.setBoolean(index, val);
		} else {
			stmt.setNull(index, Types.BOOLEAN);
		}
	}
	
	public static void stmtSetString(PreparedStatement stmt, int index, String val) throws SQLException {
		stmt.setString(index, val);
	}
	
	String _sql_insert_content = 
		"insert into emu_contents (" 
		+ "content_sha1, "
//		+ "time, "
//		+ "url, "		
		+ "data, "
//		+ "type, "
		+ "length "
		+ ") values (?,?,?)";

	boolean checkIfContentExists(String content_sha1) {
		validateConn();
		ResultSet rs = executeQuerySQL("select * from emu_contents where content_sha1='"
				+ content_sha1 + "'");
		// System.out.println("select * from libx_pages where id='" + id + "'");
		// ResultSet rs = executeQuerySQL("select * from libx_pages");
		try {
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public boolean insertContent(String content_sha1, Long time, String url, String data, String type, 
			int length) {
		if (checkIfContentExists(content_sha1))
			return false;
		// System.out.println("inserting page..." + id);
		validateConn();
		boolean rc = true;
		try {			
			_stmt_insert_content.setString(1, content_sha1);
//			Timestamp ts = new Timestamp(time);
//			_stmt_insert_content.setTimestamp(2, ts);
//			_stmt_insert_content.setString(3, url);
			_stmt_insert_content.setString(2, data);
//			_stmt_insert_content.setString(5, type);
			_stmt_insert_content.setInt(3, length);
			_stmt_insert_content.execute();
		} catch (SQLException ex) {
			rc = false;
			ex.printStackTrace();
		}
		return rc;
	}
}