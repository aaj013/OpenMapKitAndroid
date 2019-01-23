package org.redcross.openmapkit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import org.redcross.openmapkit.odkcollect.tag.ODKTag;
import org.redcross.openmapkit.odkcollect.tag.ODKTagItem;

import java.util.ArrayList;

public class FakeDataMessenger {
//    private final List<OSMTag> osmRequiredTags;
    public Intent launchOpenMapKit() {
        try {
            //launch with intent that sends plain text
            Intent launchIntent = new Intent("android.intent.action.SEND");
            launchIntent.setType("text/plain");

            //send form id
            launchIntent.putExtra("FORM_ID", "buildings_updated");

            //send instance id
            launchIntent.putExtra("INSTANCE_ID", "");

            //send instance directory
            launchIntent.putExtra("INSTANCE_DIR", "");

            //send form file name
            launchIntent.putExtra("FORM_FILE_NAME", "");

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
            return launchIntent;
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
    }

    /**
     * See: https://github.com/AmericanRedCross/openmapkit/wiki/ODK-Collect-Tag-Intent-Extras
     */
        private void writeOsmRequiredTagsToExtras(Intent intent) {
        ArrayList<ODKTag> osmRequiredTags = new ArrayList<>();

        ArrayList<String> tagKeys = new ArrayList<>();
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
        intent.putStringArrayListExtra("TAG_KEYS", tagKeys);
    }

}
