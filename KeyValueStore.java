/*
 * Copyright (C) 2023 - 2024  Zachary A. Kissel 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License 
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package dht;

import java.io.InvalidObjectException;
import java.util.HashMap;

import merrimackutil.json.JSONSerializable;
import merrimackutil.json.types.JSONArray;
import merrimackutil.json.types.JSONObject;
import merrimackutil.json.types.JSONType;

/**
 * This is the iteraction with the key value store which
 * contains the data that this node knows about.
 */
public class KeyValueStore implements JSONSerializable 
{
    HashMap<String, String> store;

    /**
     * Construct a new key-value store.
     */
    public KeyValueStore() 
    {
        store = new HashMap<>();
    }

    /**
     * Add a key-value pair to the store.
     * @param key the key to add.
     * @param value the associated value to add.
     */
    public synchronized void put(String key, String value)
    {
        store.put(key, value);
    }

    /**
     * Get the value associated with the key or return null 
     * if the key is unknown.
     * @param key the key to lookup.
     * @return the value or null.
     */
    public synchronized String getValue(String key)
    {
        if (store.containsKey(key))
            return store.get(key);
        return null;
    }

    /**
     * Determines if the store contains the key.
     * @param key the key to check.
     * @return {@code true} if the key is in the store and {@code false} otherwise.
     */
    public synchronized boolean containsKey(String key)
    {
        return store.containsKey(key);
    }

    /**
     * Create a new key-value store from a JSON representation.
     * @throws InvalidObjectException if {@code obj} is not a JSON 
     * encoded key-value store.
     */
    public KeyValueStore(JSONObject obj) throws InvalidObjectException
    {
        deserialize(obj);
    }

    /**
     * Deserialize the JSON representation of the database.
     */
    public void deserialize(JSONType obj) throws InvalidObjectException 
    {
        JSONObject kvs; 

        if (!(obj instanceof JSONObject))
            throw new InvalidObjectException("Key-value store should be an object.");
        
        kvs = (JSONObject) obj;

        if (kvs.containsKey("data"))
        {
            JSONArray array = kvs.getArray("data");
            
            for (int i = 0; i < array.size(); i++)
            {
                JSONObject kvPair = array.getObject(i);
                store.put(kvPair.getString("key"), kvPair.getString("value"));
            }
        }
        else 
            throw new InvalidObjectException("Key-value store should have data array.");
        
        if (kvs.size() > 1)
            throw new InvalidObjectException("Key-value store has superflous fields.");
    }

    /**
     * Convert the key-value store to a JSON string.
     * 
     * @return the string representation of the key-value store.
     */
    @Override
    public synchronized String serialize() 
    {
        return toJSONType().toJSON();
    }

    /**
     * Convert the key-value store to an JSON object.
     * 
     * @return a JSON object representing the key-value store.
     */
    @Override
    public JSONType toJSONType() 
    {
        JSONArray array = new JSONArray();

        for (String key : store.keySet())
        {
            JSONObject kvPair = new JSONObject();
            kvPair.put("key", key);
            kvPair.put("value", store.get(key));
            array.add(kvPair);
        }

        JSONObject rv = new JSONObject();
        rv.put("data", array);
        return rv;
    }

    /**
     * Give a string representaiton of the KV store.
     */
    @Override
    public String toString()
    {
        String kvPairs = "";
        for (String key : store.keySet())
            kvPairs += "Key: " + key + " Value: " +  store.get(key) + "\n";
        return kvPairs.strip();
    }

    public static String get(String key) {
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
}
