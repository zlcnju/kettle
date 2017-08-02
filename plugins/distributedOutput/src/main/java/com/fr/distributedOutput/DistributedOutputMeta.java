package com.fr.distributedOutput;

import org.pentaho.di.core.annotations.Step;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.shared.SharedObjectInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.*;
import org.pentaho.metastore.api.IMetaStore;
import org.w3c.dom.Node;

import java.util.List;

/**
 * Created by Licheng.zhu on 2017/7/20.
 */
@Step(
        id = "DistributedOutput.fullLoad",
        name = "DistributedOutput.StepName",
        description = "DistributedOutput.Description",
        categoryDescription = "DistributedOutput.TransformName",
        image = "com/fr/distributedOutput/image/distributed.png",
        i18nPackageName = "com.fr.DistributedOutput")
public class DistributedOutputMeta extends BaseStepMeta implements StepMetaInterface {
    private String dbName;
    private String tableName;
    private String propertyFileName;
    private boolean useTempTable;
    private String tempTableName;

    @Override
    public void loadXML(Node stepnode, List<DatabaseMeta> databases, IMetaStore metaStore) throws KettleXMLException {
        readData(stepnode, databases);
    }

    private void readData(Node stepnode, List<? extends SharedObjectInterface> databases) throws KettleXMLException {
        try {
            dbName = XMLHandler.getTagValue(stepnode, "dbName");
            tableName = XMLHandler.getTagValue(stepnode, "tableName");
            propertyFileName = XMLHandler.getTagValue(stepnode, "propertyFileName");
            tempTableName = XMLHandler.getTagValue(stepnode, "tempTableName");
            useTempTable = "true".equalsIgnoreCase(XMLHandler.getTagValue(stepnode, "useTempTable"));
        } catch (Exception e) {
            throw new KettleXMLException(Messages.getString(
                    "DistributedOutputMeta.Exception.UnableToReadStepInfoFromXML"), e);
        }
    }

    @Override
    public String getXML() {
        StringBuffer retval = new StringBuffer(100);
        retval.append("    ").append(XMLHandler.addTagValue("dbName", dbName));
        retval.append("    ").append(XMLHandler.addTagValue("tableName", tableName));
        retval.append("    ").append(XMLHandler.addTagValue("propertyFIleName", propertyFileName));
        retval.append("    ").append(XMLHandler.addTagValue("tempTableName", tempTableName));
        retval.append("    ").append(XMLHandler.addTagValue("useTempTable", useTempTable ? "true" : "false"));
        return retval.toString();
    }

    @Override
    public void setDefault() {
        dbName = "";
        tableName = "";
        propertyFileName = "";
    }

    @Override
    public StepInterface getStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans) {
        return new DistributedOutput(stepMeta, stepDataInterface, copyNr, transMeta, trans);
    }

    @Override
    public StepDataInterface getStepData() {
        return new DistributedOutputData();
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

    public String getPropertyFileName() {
        return propertyFileName;
    }

    public void setPropertyFileName(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    public boolean isUseTempTable() {
        return useTempTable;
    }

    public void setUseTempTable(boolean useTempTable) {
        this.useTempTable = useTempTable;
    }

    public String getTempTableName() {
        return tempTableName;
    }

    public void setTempTableName(String tempTableName) {
        this.tempTableName = tempTableName;
    }
}
