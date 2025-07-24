/**
 * Created on : 29/12/2021
 * Author     : Yoann Meclot (DevMyBits)
 * Email      : devmybits@gmail.com
 */
public final class SQLRequest
{
    private static final String SEPARATOR = ", ";

    public static String insert(String table, Column[] columns, Column[] wheres)
    {
        StringBuilder builder = new StringBuilder(String.format("INSERT INTO %s(%s) VALUES(%s)", table, implode(columnKeys(columns)), implode(columnValues(columns))));
        if (wheres != null && wheres.length > 0)
        {
            builder.append(" WHERE ");
            for (int i = 0; i < wheres.length; i++)
                builder.append(String.format("%s = %s" + (i > 0 ? " and " : ""), wheres[i].key, wheres[i].value));
        }
        return builder.append(";").toString();
    }

    public static String select(String table, Column[] columns, Column[] wheres)
    {
        StringBuilder builder = new StringBuilder(String.format("SELECT %s FROM %s", implode(columnKeys(columns)), table));
        if (wheres != null && wheres.length > 0)
        {
            builder.append(" WHERE ");
            for (int i = 0; i < wheres.length; i++)
                builder.append(String.format("%s = %s" + (i > 0 ? " and " : ""), wheres[i].key, wheres[i].value));
        }
        return builder.append(";").toString();
    }

    public static String update(String table, Column[] columns, Column[] wheres)
    {
        StringBuilder builder = new StringBuilder(String.format("UPDATE %s SET ", table));
        for (int i = 0; i < columns.length; i++)
            builder.append(String.format("%s = %s" + (i > 0 ? SEPARATOR : ""), columns[i].key, columns[i].value));
        if (wheres != null && wheres.length > 0)
        {
            builder.append(" WHERE ");
            for (int i = 0; i < wheres.length; i++)
                builder.append(String.format("%s = %s" + (i > 0 ? " and " : ""), wheres[i].key, wheres[i].value));
        }
        return builder.append(";").toString();
    }

    public static String delete(String table, Column[] wheres)
    {
        StringBuilder builder = new StringBuilder(String.format("DELETE FROM %s", table));
        if (wheres != null && wheres.length > 0)
        {
            builder.append(" WHERE ");
            for (int i = 0; i < wheres.length; i++)
                builder.append(String.format("%s = %s" + (i > 0 ? " and " : ""), wheres[i].key, wheres[i].value));
        }
        return builder.append(";").toString();
    }

    public static String selectCountOf(String table, String column)
    {
        return String.format("SELECT COUNT(%s) FROM %s;", column, table);
    }

    public static String obtainCount(String column)
    {
        return String.format("COUNT(%s)", column);
    }

    private static String implode(String[] array)
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++)
        {
            if (i > 0)
                builder.append(SQLRequest.SEPARATOR);
            builder.append(array[i]);
        }
        return builder.toString();
    }

    private static String[] columnValues(Column[] columns)
    {
        String[] values = new String[columns.length];
        for (int i = 0; i < columns.length; i++)
            values[i] = columns[i].value;
        return values;
    }

    private static String[] columnKeys(Column[] columns)
    {
        String[] keys = new String[columns.length];
        for (int i = 0; i < columns.length; i++)
            keys[i] = columns[i].key;
        return keys;
    }

    public static class Column
    {
        public static Column create(String key, String value)
        {
            return new Column(key, value);
        }

        private final String key;
        private final String value;

        private Column(String key, String value)
        {
            this.key = key;
            this.value = value;
        }
    }
}
