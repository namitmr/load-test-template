package com.webengage.p13;

import com.expeval.Expression;
import com.expeval.tokens.Function;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by namitmahuvakar on 01/06/17.
 */
public class Match extends Function {
    Map<String,String> compare;
    String identifier;

    public Match(String identifier, Map<String, String> compare) {
        super(identifier);
        this.identifier = identifier;
        this.compare = compare;
    }

    public Match(String identifier) {
        super(identifier);
        this.identifier = identifier;
    }

    @Override
    public Object onEvaluation(List<Object> list) {
        String event = list.get(0).toString();
        JSONObject json = new JSONObject(event);
        boolean flag = true;
        for (Map.Entry<String, String> entry : compare.entrySet())
        {
            try {
                if (json.getString(entry.getKey()).equalsIgnoreCase(entry.getValue()))
                    continue;
                else {
                    flag = false;
                    break;
                }
            }catch (Exception e){
                flag = false;
            }
        }

        return flag;
    }
}
