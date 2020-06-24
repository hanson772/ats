package com.milu.ats.bean.enums;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author max.chen
 * @class
 */
public interface IEBase<T extends Enum<T>> {
    /**
     * 返回code
     * @return
     */
    default int getCode(){
        try{
            Field fields = this.getClass().getDeclaredField("code");
            if(fields != null){
                fields.setAccessible(true);
                int object = fields.getInt(this);
                return object;
            }
        }catch (Exception ex){
        }
        return 0;
    }

    /**
     * 显示名称
     * @return
     */
    default String show(){
        try{
            Field fields = this.getClass().getDeclaredField("name");
            if(fields != null){
                fields.setAccessible(true);
                Object object = fields.get((T)this);
                return object != null ? object.toString() : "";
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "";
    }

    default boolean inEnum(T sample, T... args){
        boolean in = false;
        if(sample != null) {
            for (T ti : args) {
                if (sample == ti) {
                    in = true;
                    break;
                }
            }
        }

        return in;
    }

    default <T extends Enum<T>> T getByCode(int code){
        try{
            Method method = getClass().getMethod("values");
            T[] enums = (T[]) method.invoke(null, null);
            for (T et : enums) {
                Field abs = et.getDeclaringClass().getDeclaredField("code");
                if(abs != null){
                    try {
                        abs.setAccessible(true);
                        Object object = abs.get(et);
                        if(Integer.valueOf(object.toString()) == code){
                            return et;
                        }
                    }catch (Exception ec){}
                }
            }
        }catch (Exception ex){}
        return null;
    }
}
