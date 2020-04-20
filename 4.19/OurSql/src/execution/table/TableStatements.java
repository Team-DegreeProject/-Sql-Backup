package execution.table;

import java.util.List;
import static parsing.SqlParserConstants.*;


public class TableStatements {

    public static void createTable(List tokens){
        try {
            CreateTableStatement createTableStatement=new CreateTableStatement(tokens);
            createTableStatement.createImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dropTable(List tokens){
        try {
            DropTableStatement dropTableStatement=new DropTableStatement(tokens);
            dropTableStatement.dropTableImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public static void renameTable(List tokens){
        try {
            RenameTableStatement renameTableStatement = new RenameTableStatement(tokens);
            renameTableStatement.renameTableImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void alterTable(List tokens){
        try {
            AlterTableStatement alterTableStatement=new AlterTableStatement(tokens);
            alterTableStatement.alterTableImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//            Object o=tokens.get(3);
//            if(o instanceof Token){
//                int type=((Token)tokens.get(3)).kind;
//                if (type == MODIFY) {
//                    alterTableStatement.alterModifyImpl();
//                }
//            }else if (o instanceof List){
//                List<List> l= (List) tokens.get(3);
//                int t=((Token)l.get(0).get(0)).kind;
//                if(t== ADD){
//                    alterTableStatement.alterTableAddColumnStatement();
//                }else if (t==DROP){
//                    alterTableStatement.alterTableDropImpl();
//                }

//            }
    }

    public static void truncateTable(List tokens){
        try {
            TruncateTableStatement truncateTableStatement=new TruncateTableStatement(tokens);
            truncateTableStatement.truncateTableImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
