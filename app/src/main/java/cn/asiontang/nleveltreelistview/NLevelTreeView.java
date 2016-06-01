package cn.asiontang.nleveltreelistview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 支持N(无限)层级的树列表结构
 *
 * <p>参考资料：</p>
 * <ul>
 * <li>
 * <a href="http://item.congci.com/item/android-wuxian-ji-shuzhuang-jiegou">Android无限级树状结构 -
 * Android
 * - 从此网</a>
 * </li>
 * </ul>
 *
 * @author AsionTang
 * @since 2016年6月1日 18:38:43
 */
@SuppressWarnings("unused")
public class NLevelTreeView extends ListView
{
    private OnTreeNodeClickListener mOnTreeNodeClickListener;

    public NLevelTreeView(final Context context)
    {
        super(context);
        this.init();
    }

    public NLevelTreeView(final Context context, final AttributeSet attrs)
    {
        super(context, attrs);
        this.init();
    }

    public NLevelTreeView(final Context context, final AttributeSet attrs, final int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NLevelTreeView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init();
    }

    private void init()
    {
    }

    public void setAdapter(final NLevelTreeNodeAdapter adapter)
    {
        super.setAdapter(adapter);

        //让 NLevelTreeNodeAdapter 处理 节点 收缩展开 动作
        super.setOnItemClickListener(adapter);

        //处理当 叶子节点 被点击后的事件 回调。
        adapter.setOuterOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id)
            {
                if (NLevelTreeView.this.mOnTreeNodeClickListener != null)
                {
                    final NLevelTreeNode item = (NLevelTreeNode) parent.getItemAtPosition(position);
                    NLevelTreeView.this.mOnTreeNodeClickListener.onTreeNodeClick(item);
                }
            }
        });
    }

    /**
     * 必须使用继承自 NLevelTreeNodeAdapter 的 适配器，否则会出现异常。
     */
    @Override
    public void setAdapter(final ListAdapter adapter)
    {
        if (adapter instanceof NLevelTreeNodeAdapter)
            this.setAdapter((NLevelTreeNodeAdapter) adapter);
        else
            throw new RuntimeException("For NLevelTreeView, use setAdapter(NLevelTreeNodeAdapter) instead of setAdapter(ListAdapter)");
    }

    /**
     * 不支持使用此回调方式
     */
    @Override
    @Deprecated
    public void setOnItemClickListener(final OnItemClickListener listener)
    {
        //实际的事件回调在setAdapter里设置，由 setOnTreeNodeClickListener 处理。
        //super.setOuterOnItemClickListener(listener);

        throw new RuntimeException("For NLevelTreeView, use setOnTreeNodeClickListener() instead of setOnItemClickListener()");
    }

    /**
     * 默认只支持叶子节点的Click事件
     */
    public void setOnTreeNodeClickListener(final OnTreeNodeClickListener listener)
    {
        this.mOnTreeNodeClickListener = listener;
    }

    /**
     * 默认只支持叶子节点的Click事件
     */
    public interface OnTreeNodeClickListener
    {
        /**
         * 默认只支持叶子节点的Click事件
         */
        void onTreeNodeClick(NLevelTreeNode node);
    }
}
