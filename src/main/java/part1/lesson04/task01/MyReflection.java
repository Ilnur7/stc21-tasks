package part1.lesson04.task01;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyReflection {

    static void cleanup(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) throws IllegalAccessException {
        if (object instanceof Map) {
            findEntryHashMapBySet(object, fieldsToCleanup, fieldsToOutput);
        } else {
            findFieldsBySet(object, fieldsToCleanup, fieldsToOutput);
        }
    }

    /**
     * Метод, который находит элементы Map по списку ключей в Set
     * @param object
     * @param fieldsToCleanup
     * @param fieldsToOutput
     */
    private static void findEntryHashMapBySet(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) {
        Map map = (Map) object;
        Set setKey = map.keySet();
        Iterator iterator = setKey.iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            if (fieldsToCleanup.contains((String) key)) {
                iterator.remove();
            }
            if (fieldsToOutput.contains((String) key)) {
                System.out.println(key + " = " + map.get(key));
            }
        }
    }

    /**
     * Метод, который находит поля объекта Object по списку названий из Set
     * @param object
     * @param fieldsToCleanup
     * @param fieldsToOutput
     * @throws IllegalAccessException
     */
    private static void findFieldsBySet(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) throws IllegalAccessException {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            checkPresenceFieldForDelete(field, object, fieldsToCleanup);
            checkPresenceFieldForRendering(field, object, fieldsToOutput);
        }
    }

    /**
     * Метод проверки наличия поля для вывода в консоль
     * @param field
     * @param object
     * @param fieldsToOutput
     * @throws IllegalAccessException
     */
    private static void checkPresenceFieldForRendering(Field field, Object object, Set<String> fieldsToOutput) throws IllegalAccessException {
        if (fieldsToOutput.contains(field.getName())) {
            if (field.getType().isPrimitive()) {
                String stringPrimitive = String.valueOf(field.get(object));
                System.out.println(stringPrimitive);
            } else {
                if (field.get(object) == null) return;
                String stringRef = field.get(object).toString();
                System.out.println(stringRef);
            }
        } else {
            throw new IllegalArgumentException("Нет такого поля в сете для вывода в консоль");
        }
    }

    /**
     * Метод проверки наличия полей из Set для установления значений по умолчанию
     * @param field
     * @param object
     * @param fieldsToCleanup
     * @throws IllegalAccessException
     */
    private static void checkPresenceFieldForDelete(Field field, Object object, Set<String> fieldsToCleanup) throws IllegalAccessException {
        if (fieldsToCleanup.contains(field.getName())) {
            if (Modifier.isPrivate(field.getModifiers())) field.setAccessible(true);
            setDefaultValueInPrimitiveType(field, object);
        } else {
            throw new IllegalArgumentException("Нет такого поля в сете для установления значений по умолчанию");
        }
    }

    /**
     * Установка полям значения по умолчанию
     * @param field
     * @param object
     * @throws IllegalAccessException
     */
    private static void setDefaultValueInPrimitiveType(Field field, Object object) throws IllegalAccessException {
        switch (field.getType() + "") {
            case "boolean":
                field.set(object, false);
                break;
            case "byte":
            case "short":
            case "int":
                field.set(object, 0);
                break;
            case "long":
                field.set(object, 0L);
                break;
            case "char":
                field.set(object, '\u0000');
                break;
            case "float":
                field.set(object, 0f);
                break;
            case "double":
                field.set(object, 0d);
                break;
            default:
                field.set(object, null);
                break;
        }
    }
}


