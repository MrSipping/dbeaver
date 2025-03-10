/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2024 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ext.mssql.model.session;

import org.jkiss.code.NotNull;
import org.jkiss.dbeaver.DBDatabaseException;
import org.jkiss.dbeaver.DBException;
import org.jkiss.dbeaver.ext.mssql.SQLServerConstants;
import org.jkiss.dbeaver.ext.mssql.model.SQLServerDataSource;
import org.jkiss.dbeaver.model.DBPDataSource;
import org.jkiss.dbeaver.model.admin.sessions.DBAServerSessionManager;
import org.jkiss.dbeaver.model.admin.sessions.DBAServerSessionManagerSQL;
import org.jkiss.dbeaver.model.exec.DBCSession;
import org.jkiss.dbeaver.model.exec.jdbc.JDBCPreparedStatement;
import org.jkiss.dbeaver.model.exec.jdbc.JDBCResultSet;
import org.jkiss.dbeaver.model.exec.jdbc.JDBCSession;
import org.jkiss.utils.CommonUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * SQLServer session manager
 */
public class SQLServerSessionManager implements DBAServerSessionManager<SQLServerSession>, DBAServerSessionManagerSQL {

    public static final String OPTION_SHOW_ONLY_CONNECTIONS = "showOnlyConnections";

    private final SQLServerDataSource dataSource;

    public SQLServerSessionManager(SQLServerDataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    @NotNull
    @Override
    public DBPDataSource getDataSource()
    {
        return dataSource;
    }

    @NotNull
    @Override
    public Collection<SQLServerSession> getSessions(@NotNull DBCSession session, @NotNull Map<String, Object> options) throws DBException
    {
        try {
            try (JDBCPreparedStatement dbStat = ((JDBCSession) session).prepareStatement(generateSessionReadQuery(options))) {
                try (JDBCResultSet dbResult = dbStat.executeQuery()) {
                    List<SQLServerSession> sessions = new ArrayList<>();
                    while (dbResult.next()) {
                        sessions.add(new SQLServerSession(dbResult));
                    }
                    return sessions;
                }
            }
        } catch (SQLException e) {
            throw new DBDatabaseException(e, session.getDataSource());
        }
    }

    @Override
    public void alterSession(@NotNull DBCSession session, @NotNull String sessionId, @NotNull Map<String, Object> options) throws DBException
    {
        try {
            try (Statement dbStat = ((JDBCSession) session).createStatement()) {
                dbStat.execute("KILL " + sessionId + "");
            }
        }
        catch (SQLException e) {
            throw new DBDatabaseException(e, session.getDataSource());
        }
    }

    @NotNull
    @Override
    public Map<String, Object> getTerminateOptions() {
        return Map.of();
    }

    @Override
    public boolean canGenerateSessionReadQuery() {
        return true;
    }

    @NotNull
    @Override
    public String generateSessionReadQuery(@NotNull Map<String, Object> options) {
        boolean onlyConnections = CommonUtils.getOption(options, OPTION_SHOW_ONLY_CONNECTIONS);
        boolean supportsDatabaseInfo = dataSource.isServerVersionAtLeast(SQLServerConstants.SQL_SERVER_2012_VERSION_MAJOR, 0);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s.*,");
        if (supportsDatabaseInfo) {
            sql.append("db.name as database_name,");
        } else {
            sql.append("NULL as database_name,");
        }
        sql.append("c.connection_id,(select text from sys.dm_exec_sql_text(c.most_recent_sql_handle)) as sql_text\n")
            .append("FROM sys.dm_exec_sessions s\n");
        if (onlyConnections) {
            sql.append("LEFT OUTER ");
        }
        sql.append("JOIN sys.dm_exec_connections c ON c.session_id=s.session_id\n");
        if (supportsDatabaseInfo) {
            sql.append("LEFT OUTER JOIN sys.sysdatabases db on db.dbid=s.database_id\n");
        }
        sql.append("ORDER BY s.session_id DESC");
        return sql.toString();
    }
}
