package org.redcross.openmapkit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.spatialdev.osm.model.OSMElement;
import com.spatialdev.osm.model.OSMXmlWriter;

import org.json.JSONObject;
import org.redcross.openmapkit.odkcollect.tag.ODKTag;
import org.redcross.openmapkit.odkcollect.tag.ODKTagItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.redcross.openmapkit.odkcollect.ODKCollectData.APP_NAME;

public class FakeDataMessenger {
//    private final List<OSMTag> osmRequiredTags;
    Intent launchIntent;



        public Intent launchOpenMapKit() {
        try {
            //launch with intent that sends plain text
             launchIntent = new Intent("android.intent.action.SEND");
            launchIntent.setType("text/plain");

            //send form id
           // launchIntent.putExtra("FORM_ID", "-1");

            //send instance id
           // launchIntent.putExtra("INSTANCE_ID", "uuid:6569f44b-266b-4787-a717-1e6872ed7a0d");

            //send instance directory
          //  launchIntent.putExtra("INSTANCE_DIR", "/storage/emulated/0/odk/instances/Buildings updated_2019-01-07_15-48-02");

            //send form file name
         //   launchIntent.putExtra("FORM_FILE_NAME", "Buildings updated");

//            //send OSM file name if there was a previous edit
//            if (osmFileName != null) {
//                launchIntent.putExtra("OSM_EDIT_FILE_NAME", osmFileName);
//            }

//            // Determine the tags required
//            osmRequiredTags = prompt.getQuestion().getOsmTags();

            //send encode tag data structure to intent
            writeOsmRequiredTagsToExtras(launchIntent);

//            //verify the package resolves before starting activity
//            Context ctx = getContext();
//            PackageManager packageManager = ctx.getPackageManager();
//            List<ResolveInfo> activities = packageManager.queryIntentActivities(launchIntent, 0);
//            boolean isIntentSafe = !activities.isEmpty();
//
//            //launch activity if it is safe
//            if (isIntentSafe) {
//                // notify that the form is waiting for data
//                waitForData();
//
//                // launch
//                ((Activity) ctx).startActivityForResult(launchIntent, RequestCodes.OSM_CAPTURE);
//            } else {
//                errorTextView.setVisibility(View.VISIBLE);
//            }
           // return launchIntent;
        } catch (Exception ex) {
            AlertDialog.Builder builder = new AlertDialog.Builder(null);
            builder.setTitle("Error");
            builder.setMessage("app missing!");
            DialogInterface.OnClickListener okClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //TODO: launch to app store?
                }
            };

            builder.setPositiveButton("Ok", okClickListener);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return launchIntent;
    }


   //  * See: https://github.com/AmericanRedCross/openmapkit/wiki/ODK-Collect-Tag-Intent-Extras


        private void writeOsmRequiredTagsToExtras(Intent intent) {
        ArrayList<ODKTag> osmRequiredTags = new ArrayList<>();
            //ArrayList<String> tagKeys = new ArrayList<>();

        ArrayList<String> tagKeys = new ArrayList<>();
        tagKeys.add("building");
       tagKeys.add("building:material");
       /* tagKeys.add("building:condition");
        tagKeys.add("building:levels");
        tagKeys.add("addr:housenumber");
        tagKeys.add("addr:street");
        tagKeys.add("addr:city");
        tagKeys.add("addr:state");
        tagKeys.add("addr:postcode");
        tagKeys.add("name");*/
        //Map for adding tag "Building"
            Map<String , String > building =new HashMap<String, String>();
            building.put("yes" , "Yes");
            building.put("residential" , "Residential");
            building.put("mixed" , "Other/Multiple (Mixed)");
            building.put("commercial" , "Commercial");
            building.put("industrial" , "Industrial");

            Iterator it = building.keySet().iterator();
            while (it.hasNext()){
                Object key = it.next();
                Object value = building.get(key);
                String keyStr = key.toString();
                String valueStr = value.toString();
                intent.putExtra("TAG_LABEL." + tagKeys.get(0),"Building");
                intent.putExtra("TAG_VALUE_LABEL." + tagKeys.get(0) + "." + keyStr,
                        valueStr);
            }
            //Map for adding tag "Building material"
            Map<String , String > material =new HashMap<String, String>();
            material.put("concrete" ,"Concrete");
            material.put("stone" ,"Stone");
            material.put("tin" ,"Tin");
            material.put("wood" ,"Wood");
            material.put("bamboo" ,"Bamboo");
            Iterator iterator = material.keySet().iterator();
            while (iterator.hasNext()){
                Object key = iterator.next();
                Object value = material.get(key);
                String keyStr = key.toString();
                String valueStr = value.toString();
                intent.putExtra("TAG_LABEL." + tagKeys.get(1),"Building Material");
                intent.putExtra("TAG_VALUE_LABEL." + tagKeys.get(1) + "." + keyStr,
                       valueStr);
                }

            ArrayList<String> buildingValues = new ArrayList<>(building.values());
            ArrayList<String> materialValues = new ArrayList<>(material.values());

            intent.putStringArrayListExtra("TAG_VALUES." + "building", buildingValues);
            intent.putStringArrayListExtra("TAG_VALUES." + "building:material", materialValues);
            intent.putStringArrayListExtra("TAG_KEYS", tagKeys);

      //  intent.putExtra("TAG_LABEL." + tagKeys.get(0),"Building");
            //intent.putExtra("TAG_LABEL." + "building", "Building");
            /*intent.putExtra("TAG_VALUE_LABEL." + "building" + "." + "yes",
                    "Yes");
            intent.putExtra("TAG_VALUE_LABEL." + "building" + "." + "residential",
                    "residential");
            intent.putExtra("TAG_LABEL." + "building:material", "Building Material");
            intent.putExtra("TAG_VALUE_LABEL." + "building:material" + "." + "concrete",
                    "Concrete");
            intent.putExtra("TAG_VALUE_LABEL." + "building:material" + "." + "brick",
                    "Brick");
            ArrayList<String> tagValues = new ArrayList<>();
            tagValues.add("yes");
            tagValues.add("residential");
            ArrayList<String> tagValues1 = new ArrayList<>();
            tagValues1.add("brick");
            tagValues1.add("concrete");*/


/*
    for (ODKTag tag : osmRequiredTags) {
            tagKeys.add(tag.key);
            if (tag.label != null) {
                intent.putExtra("TAG_LABEL." + tag.key, tag.label);
            }
            ArrayList<String> tagValues = new ArrayList<>();
            if (tag.items != null) {
                for (ODKTagItem item : tag.items) {
                    tagValues.add(item.value);
                    if (item.label != null) {
                        intent.putExtra("TAG_VALUE_LABEL." + tag.key + "." + item.value,
                                item.label);
                    }
                }
            }
            intent.putStringArrayListExtra("TAG_VALUES." + tag.key, tagValues);
        }
*/


    }




}
