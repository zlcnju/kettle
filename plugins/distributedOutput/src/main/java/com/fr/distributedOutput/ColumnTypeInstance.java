package com.fr.distributedOutput;

import com.fr.engine.constant.Type;
import com.fr.engine.constant.TypeVisitor;
import com.fr.engine.constant.TypeVoidVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Licheng.zhu on 2017/8/3.
 */
public enum ColumnTypeInstance {
    INSTANCE;
    private static Map<String, Column> columnList;
    public static class Column{
        protected String typeName;
        protected Type type;

        public Column(String typeName, Type type) {
            this.typeName = typeName;
            this.type = type;
        }
    }

    public static class TypeVisitorImpl implements TypeVisitor<String> {

        @Override
        public String visitBool() {
            return "bool";
        }

        @Override
        public String visitInt() {
            return  "int";
        }

        @Override
        public String visitFloat() {
            return "float";
        }

        @Override
        public String visitLong() {
            return "long";
        }

        @Override
        public String visitDouble() {
            return "double";
        }

        @Override
        public String visitString() {
            return "string";
        }

        @Override
        public String visitDate() {
            return "date";
        }

        @Override
        public String visitTime() {
            return "time";
        }

        @Override
        public String visitTimestamp() {
            return "timestamp";
        }

    }

    private ColumnTypeInstance(){
        init();
    }

    private void init(){
        columnList = new HashMap<>(20);
        TypeVisitor<String> visitor = new TypeVisitorImpl();
        for(Type type: Type.values()){
            String typeName = type.accept(visitor);
            columnList.put(typeName, new Column(typeName, type));
        }
    }

    public String[] getTypeNames(){
        return (String[]) columnList.keySet().toArray(new String[columnList.size()]);
    }

    public Type getType(String typeName){
        return columnList.get(typeName).type;
    }
}
