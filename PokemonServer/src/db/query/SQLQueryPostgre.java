/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db.query;

import db.SQLQuery;

/**
 *
 * @author bruno.weig
 */
public class SQLQueryPostgre extends SQLQuery {
    @Override
    public SQLQuery select(String columns) {
        query = "SELECT " + columns;
        
        return this;
    }

    @Override
    public SQLQuery from(String tables) {
        query += " FROM " + tables;
        
        return this;
    }

    @Override
    public SQLQuery insert(String table, String columns, String values) {
        String[] _columns = columns.split(",");
        String[] _values = values.split(",");
        
        query = "INSERT INTO " + table + " (";
        
        // Adiciona aspas antes e depois do nome de cada coluna
        for (int i=0; i < _columns.length; i++) {
            query += '"' + _columns[i] + '"';
            
            if ( i < _columns.length - 1 )
                query += ",";
        }
        

        
        // TODO: Verificar se é string ou número
        
        // Adiciona aspa ao redor das strins
        for (int i=0; i < _values.length; i++) {
//            if ( _values[i]  ) {
                
//            }
        }
        
        return this;
    }

    @Override
    public SQLQuery where() {
        return this;
    }

    @Override
    public SQLQuery _and() {
        return this;
    }

    @Override
    public SQLQuery _or() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SQLQuery _not() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SQLQuery equal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SQLQuery like() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
