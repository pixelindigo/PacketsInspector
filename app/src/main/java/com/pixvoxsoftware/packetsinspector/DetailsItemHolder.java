package com.pixvoxsoftware.packetsinspector;

import android.content.Context;
import android.view.View;

import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by PixelIndigo.
 */
public class DetailsItemHolder extends TreeNode.BaseNodeViewHolder<DetailsItemHolder.DetailsItem> {

    public DetailsItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, DetailsItem item) {
        return null;
    }

    class DetailsItem {
        private String value;

        public DetailsItem(final String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
