package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.exception.DomainException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.BAD_REQUEST;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOTHING_TO_PATCH;
import static fr.istic.gm.weplan.domain.exception.DomainException.WRONG_DATA_TO_PATCH;

/**
 * Patch an object.
 *
 * @param <T> the class of the object to patch
 */
abstract class PatchService<T> {

    protected String[] getIgnoreFieldToPatch() {
        return new String[0];
    }

    /**
     * Patch an object with values.
     *
     * @param o    the object to patch
     * @param data the data to patch
     */
    protected void patch(T o, Map<String, Object> data) {
        verifyData(o, data);
        for (Method m : o.getClass().getDeclaredMethods()) {
            if (m.getName().startsWith("set")) {
                patchField(o, data, m);
            }
        }
    }

    /**
     * Patch a field
     *
     * @param o      the object to patch
     * @param data   the data to patch
     * @param method the method to patch a field
     */
    private void patchField(T o, Map<String, Object> data, Method method) {
        String dataField = toDataField(method.getName());
        if (Arrays.stream(getIgnoreFieldToPatch()).noneMatch(f -> f.equals(dataField)) && data.containsKey(dataField)) {
            try {
                method.invoke(o, data.get(dataField));
            } catch (Exception ex) {
                throw new DomainException(WRONG_DATA_TO_PATCH, o.getClass().getSimpleName(), BAD_REQUEST);
            }
        }
    }

    /**
     * Verify if data is null or empty.
     *
     * @param o    the object for logging
     * @param data the data to test
     */
    private void verifyData(T o, Map<String, Object> data) {
        if (data == null || data.isEmpty()) {
            throw new DomainException(NOTHING_TO_PATCH, o.getClass().getSimpleName(), BAD_REQUEST);
        }
    }

    /**
     * Convert a true fieldName to a data field.
     *
     * @param fieldName the field name
     * @return the data field
     */
    private String toDataField(String fieldName) {
        char[] c = fieldName.substring(3).toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }
}
