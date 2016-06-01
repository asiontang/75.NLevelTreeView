package cn.asiontang.nleveltreelistview.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.asiontang.nleveltreelistview.NLevelTreeNode;
import cn.asiontang.nleveltreelistview.NLevelTreeNodeAdapter;
import cn.asiontang.nleveltreelistview.NLevelTreeView;
import cn.asiontang.nleveltreelistview.R;

public class DemoActivity extends Activity implements NLevelTreeView.OnTreeNodeClickListener
{
    private List<NLevelTreeNode> getTestDate()
    {
        final List<NLevelTreeNode> nodeList = new ArrayList<>();
        nodeList.add(new NLevelTreeNode("1"));
        nodeList.add(new NLevelTreeNode("2")//
                .addChild(new NLevelTreeNode("21"))//
                .addChild(new NLevelTreeNode("22"))//
                .addChild(new NLevelTreeNode("23"))//
        );
        nodeList.add(new NLevelTreeNode("3")//
                .addChild(new NLevelTreeNode("31")//
                        .addChild(new NLevelTreeNode("311"))//
                        .addChild(new NLevelTreeNode("312"))//
                        .addChild(new NLevelTreeNode("313"))//
                )//
                .addChild(new NLevelTreeNode("32"))//
                .addChild(new NLevelTreeNode("33"))//
        );
        nodeList.add(new NLevelTreeNode("4")//
                .addChild(new NLevelTreeNode("41")//
                        .addChild(new NLevelTreeNode("411")//
                                .addChild(new NLevelTreeNode("4111"))//
                                .addChild(new NLevelTreeNode("4112"))//
                                .addChild(new NLevelTreeNode("4113"))//
                        )//
                        .addChild(new NLevelTreeNode("412")//
                                .addChild(new NLevelTreeNode("4121"))//
                                .addChild(new NLevelTreeNode("4122"))//
                                .addChild(new NLevelTreeNode("4123"))//
                        )//
                        .addChild(new NLevelTreeNode("413")//
                                .addChild(new NLevelTreeNode("4131"))//
                                .addChild(new NLevelTreeNode("4132"))//
                                .addChild(new NLevelTreeNode("4133"))//
                        )//
                )//
                .addChild(new NLevelTreeNode("42"))//
                .addChild(new NLevelTreeNode("43"))//
        );
        return nodeList;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        final NLevelTreeView listView = ((NLevelTreeView) this.findViewById(android.R.id.list));
        listView.setAdapter(new InnerAdapter(this, android.R.layout.simple_list_item_1, getTestDate()));
        listView.setOnTreeNodeClickListener(this);
    }

    @Override
    public void onTreeNodeClick(final NLevelTreeNode node)
    {
        Toast.makeText(DemoActivity.this, "" + node.getID(), Toast.LENGTH_SHORT).show();
    }

    private class InnerAdapter extends NLevelTreeNodeAdapter
    {
        private int mDefaultPaddingLeft = -1;

        public InnerAdapter(final Context context, final int itemLayoutResId, final List<NLevelTreeNode> objects)
        {
            super(context, itemLayoutResId, objects);
        }

        @Override
        public void convertView(final ViewHolder viewHolder, final NLevelTreeNode item, final boolean isExpanded)
        {
            final TextView textView = (TextView) viewHolder.convertView;

            if (item.getChilds().size() == 0)
                textView.setText(item.getName());
            else if (isExpanded)
                textView.setText("↓" + item.getName());
            else
                textView.setText("→" + item.getName());

            if (this.mDefaultPaddingLeft == -1)
                this.mDefaultPaddingLeft = textView.getPaddingLeft();
            textView.setPadding(this.mDefaultPaddingLeft + item.getLevel() * 48//
                    , textView.getPaddingTop()//
                    , textView.getPaddingRight()//
                    , textView.getPaddingBottom());
        }
    }
}
