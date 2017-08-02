package com.fr.distributedOutput;

import client.TableOperate;
import com.fr.engine.local.config.DistributedPropertiesLoader;
import db.table.row.Row;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.*;

/**
 * Created by Licheng.zhu on 2017/7/24.
 */
public class DistributedOutput extends BaseStep implements StepInterface {
    private DistributedOutputMeta meta;
    private DistributedOutputData data;

    /**
     * This is the base step that forms that basis for all steps. You can derive from this class to implement your own
     * steps.
     *
     * @param stepMeta          The StepMeta object to run.
     * @param stepDataInterface the data object to store temporary data, database connections, caches, result sets,
     *                          hashtables etc.
     * @param copyNr            The copynumber for this step.
     * @param transMeta         The TransInfo of which the step stepMeta is part of.
     * @param trans             The (running) transformation to obtain information shared among the steps.
     */
    public DistributedOutput(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans) {
        super(stepMeta, stepDataInterface, copyNr, transMeta, trans);
    }

    @Override
    public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException {
        this.meta = (DistributedOutputMeta) smi;
        this.data = (DistributedOutputData) sdi;

        Object[] r = this.getRow();
        if (r == null) {
            data.getWritableTable().commit();
            if (meta.isUseTempTable()) {
                TableOperate.Default().renameTable(data.getDbName(), data.getTempTableName(), data.getTableName());
            }
            return false;
        }

        if (this.first) {
            this.first = false;
            DistributedPropertiesLoader.load(meta.getPropertyFileName());
            initDistributedOutputData();
        }
        data.getWritableTable().appendRow(Row.Default.create(r));
        this.putRow(data.outputRowMeta, r);
        return true;
    }

    private void initDistributedOutputData() {
        data.setDbName(this.environmentSubstitute(meta.getDbName()));
        data.setTableName(this.environmentSubstitute(meta.getTableName()));
        data.setTempTableName(this.environmentSubstitute(meta.getTempTableName()));
        data.setUseTempTable(meta.isUseTempTable());
        if (!meta.isUseTempTable()) {
            data.initWriteTable(data.getDbName(), data.getTableName());
        } else {
            TableOperate.Default().copyTableStructure(data.getDbName(), data.getTableName(), data.getTempTableName());
            data.initWriteTable(data.getDbName(), data.getTempTableName());
        }
        data.outputRowMeta = this.getInputRowMeta().clone();
    }
}
