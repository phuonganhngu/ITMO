package serverudp;

import classes.Human;
import java.awt.*;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ORMUtils1 {

    static Set<String> modifys = new HashSet<>();

    //connect to the database
    public static Connection getConn() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/postgres";//The MySQL connection URL, do not explain
        String user = "postgres";//MySQL users
        String password = "becung";//MySQL password
        return DriverManager.getConnection(url, user, password);//Get connected
    }

    public static List<Human> doRead(Human h) throws Exception{
        return doRead(h.getClass());
    }
    public static List<Human> doRead(Class<?> clazz) throws Exception {
        List<Field> fieldList = new LinkedList<>();
        getAllFeild(fieldList, clazz);
        LinkedList<Human> ll = new LinkedList<>();

// Query SQL, according to the primary key retrieval, using the definition attribute name here. Can use the configuration, or the other way
        String sql = "SELECT * FROM " + clazz.getSimpleName() + ";";
        System.out.println(sql);
        try (Connection conn = getConn();
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Human data = (Human) clazz.newInstance();

                for (Field field : fieldList) {
//Attribute assignment
                    setValue(result, data, field);
                }
                ll.add(data);
            }

        }
        return (List<Human>) ll;
    }
    
    public static Integer getNextId() throws Exception 
    {    
        String sql = "select max(humanid) from Human"; 
        try (Connection conn = getConn();
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {
                return result.getInt(1);
            }
    }

    //INSERT
    public static void doInsert(Human data) throws Exception {
        List<Field> fieldList = new LinkedList<>();
        getAllFeild(fieldList, data.getClass());

        StringBuffer keys = new StringBuffer();
        StringBuffer values = new StringBuffer();
        for (Field field : fieldList) {
            if (keys.length() > 0) {
                keys.append(",");
                values.append(",");
            }
            keys.append(field.getName());
            values.append("?");
        }
        //Formation of the insert SQL, don't do the correlation processing (left, right, inline etc.)
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ");
//Here is the table name, I called the name of the table; you can use the hibernate similar to the XML configuration, annotations etc.
        sql.append(data.getClass().getSimpleName());
        sql.append(" ( ").append(keys).append(") VALUES (").append(values.toString()).append(");");

        // System.out.println(sql);
        try (Connection conn = getConn(); PreparedStatement statement = conn.prepareStatement(sql.toString())) {
            int index = 1;
            for (Field field : fieldList) {
                //      System.out.println(index + " " + data + " " + field );
                setStatement(statement, index, data, field);
                index++;
            }
            System.out.println(statement);
            statement.executeUpdate();
        }

    }

    //DELETE
    public static void doDelete(Human data) throws Exception {
        doDelete(data.getClass(), data.getId());
    }

    public static void doDelete(Class<?> clazz, Object Id) throws Exception {
        List<Field> fieldList = new LinkedList<>();
        getAllFeild(fieldList, clazz);

        Field key = null;
        for (Field field : fieldList) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                key = field;
                break;
            }
        }

        String sql = "DELETE FROM " + clazz.getSimpleName() + " WHERE " + key.getName() + " LIKE " + "'%" + Id + "'" + ";";
        System.out.println(sql);
        try (Connection conn = getConn();
                PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.executeUpdate();
        }
        System.out.println(sql);
    }

    private static void setStatement(PreparedStatement statement, int index, Human data, Field field) throws Exception {
        boolean isAccess = field.isAccessible();
        try {
//Close the safety check, or read not to private property
            field.setAccessible(true);
            if (field.getType() == byte.class || field.getType() == Byte.class) {
                statement.setByte(index, field.getByte(data));
            } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                statement.setBoolean(index, field.getBoolean(data));
            } else if (field.getType() == short.class || field.getType() == Short.class) {
                statement.setShort(index, field.getShort(data));
            } else if (field.getType() == char.class || field.getType() == Character.class) {
                statement.setString(index, String.valueOf(field.getChar(data)));
            } else if (field.getType() == int.class || field.getType() == Integer.class) {
                statement.setInt(index, field.getInt(data));
            } else if (field.getType() == float.class || field.getType() == Float.class) {
                statement.setFloat(index, field.getFloat(data));
            } else if (field.getType() == long.class || field.getType() == Long.class) {
                statement.setLong(index, field.getLong(data));
            } else if (field.getType() == double.class || field.getType() == Double.class) {
                statement.setDouble(index, field.getDouble(data));
            } else if (field.getType() == String.class) {
                statement.setString(index, field.get(data).toString());
            } else if (field.getType() == LocalDateTime.class){
                LocalDateTime localDateTime = (LocalDateTime) field.get(data);
                statement.setTimestamp(index, Timestamp.valueOf(localDateTime));
            } else if (field.getType() == Color.class) {
                Color color = (Color) field.get(data);
                String newColor = "";
                if (Color.RED == color) {
                    newColor = "red";
                } else if (Color.GREEN == color) {
                    newColor = "green";
                } else if (Color.BLACK == color) {
                    newColor = "black";
                } else if (Color.BLUE == color) {
                    newColor = "blue";
                } else if (Color.YELLOW == color) {
                    newColor = "yellow";
                };
                statement.setString(index, newColor);
            } else {
//This column only supports the above types, if the need for new types increase disposal; generic processing can also support, because the time is limited, the generic type is more complex, here don't do processing, later will have the opportunity to send a generic processing
                throw new RuntimeException("unsupport provided type:" + field.getType());
            }
        } finally {
            field.setAccessible(isAccess);
        }
    }

    // Get all the defining properties, special treatment here don't do; if you need to deal with, such as the static final definition
