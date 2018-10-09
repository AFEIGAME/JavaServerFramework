package core.dataBase;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.config.ConfigService;
import core.dataBase.ConnectionPool.PooledConnection;
import core.log.LogService;

public class DatabaseService {
	String m_ModelName = "DataBaseService";
	String m_ErrorSql = "ErrorSQL";

	String m_DriverString = "com.mysql.jdbc.Driver";

	String m_DataBaseName;
	int m_dataBaseMaxRequest;

	List<String> m_SQLlist = new ArrayList<String>();
	List<String> m_ErrorSQLlist = new ArrayList<String>();

	static DatabaseService m_Instance;

	public static DatabaseService GetInstance() {
		if (m_Instance == null) {
			m_Instance = new DatabaseService();
			m_Instance.Init();
		}
		return m_Instance;
	}

	public void Init() {
		m_DataBaseName = ConfigService.GetString("DataBase", "DataBaseName", "DefaultDataBase");
		m_dataBaseMaxRequest = ConfigService.GetInt("DataBase", "dataBaseMaxRequest", 5);
	}

	// ��
	public void InsertData(String tableName, String[] keys, String[] values) {
		String SQL = "INSERT INTO " + m_DataBaseName + "." + tableName;

		String Value = "";
		String Keys = "";

		for (int i = 0; i < keys.length; i++) {
			Keys += keys[i];

			if (i != keys.length - 1) {

				Keys += ",";

			}
		}
		
		for (int i = 0; i < values.length; i++) {
			Value +=values[i];

			if (i != values.length - 1) {

				Value += ",";

			}
		}

		SQL = SQL + "(" + Keys + ")" + "VALUES" + "(" + Value + ");";

		ExecuteUpdate(SQL);
	}

	// ɾ
	public void DeleteData(String tableName, String where) {
		String SQL = "DELETE FROM " + m_DataBaseName + "." + tableName;

		SQL = SQL + " WHERE " + where;

		ExecuteUpdate(SQL);
	}

	// ��
	public void UpdateData(String tableName, String[] keys,String[] Values, String where) {
		String SQL = "UPDATE " + m_DataBaseName + "." + tableName + " set ";
		String content = "";

		for (int i = 0; i < keys.length; i++) {
			content += keys[i] + " = " + Values[i];
			
			if (i != keys.length - 1) {
				content += ",";
			}
		}
	
		SQL = SQL + content + " WHERE " + where;

		ExecuteUpdate(SQL);
	}

	// ��
	public List<Map<String, Object>> SelectData(String tableName, String[] keys, String where) {
		String SQL = "SELECT ";
		String keyString = "";

		if (keys == null || keys.length == 0) {
			keyString = "*";
		} else {
			for (int i = 0; i < keys.length; i++) {
				keyString += keys[i];

				if (i != keys.length - 1) {
					keyString += ",";
				}
			}
		}

		if (where != null && where != "") {
			SQL = SQL + keyString + "FROM " + m_DataBaseName + "." + tableName + " WHERE " + where;
		} else {
			SQL = SQL + keyString + "FROM " + m_DataBaseName + "." + tableName;
		}

		return ExecuteQuery(SQL);
	}

	// ��ѯ���ݿ⣬���ؽ��
	public List<Map<String, Object>> ExecuteQuery(String sql) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		PooledConnection connection = null;

		try {
			long time = System.currentTimeMillis();

			connection = DBManager.getConnection();
			ResultSet rs = connection.executeQuery(sql);
			list = Ret2Map(rs);
			rs.close();

			long useTime = (System.currentTimeMillis() - time) / 1000;
			LogService.Log(m_ModelName, "���ݿⷵ��         ��ʱ" + useTime + "s");

		} catch (Exception e) {
			LogService.Exception(m_ModelName, "ExecuteQuery Error", e);
		} finally {
			connection.close();
		}

		return list;

	}

	// �������ݿ�
	public void ExecuteUpdate(String sql) {
		PooledConnection connection = null;

		try {
			long time = System.currentTimeMillis();

			connection = DBManager.getConnection();
			connection.executeUpdate(sql);

			long useTime = (System.currentTimeMillis() - time) / 1000;

			LogService.Log(m_ModelName, "sql �����ɹ� ��ʱ" + useTime + "s ");
		} catch (Exception e) {
			connection.close();
			LogService.Error(m_ModelName, "ErrorSql: :\n" + sql);
			LogService.Exception(m_ModelName, "SQL Exception" + e.toString(), e);
		} finally {
			connection.close();
		}
	}

	void TimerTask() {
		if (m_SQLlist.size() > 0) {

		}

	}

	public List<Map<String, Object>> Ret2Map(ResultSet rs) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {

			ResultSetMetaData md = rs.getMetaData();

			int columnCount = md.getColumnCount();

			while (rs.next()) {

				Map<String, Object> map = new HashMap<String, Object>();

				for (int i = 1; i <= columnCount; i++) {
					map.put(md.getColumnName(i), rs.getObject(i));

				}

				list.add(map);
			}
		} catch (Exception e) {
			LogService.Exception(m_ModelName, "Ret2Map Error" + e.toString(), e);
		}

		return list;
	}
}
