package com.mli.mpro.productRestriction.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mli.mpro.utils.Utility;

import java.util.Map;


public class GsonTools {

    public static enum ConflictStrategy {

        THROW_EXCEPTION, PREFER_FIRST_OBJ, PREFER_SECOND_OBJ, PREFER_NON_NULL;
    }

    public class JsonObjectExtensionConflictException extends Exception {

        private static final long serialVersionUID = 1L;

        public JsonObjectExtensionConflictException(String message) {
            super(message);
        }

    }

    public JsonObject extendJsonObject(JsonObject destinationObject, ConflictStrategy conflictResolutionStrategy, JsonObject Objs)
            throws JsonObjectExtensionConflictException {

        return extendJsonObject(destinationObject, Objs, conflictResolutionStrategy);
    }

    private JsonObject extendJsonObject(JsonObject leftObj, JsonObject rightObj, ConflictStrategy conflictStrategy)
            throws JsonObjectExtensionConflictException {
        for (Map.Entry<String, JsonElement> rightEntry : rightObj.entrySet()) {
            String rightKey = rightEntry.getKey();
            JsonElement rightVal = rightEntry.getValue();
            if (leftObj.has(rightKey)) {
                // conflict
                JsonElement leftVal = leftObj.get(rightKey);
                if (Utility.andTwoExpressions(leftVal.isJsonArray(),rightVal.isJsonArray())) {
                    JsonArray leftArr = leftVal.getAsJsonArray();
                    JsonArray rightArr = rightVal.getAsJsonArray();
                    // concat the arrays -- there cannot be a conflict in an array, it's just a
                    // collection of stuff
                    concatArray(leftArr, rightArr);
                } else if (Utility.andTwoExpressions(leftVal.isJsonObject(), rightVal.isJsonObject())) {
                    // recursive merging
                    extendJsonObject(leftVal.getAsJsonObject(), rightVal.getAsJsonObject(), conflictStrategy);
                } else {// not both arrays or objects, normal merge with conflict resolution
                    handleMergeConflict(rightKey, leftObj, leftVal, rightVal, conflictStrategy);
                }
                continue;
            }
            // no conflict, add to the object
                leftObj.add(rightKey, rightVal);

        }
        return leftObj;
    }

    private void handleMergeConflict(String key, JsonObject leftObj, JsonElement leftVal, JsonElement rightVal, ConflictStrategy conflictStrategy)
            throws JsonObjectExtensionConflictException {
        {
            switch (conflictStrategy) {
                case PREFER_FIRST_OBJ:
                    break;// do nothing, the right val gets thrown out
                case PREFER_SECOND_OBJ:
                    leftObj.add(key, rightVal);// right side auto-wins, replace left val with its val
                    break;
                case PREFER_NON_NULL:
                    // check if right side is not null, and left side is null, in which case we use
                    // the right val
                    // else do nothing since either the left value is non-null or the right value is
                    // null
                    if (!rightVal.isJsonNull()) {
                        leftObj.add(key, rightVal);
                    }
                    break;
                case THROW_EXCEPTION:
                    throw new JsonObjectExtensionConflictException(
                            "Key " + key + " exists in both objects and the conflict resolution strategy is " + conflictStrategy);
                default:
                    throw new UnsupportedOperationException("The conflict strategy " + conflictStrategy + " is unknown and cannot be processed");
            }
        }
    }
private void concatArray(JsonArray leftArr, JsonArray rightArr) throws JsonObjectExtensionConflictException {
    int x = leftArr.size();
    for (int i = 0; i < rightArr.size(); i++) {
        if (Utility.andTwoExpressions(rightArr.get(i).isJsonObject(),  x > 0)) {
            extendJsonObject(leftArr.get(i).getAsJsonObject(), ConflictStrategy.PREFER_NON_NULL, rightArr.get(i).getAsJsonObject());
            x--;
            continue;
        }
        leftArr.add(rightArr.get(i));

    }
}
}