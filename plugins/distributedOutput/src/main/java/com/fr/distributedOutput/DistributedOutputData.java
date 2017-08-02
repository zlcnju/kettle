package com.fr.distributedOutput;

import common.exception.DSTableNotExistException;
import db.DB;
import db.table.WritableTable;
import direct.ExecuteContext;
import jxl.demo.Write;
import local.LocalWriteTable;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;

/**
 * Created by Licheng.zhu on 2017/7/31.
 */
public class DistributedOutputData extends BaseStepData implements StepDataInterface {
    private WritableTable writableTable;
    private String tempTableName;
    private String dbName;
    private String tableName;
    private boolean useTempTable;
    protected RowMetaInterface outputRowMeta;

    public WritableTable getWritableTable() {
        return writableTable;
    }

    public void initWriteTable(String dbName, String tableName) throws DSTableNotExistException {
        DB db = ExecuteContext.getDB(dbName);
        writableTable = db.openWritableTable(tableName);
    }

    public void setWritableTable(WritableTable writableTable) {
        this.writableTable = writableTable;
    }

    public String getTempTableName() {
        return tempTableName;
    }

    public void setTempTableName(String tempTableName) {
        this.tempTableName = tempTableName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isUseTempTable() {
        return useTempTable;
    }

    public void setUseTempTable(boolean useTempTable) {
        this.useTempTable = useTempTable;
    }
}
