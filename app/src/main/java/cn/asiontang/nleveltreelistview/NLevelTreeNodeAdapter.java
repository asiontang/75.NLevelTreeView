package cn.asiontang.nleveltreelistview;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author AsionTang
 * @since 2016年6月1日 18:38:43
 */
@SuppressWarnings("unused")
public abstract class NLevelTreeNodeAdapter extends BaseAdapterEx3<NLevelTreeNode> implements AdapterView.OnItemClickListener
{
    private final Set<NLevelTreeNode> mExpandedNodeList = new HashSet<>();
    private AdapterView.OnItemClickListener mOuterOnItemClickListener;

    public NLevelTreeNodeAdapter(final Context context, final int itemLayoutResId)
    {
        super(context, itemLayoutResId);
    }

    public NLevelTreeNodeAdapter(final Context context, final int itemLayoutResId, final List<NLevelTreeNode> objects)
    {
        super(context, itemLayoutResId, objects);
    }

    @Override
    public void convertView(final ViewHolder viewHolder, final NLevelTreeNode item)
    {
        this.convertView(viewHolder, item, this.mExpandedNodeList.contains(item));
    }

    public abstract void convertView(final ViewHolder viewHolder, final NLevelTreeNode item, boolean isExpanded);

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id)
    {
        final NLevelTreeNode item = getItem(position);
        if (this.mExpandedNodeList.contains(item))
        {
            //把展开的节点，收缩起来
            this.mExpandedNodeList.remove(item);

            final int nextPosition = position + 1;
            while (true)
            {
                //说明已经删除到最后一个节点了。
                if (nextPosition >= this.getOriginaItems().size())
                    break;

                final NLevelTreeNode tmpNode = this.getOriginaItems().get(nextPosition);

                //只删除比它自己级别深的节点（如它的子、孙、重孙节点）
                if (tmpNode.getLevel() <= item.getLevel())
                    break;

                this.getOriginaItems().remove(tmpNode);

                //防止它的子孙节点也有可能是展开的，所有也要移除其状态。
                this.mExpandedNodeList.remove(tmpNode);
            }
            this.refresh();
        }
        else
        {
            //没有子节点，则不允许展开
            if (item.getChilds().size() == 0)
            {
                //默认只支持叶子节点的Click事件
                if (this.mOuterOnItemClickListener != null)
                    this.mOuterOnItemClickListener.onItemClick(parent, view, position, id);
                return;
            }

            //把收缩的节点，展开起来
            this.mExpandedNodeList.add(item);

            this.getOriginaItems().addAll(position + 1, item.getChilds());

            this.refresh();
        }
    }

    /**
     * 设置外围调用者真正需要的 项点击OnItemClickListener 事件 回调。
     */
    protected void setOuterOnItemClickListener(final AdapterView.OnItemClickListener listener)
    {
        this.mOuterOnItemClickListener = listener;
    }
}