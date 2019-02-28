package org.redcross.openmapkit.odkcollect;

import android.content.Intent;
import android.os.Bundle;

import com.spatialdev.osm.model.OSMElement;

import org.redcross.openmapkit.odkcollect.tag.ODKTag;
import org.redcross.openmapkit.odkcollect.tag.ODKTagItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Nicholas Hallahan on 2/9/15.
 * nhallahan@spatialdev.com
 * * * 
 */
public class ODKCollectHandler {

    private static ODKCollectData odkCollectData;
    
    public static void registerIntent(Intent intent) {
        String action = intent.getAction();
        if(action != null && action.equals("android.intent.action.SEND")) {
            if (intent.getType().equals("text/plain")) {
                Bundle extras = intent.getExtras();
                LinkedHashMap<String, ODKTag> requiredTags = generateRequiredOSMTagsFromBundle(extras);
                odkCollectData = new ODKCollectData(requiredTags);
             /*   if(extras != null) {
                    // extract data from intent extras
                    String formId = extras.getString("FORM_ID");
                    String formFileName = extras.getString("FORM_FILE_NAME");
                    String instanceId = extras.getString("INSTANCE_ID");
                    String instanceDir = extras.getString("INSTANCE_DIR");
                    String previousOSMEditFileName = extras.getString("OSM_EDIT_FILE_NAME");
                    LinkedHashMap<String, ODKTag> requiredTags = generateRequiredOSMTagsFromBundle(extras);
                    odkCollectData = new ODKCollectData(formId, 
                                                        formFileName,
                                                        instanceId,
                                                        instanceDir,
                                                        previousOSMEditFileName,
                                                        requiredTags);
                }*/
            }
        }
    }
    
    public static boolean isODKCollectMode() {
        if (odkCollectData != null) {
            return true;
        }
        return false;
    }
    
    public static boolean isStandaloneMode() {
        if (odkCollectData == null) {
            return true;
        }
        return false;
    }
    
    public static ODKCollectData getODKCollectData() {
        return odkCollectData;
    }

    /**
     * Saves an OSM Element as XML in ODK Collect.
     * * * 
     * @param el
     * @return The full path of the saved OSM XML File
     */
    public static String saveXmlInODKCollect(OSMElement el, String osmUserName) {
        try {
            System.out.println("inside saveXmlInODKCollect");
            odkCollectData.consumeOSMElement(el, osmUserName);
            odkCollectData.deleteOldOSMEdit();
            odkCollectData.writeXmlToOdkCollectInstanceDir();
            return odkCollectData.getOSMFileFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static LinkedHashMap<String, ODKTag> generateRequiredOSMTagsFromBundle(Bundle extras) {
        List<String> tagKeys = extras.getStringArrayList("TAG_KEYS");
        for(int i=0;i<tagKeys.size();i++){
            System.out.println("Tagkeys are: " + tagKeys.get(i));
        }
        if (tagKeys == null || tagKeys.size() == 0) {
            System.out.println("Tagkeys are null");
            return null;
        }
        LinkedHashMap<String, ODKTag> tags = new LinkedHashMap<>();
        for (String key : tagKeys) {
            ODKTag tag = new ODKTag();
            tags.put(key, tag);
            System.out.println("Key added: " + tags.get(key).toString());
            System.out.println("ODK TagKey is:" + tag.getKey());
            System.out.println("ODK TagLabel is:" + tag.getLabel());
            tag.setKey(key);
            System.out.println("key of tag: "+tag.getKey());
            String label = extras.getString("TAG_LABEL." + key);
            System.out.println("Label is:" + label);
            if (label != null) {
                tag.setLabel(label);
                System.out.println("label of tag is: " + tag.getLabel());
            }
            List<String> values = extras.getStringArrayList("TAG_VALUES." + key);
            if (values != null && values.size() > 0) {
                for (String value : values) {
                    ODKTagItem tagItem = new ODKTagItem();
                    tagItem.setValue(value);
                    System.out.println("Tagitem label is :" +tagItem.getLabel());
                    System.out.println("Tagitem value is :" +tagItem.getValue());
                    System.out.println("values are: " +value);
                    String valueLabel = extras.getString("TAG_VALUE_LABEL." + key + "." + value);
                    if (valueLabel != null) {
                        tagItem.setLabel(valueLabel);
                        System.out.println("value label is: " + valueLabel);
                    }
                    tag.addItem(tagItem);
                    System.out.println("After adding tagitem, label is: " + tag.getLabel());
                    System.out.println("After adding tagitem, key is: " + tag.getKey());
                }
            }
        }
      System.out.println("Size of tags is: " + tags.size());
        return tags;
    }


}
