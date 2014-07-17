/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db;

/**
 *
 * @author bruno.weig
 */
public abstract class SQLQuery  {
    protected String query = new String();
    
    public abstract SQLQuery select(String columns);
    
    public abstract SQLQuery from(String tables);
    
    public abstract SQLQuery insert(String table, String columns, String values);
    
    public abstract SQLQuery where();
    
    public abstract SQLQuery _and();
    public abstract SQLQuery _or();
    public abstract SQLQuery _not();
    
    public abstract SQLQuery equal(String key, String value);
    public abstract SQLQuery equal(String key, int value);
    public abstract SQLQuery like(String key, String value);
    
    public String getQuery() {
        return query;
    }
}