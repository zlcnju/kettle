package com.fr.distributedOutput;

import org.pentaho.di.core.annotations.Step;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.*;

/**
 * Created by Licheng.zhu on 2017/7/20.
 */
@Step(
        id = "DistributedOutput.TransformName",
        name = "zlc.Name",
        description = "zlc.Description",
        categoryDescription = "DistributedOutput.TransformName",
        image = "com/fr/distributedOutput/image/distributed.png",
        i18nPackageName = "com.fr.distributedOutput")
public class distributedOutputMeta extends BaseStepMeta implements StepMetaInterface {
    @Override
    public void setDefault() {

    }

    @Override
    public StepInterface getStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans) {
        return null;
    }

    @Override
    public StepDataInterface getStepData() {
        return null;
    }
}
