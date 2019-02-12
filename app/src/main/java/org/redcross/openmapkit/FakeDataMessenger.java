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


        //* See: https://github.com/AmericanRedCross/openmapkit/wiki/ODK-Collect-Tag-Intent-Extras

        private void writeOsmRequiredTagsToExtras(Intent intent) {
            //ArrayList<ODKTag> osmRequiredTags = new ArrayList<>();
            //ArrayList<String> tagKeys = new ArrayList<>();

            ArrayList<String> tagKeys = new ArrayList<>();
//            tagKeys.add("building");
//            tagKeys.add("building:material");
            tagKeys.add("name"); // Office Name
            tagKeys.add("addr:housenumber"); // Building Number
            tagKeys.add("addr:street"); // Street Name
            tagKeys.add("addr:place"); // Place
            tagKeys.add("addr:postcode"); // Post Code
            tagKeys.add("addr:city"); // City
            tagKeys.add("addr:district"); // District
            tagKeys.add("local_body_type"); // Local Body Type
            tagKeys.add("opening_hours"); // Working Hours
            tagKeys.add("contact"); // Phone Number
            tagKeys.add("email"); // OFFICE Email
            tagKeys.add("website"); // OFFICE Website Link

            intent.putStringArrayListExtra("TAG_KEYS", tagKeys);

            ArrayList<String> tagLabels = new ArrayList<>();
//            tagLabels.add("Building");
//            tagLabels.add("Building Material");
            tagLabels.add("Office Name"); // Office Name
            tagLabels.add("Building Number"); // Building Number
            tagLabels.add("Street Name"); // Street Name
            tagLabels.add("Place"); // Place
            tagLabels.add("Post Code"); // Post Code
            tagLabels.add("City"); // City
            tagLabels.add("District"); // District
            tagLabels.add("Local Body Type"); // Local Body Type
            tagLabels.add("Working Hours"); // Working Hours
            tagLabels.add("Phone Number"); // Phone Number
            tagLabels.add("OFFICE Email"); // OFFICE Email
            tagLabels.add("OFFICE Website Link"); // OFFICE Website Link

            for(int counter = 0; counter < tagKeys.size(); counter++){
                intent.putExtra("TAG_LABEL." + tagKeys.get(counter),tagLabels.get(counter));
            }

            /* tagKeys.add("building:condition");
            tagKeys.add("building:levels");
            tagKeys.add("addr:housenumber");
            tagKeys.add("addr:street");
            tagKeys.add("addr:city");
            tagKeys.add("addr:state");
            tagKeys.add("addr:postcode");
            tagKeys.add("name");*/

            //Map for adding tag "District"
            Map<String, String > district = new HashMap<String, String>();
            district.put("thiruvananthapuram" , "Thiruvananthapuram");
            district.put("kollam" , "Kollam");
            district.put("pathanamthitta" , "Pathanamthitta");
            district.put("alappuzha" , "Alappuzha");
            district.put("kottayam" , "Kottayam");
            district.put("idukki" , "Idukki");
            district.put("ernakulam" , "Ernakulam");
            district.put("thrissur" , "Thrissur");
            district.put("palakkad" , "Palakkad");
            district.put("malappuram" , "Malappuram");
            district.put("kozhikode" , "Kozhikode");
            district.put("wayanad" , "Wayanad");
            district.put("kannur" , "Kannur");
            district.put("kasaragod" , "Kasaragod");

            Iterator it;

            it = district.keySet().iterator();
            while (it.hasNext()){
                Object key = it.next();
                Object value = district.get(key);
                String keyStr = key.toString();
                String valueStr = value.toString();
                intent.putExtra("TAG_VALUE_LABEL.addr:district." + keyStr, valueStr);
            }

            ArrayList<String> districtValues = new ArrayList<>(district.values());
            intent.putStringArrayListExtra("TAG_VALUES.addr:district", districtValues);

            //Map for adding tag "Local Body Type"
            Map<String, String > local_body_type = new HashMap<String, String>();
            local_body_type.put("corporation" , "Corporation");
            local_body_type.put("municipality" , "Municipality");
            local_body_type.put("panchayat" , "Panchayat");

            it = local_body_type.keySet().iterator();
            while (it.hasNext()){
                Object key = it.next();
                Object value = local_body_type.get(key);
                String keyStr = key.toString();
                String valueStr = value.toString();
                intent.putExtra("TAG_VALUE_LABEL.local_body_type." + keyStr, valueStr);
            }

            ArrayList<String> local_body_typeValues = new ArrayList<>(local_body_type.values());
            intent.putStringArrayListExtra("TAG_VALUES.local_body_type", local_body_typeValues);

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
                        intent.putExtra("TAG_VALUE_LABEL." + tag.key + "." + item.value, item.label);
                    }
                }
            }
            intent.putStringArrayListExtra("TAG_VALUES." + tag.key, tagValues);
            }
            */

    }

}