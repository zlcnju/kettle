package com.fr.distributedOutput;

import client.TableLocation;
import client.TableOperate;
import client.TableTypeInfo;
import com.fr.engine.constant.Type;

import java.util.List;

/**
 * Created by Licheng.zhu on 2017/8/3.
 */
public class TableUtil {
    public static void createTable(String dbName, String tableName, String[] columnNames, String[] columnTypes){
        TableTypeInfo.Builder builder = new TableTypeInfo.Builder();
        for(int i = 0; i < columnNames.length; i++){
            if(StringUtils.isEmpty(columnNames[i]) && StringUtils.isEmpty(columnTypes[i])){
                continue;
            }

            if(StringUtils.isEmpty(columnNames[i]) || StringUtils.isEmpty(columnTypes[i])){
                throw new RuntimeException("uncorrect columnName or type");
            }

            try {
                Type type = ColumnTypeInstance.INSTANCE.getType(columnTypes[i]);
                builder.appendColumn(columnNames[i], type);
            }catch(Exception e) {
                throw new RuntimeException("uncorrect type");
            }
        }

        TableOperate.Default().createTable(new TableLocation(dbName, tableName), builder.build());
    }

    public static void deleteTable(String dbName, String tableName){
        TableOperate.Default().deleteTable( new TableLocation(dbName, tableName));
    }

}
