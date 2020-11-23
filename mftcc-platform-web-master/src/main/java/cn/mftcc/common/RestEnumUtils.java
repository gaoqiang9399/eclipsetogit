package cn.mftcc.common;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sun.reflect.ConstructorAccessor;
import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

/**
 * 动态增加Enum实例枚举值
 */
public final class RestEnumUtils {

    private static ReflectionFactory reflectionFactory = ReflectionFactory.getReflectionFactory();

    public static final String WRONG_VALUE = "ENUM_WRONG_VALUE";

    private RestEnumUtils() {
    }

    private static void setFailSafeFieldValue(Field field, Object target, Object value) throws NoSuchFieldException,
            IllegalAccessException {

        // let's make the field accessible
        field.setAccessible(true);

        // next we change the modifier in the Field instance to
        // not be final anymore, thus tricking reflection into
        // letting us modify the static final field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        int modifiers = modifiersField.getInt(field);

        // blank out the final bit in the modifiers int
        modifiers &= ~Modifier.FINAL;
        modifiersField.setInt(field, modifiers);

        FieldAccessor fa = reflectionFactory.newFieldAccessor(field, false);
        fa.set(target, value);
    }

    private static void blankField(Class<?> enumClass, String fieldName) throws NoSuchFieldException,
            IllegalAccessException {
        for (Field field : Class.class.getDeclaredFields()) {
            if (field.getName().contains(fieldName)) {
                AccessibleObject.setAccessible(new Field[] { field }, true);
                setFailSafeFieldValue(field, enumClass, null);
                break;
            }
        }
    }

    private static void cleanEnumCache(Class<?> enumClass) throws NoSuchFieldException, IllegalAccessException {
        blankField(enumClass, "enumConstantDirectory"); // Sun (Oracle?!?) JDK 1.5/6
        blankField(enumClass, "enumConstants"); // IBM JDK
    }

    private static ConstructorAccessor getConstructorAccessor(Class<?> enumClass, Class<?>[] additionalParameterTypes)
            throws NoSuchMethodException {
        Class<?>[] parameterTypes = new Class[additionalParameterTypes.length + 2];
        parameterTypes[0] = String.class;
        parameterTypes[1] = int.class;
        System.arraycopy(additionalParameterTypes, 0, parameterTypes, 2, additionalParameterTypes.length);
        return reflectionFactory.newConstructorAccessor(enumClass.getDeclaredConstructor(parameterTypes));
    }

    private static Object makeEnum(Class<?> enumClass, String value, int ordinal, Class<?>[] additionalTypes,
                                   Object[] additionalValues) throws Exception, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        Object[] params = new Object[additionalValues.length + 2];
        params[0] = value;
        params[1] = Integer.valueOf(ordinal);
        System.arraycopy(additionalValues, 0, params, 2, additionalValues.length);
        return enumClass.cast(getConstructorAccessor(enumClass, additionalTypes).newInstance(params));
    }

    /**
     * Add an enum instance to the enum class given as argument
     *
     * @param <T> the type of the enum (implicit)
     * @param enumType the class of the enum to be modified
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws InstantiationException 
     * @throws NoSuchFieldException 
     * @throws NegativeArraySizeException 
     */
    @SuppressWarnings("unchecked")
    private static <T extends Enum<?>> void addEnum(Class<T> enumType) throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, NegativeArraySizeException, NoSuchFieldException {

        // 0. Sanity checks
        if (!Enum.class.isAssignableFrom(enumType)) {
            throw new RuntimeException("class " + enumType + " is not an instance of Enum");
        }

        // 1. Lookup "$VALUES" holder in enum class and get previous enum instances
        Field valuesField = null;
        Field[] fields = enumType.getDeclaredFields();
        //enumName the name of the new enum instance to be added to the class.
        for (Field field : fields) {
            if (field.getName().contains("$VALUES")) {
                valuesField = field;
                break;
            }
        }
        AccessibleObject.setAccessible(new Field[] { valuesField }, true);

        try {

            // 2. Copy it
            T[] previousValues = (T[]) valuesField.get(enumType);
            List<T> values = new ArrayList<T>(Arrays.asList(previousValues));

            // 3. build new enum
            T newValue = (T) makeEnum(enumType, WRONG_VALUE, values.size(), new Class[]{String.class}, new Object[]{WRONG_VALUE});

            // 4. add new value
            values.add(newValue);

            // 5. Set new values field
            setFailSafeFieldValue(valuesField, null, values.toArray((T[]) Array.newInstance(enumType, 0)));

            // 6. Clean enum cache
            cleanEnumCache(enumType);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 根据值判断是否增加错误的枚举值
     * @param enumType 枚举类
     * @param value 枚举值
     * @param <T> 枚举类
     * @return 枚举值
     * @throws NoSuchFieldException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     * @throws NegativeArraySizeException 
     * @throws IllegalArgumentException 
     */
    public static <T extends Enum<?>> String setEnumWrongValue(Class<T> enumType, String value) throws IllegalArgumentException, NegativeArraySizeException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException{
        if(null != value){
            boolean addWrongValue = true;
            boolean hasWrongValue = false;
            for(T type : enumType.getEnumConstants()){
                if(type.name().equals(value)){
                    addWrongValue = false;
                    break;
                }
                if(type.name().equals(WRONG_VALUE)){
                    hasWrongValue = true;
                }
            }
            if(addWrongValue){
                value = WRONG_VALUE;
                if(!hasWrongValue){
                    addEnum(enumType);
                }
            }
        }
        return value;
    }

    /**
     * 自定义表单模块类型
     */
    public static enum FXModel {
        /**
         *tpl
         */
        M_TPL("tpl"),
        /**
         *product
         */
        M_PRODUCT("product");

        private String value;
        public String getValue() {
			return value;
		}
        
        private FXModel(String value) {
            try {
				Field e = this.getClass().getSuperclass().getDeclaredField("name");
				e.setAccessible(true);
				e.set(this, value);
				e.setAccessible(false);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    public static void main(String[] args) {
        try {
			// Dynamically add 3 new enum instances d, e, f to TestEnum
			setEnumWrongValue(FXModel.class, "456");
			System.out.println(Arrays.deepToString(FXModel.values()));
			//[tpl, product, ENUM_WRONG_VALUE]
			for (Field field : FXModel.class.getDeclaredFields()) {
			    System.out.println(field.getName());
			    //M_TPL,M_PRODUCT,$VALUES
			}
			for (FXModel f :FXModel.values()) {
				System.out.println("name-"+f.name());
				System.out.println("value-"+f.getValue());
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NegativeArraySizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}