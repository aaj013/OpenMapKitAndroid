package org.redcross.openmapkit.odkcollect.tag;

/**
 *	Structure defining possible tag values that can be selected.
 *  Created by Nicholas Hallahan nhallahan@spatialdev.com
 */
public class ODKTagItem {
    //edited by athii-start
    //  changed access modifier of label and value from private to public
    public String label;
    public String value;
    //edited by athii-end
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