// Still can decide whether the property used in note form processing
    private static void getAllFeild(List<Field> fieldList, Class<?> clazz) {
        if (clazz == Object.class) {
            return;
        } else {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                fieldList.add(field);
            }

            getAllFeild(fieldList, clazz.getSuperclass());
        }
    }

    private static void setValue(ResultSet result, Human data, Field field) throws Exception {
        boolean isAccess = field.isAccessible();
        try {
//Close the safety check, or read not to private property
            field.setAccessible(true);
            if (field.getType() == byte.class || field.getType() == Byte.class) {
                field.setByte(data, result.getByte(field.getName()));
            } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                field.setBoolean(data, result.getBoolean(field.getName()));
            } else if (field.getType() == short.class || field.getType() == Short.class) {
                field.setShort(data, result.getShort(field.getName()));
            } else if (field.getType() == char.class || field.getType() == Character.class) {
                field.set(data, result.getString(field.getName()).charAt(0));
            } else if (field.getType() == int.class || field.getType() == Integer.class) {
                field.setInt(data, result.getInt(field.getName()));
            } else if (field.getType() == float.class || field.getType() == Float.class) {
                field.setFloat(data, result.getFloat(field.getName()));
            } else if (field.getType() == long.class || field.getType() == Long.class) {
                field.setLong(data, result.getLong(field.getName()));
            } else if (field.getType() == double.class || field.getType() == Double.class) {
                field.setDouble(data, result.getDouble(field.getName()));
            } else if (field.getType() == String.class) {
                field.set(data, result.getString(field.getName()));
            } else if (field.getType() == LocalDateTime.class){
                LocalDateTime localDateTime = result.getTimestamp(field.getName()).toLocalDateTime();
                field.set(data, localDateTime);
            } else if (field.getType() == Color.class) {
                String lol = result.getObject(field.getName()).toString();
                System.out.println(result.getObject(field.getName()));
                Color color;
                try {
                    Field fieldcolor = Class.forName("java.awt.Color").getField(lol);
                    color = (Color) fieldcolor.get(null);
                } catch (Exception e) {
                    color = null; // Not defined
                }
                field.set(data, color);
            } else {
//This column only supports the above types, if the need for new types increase disposal; generic processing can also support, because the time is limited, the generic type is more complex, here don't do processing, later will have the opportunity to send a generic processing
                throw new RuntimeException("uns provided type:" + field.getType());
            }
        } finally {
            field.setAccessible(isAccess);
        }
    }

}
