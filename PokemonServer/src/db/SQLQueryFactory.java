/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db;

import db.SQLConnection.SGBD;
import db.query.SQLQueryPostgre;

/**
 *
 * @author bruno.weig
 */
public class SQLQueryFactory {
    static public SQLQuery factoryQuery(SGBD sgbd) {
        SQLQuery query = null;
        
        switch ( sgbd ) {
            case Postgre:
                query = new SQLQueryPostgre();
                break;
            default:
                return null;
        }
        
        return query;
    }
}
