package com.thiagoivens.babe.database;

public class ScriptDLL {

    public static String getCreateTable(){
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS ROTINA (");
        sql.append("    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append("    DIA DATETIME NOT NULL, ");
        sql.append("    HORA DATETIME NOT NULL, ");
        sql.append("    TIPO VARCHAR(30) NOT NULL ); ");

        return sql.toString();
    }
}
